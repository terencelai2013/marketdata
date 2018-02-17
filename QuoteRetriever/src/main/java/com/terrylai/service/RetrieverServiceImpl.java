package com.terrylai.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
import com.terrylai.constant.CommonConstants;
import com.terrylai.constant.MongoConstants;
import com.terrylai.entity.Quote;
import com.terrylai.entity.Symbol;
import com.terrylai.processor.HistoricalQuoteProcessor;
import com.terrylai.repository.QuoteRepository;

@Service
@EnableMongoRepositories(basePackageClasses = QuoteRepository.class)
public class RetrieverServiceImpl implements RetrieverService, MongoConstants, CommonConstants {

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
	public long retrieve(String symbol) {
		long t0 = System.nanoTime();
		long retValue = 0;
		Symbol symObject = getSymbol(symbol);
		Calendar startDate = null;
		if (symObject != null && symObject.getCount() > 0) {
			try {
				Date sDate = new SimpleDateFormat(CONSTANT_DATE_FORMAT).parse(symObject.getEnd().toString());
				startDate = Calendar.getInstance();
				startDate.setTime(sDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (startDate != null) {
			Calendar today = Calendar.getInstance();
			today.set(Calendar.HOUR_OF_DAY, 0);
			today.set(Calendar.MINUTE, 0);
			today.set(Calendar.SECOND, 0);
			today.set(Calendar.MILLISECOND, 0);

			if (today.compareTo(startDate) <= 1) {
				System.out.println("Data are up-to-date, no need to retrieve from Yahoo");
				return 0;
			}
		}
		List<Quote> unfilteredQuotes = HistoricalQuoteProcessor.parse(symbol, startDate);

		if (unfilteredQuotes.size() > 0) {
			unfilteredQuotes.parallelStream()
					.filter(p -> p.getClose() != null && p.getAdjClose() != null && p.getHigh() != null
							&& p.getLow() != null && p.getOpen() != null && p.getVolume() != null)
					.forEach(p -> quoteRepository.save(p));
			retValue = unfilteredQuotes.size();
			System.out.println("Unfiltered Quotes size:" + unfilteredQuotes.size());

			long t2 = System.nanoTime();
			System.out.println(String.format("parallel stream collect: %d ms", TimeUnit.NANOSECONDS.toMillis(t2 - t0)));
		}

		return retValue;
	}

	@Override
	public Symbol getSymbol(String symbol) {
		Symbol returnSymbol = new Symbol(symbol, null, null, 0);
		GroupOperation groupBy = Aggregation.group(FIELD_KEY_SYMBOL).count().as(OUTPUT_FIELD_KEY_COUNT)
				.min(FIELD_KEY_DATE).as(OUTPUT_FIELD_KEY_START_DATE).max(FIELD_KEY_DATE).as(OUTPUT_FIELD_KEY_END_DATE);
		MatchOperation isEqual = Aggregation.match(new Criteria(FIELD_KEY_SYMBOL).is(symbol));
		Aggregation aggregation = Aggregation.newAggregation(isEqual, groupBy);
		AggregationResults<DBObject> results = mongoTemplate.aggregate(aggregation, COLLECTION_QUOTE, DBObject.class);

		DBObject object = results.getUniqueMappedResult();

		if (object != null) {
			returnSymbol.setName(object.get(OUTPUT_FIELD_KEY_ID).toString());
			returnSymbol.setStart(object.get(OUTPUT_FIELD_KEY_START_DATE).toString());
			returnSymbol.setEnd(object.get(OUTPUT_FIELD_KEY_END_DATE).toString());
			returnSymbol.setCount(Integer.valueOf(object.get(OUTPUT_FIELD_KEY_COUNT).toString()));
		}
		return returnSymbol;
	}
}
