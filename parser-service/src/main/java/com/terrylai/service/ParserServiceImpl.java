package com.terrylai.service;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import com.mongodb.DBObject;
import com.terrylai.constant.MongoConstants;
import com.terrylai.entity.Quote;
import com.terrylai.entity.Symbol;
import com.terrylai.parser.Parser;
import com.terrylai.repository.QuoteRepository;

@Service
@EnableMongoRepositories(basePackageClasses = QuoteRepository.class)
public class ParserServiceImpl implements ParserService, MongoConstants {

	@Autowired
	MongoTemplate mongoTemplate;

	@Autowired
	QuoteRepository quoteRepository;

	@Override
	public void reset() {
		quoteRepository.deleteAll();
	}

	@Override
	public void reset(String symbol) {
		quoteRepository.deleteBySymbol(symbol);
	}

	@Override
	public int get(String symbol) {
		int retValue = 0;
		List<Quote> quotes = null;
		if (symbol != null) {
			quotes = quoteRepository.findBySymbol(symbol);
		} else {
			quotes = quoteRepository.findAll();
		}
		if (quotes != null) {
			retValue = quotes.size();
		}
		return retValue;
	}

	@Override
	public int parse(String symbol, int period) {
		return get(symbol, period);
	}

	private int get(String symbol, int period) {
		long t0 = System.nanoTime();
		int retValue = 0;
		Calendar cal = Calendar.getInstance();
		List<Quote> quotes;

		Symbol symObject = getSymbol(symbol);

		if (symObject != null && !symObject.getName().equals(OUTPUT_VALUE_SYMBOL_NA)) {
			Date sDate = symObject.getStart();
			Date eDate = symObject.getEnd();

			cal.set(sDate.getYear() + 1900, sDate.getMonth(), sDate.getDate(), 0, 0, 0); 
			cal.add(Calendar.DATE, -1);
			final Date before = cal.getTime();

			cal.set(eDate.getYear() + 1900, eDate.getMonth(), eDate.getDate(), 0, 0, 0);
			final Date after = cal.getTime();

			List<Quote> unfilteredQuotes = Parser.parse(symbol, period);
			System.out.println("Unfiltered Quotes size:" + unfilteredQuotes.size());
			
			quotes = unfilteredQuotes.parallelStream()
					.filter(p -> p.getDate().after(after) || p.getDate().before(before)).collect(Collectors.toList());
		} else {
			quotes = Parser.parse(symbol, period);
		}
		System.out.println("Quotes size:" + quotes.size());

		if (quotes != null && quotes.size() > 0) {

			Iterator<Quote> iterator = quotes.iterator();

			Quote quote;
			while (iterator.hasNext()) {
				quote = (Quote) iterator.next();
				if (quote.getClose() == null || quote.getAdjClose() == null || quote.getHigh() == null
						|| quote.getLow() == null || quote.getOpen() == null || quote.getVolume() == null) {
					// quote contains null data
				} else {
					quoteRepository.save(quote);
				}
			}
			retValue = quotes.size();
		}
		long t1 = System.nanoTime();

		long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
		System.out.println(String.format("parallel stream took: %d ms", millis));
		return retValue;
	}

	@Override
	public Symbol getSymbol(String symbol) {
		Symbol returnSymbol = new Symbol(OUTPUT_VALUE_SYMBOL_NA, null, null, 0);
		GroupOperation groupBy = Aggregation.group(FIELD_KEY_SYMBOL).count().as(OUTPUT_FIELD_KEY_COUNT)
				.min(FIELD_KEY_DATE).as(OUTPUT_FIELD_KEY_START_DATE).max(FIELD_KEY_DATE).as(OUTPUT_FIELD_KEY_END_DATE);
		MatchOperation isEqual = Aggregation.match(new Criteria(FIELD_KEY_SYMBOL).is(symbol));
		Aggregation aggregation = Aggregation.newAggregation(isEqual, groupBy);
		AggregationResults<DBObject> results = mongoTemplate.aggregate(aggregation, COLLECTION_QUOTE, DBObject.class);
		Iterator<DBObject> iterators = results.iterator();
		DBObject object = null;
		while (iterators.hasNext()) {
			object = iterators.next();
			returnSymbol.setName(object.get(OUTPUT_FIELD_KEY_ID).toString());
			returnSymbol.setStart(new Date(object.get(OUTPUT_FIELD_KEY_START_DATE).toString()));
			returnSymbol.setEnd(new Date(object.get(OUTPUT_FIELD_KEY_END_DATE).toString()));
			returnSymbol.setCount(Integer.valueOf(object.get(OUTPUT_FIELD_KEY_COUNT).toString()));
		}
		return returnSymbol;
	}

}
