package com.terrylai.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
import com.terrylai.repository.QuoteRepository;

@Service
@EnableMongoRepositories(basePackageClasses = QuoteRepository.class)
public class DataServiceImpl implements DataService, MongoConstants {

	@Autowired
	QuoteRepository quoteRepository;

	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public Symbol getSymbol(String symbol) {
		Symbol returnSymbol = new Symbol(OUTPUT_VALUE_SYMBOL_NA, null, null, 0);
		GroupOperation groupBy = Aggregation.group(FIELD_KEY_SYMBOL).count().as(OUTPUT_FIELD_KEY_COUNT).min(FIELD_KEY_DATE)
				.as(OUTPUT_FIELD_KEY_START_DATE).max(FIELD_KEY_DATE).as(OUTPUT_FIELD_KEY_END_DATE);
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

	@Override
	public List<Symbol> getSymbols() {
		List<Symbol> symbols = new ArrayList<Symbol>();
		GroupOperation groupBy = Aggregation.group(FIELD_KEY_SYMBOL).count().as(OUTPUT_FIELD_KEY_COUNT).min(FIELD_KEY_DATE)
				.as(OUTPUT_FIELD_KEY_START_DATE).max(FIELD_KEY_DATE).as(OUTPUT_FIELD_KEY_END_DATE);
		Aggregation aggregation = Aggregation.newAggregation(groupBy);
		AggregationResults<DBObject> results = mongoTemplate.aggregate(aggregation, COLLECTION_QUOTE, DBObject.class);
		Iterator<DBObject> iterators = results.iterator();
		DBObject object = null;
		Symbol symbol = null;
		while (iterators.hasNext()) {
			object = iterators.next();
			symbol = new Symbol(object.get(OUTPUT_FIELD_KEY_ID).toString(), new Date(object.get(OUTPUT_FIELD_KEY_START_DATE).toString()),
					new Date(object.get(OUTPUT_FIELD_KEY_END_DATE).toString()),
					Integer.valueOf(object.get(OUTPUT_FIELD_KEY_COUNT).toString()));
			symbols.add(symbol);
		}
		return symbols;
	}

	@Override
	public List<Quote> getQuote(String symbol) {
		List<Quote> quotes = null;
		quotes = quoteRepository.findBySymbolAndType(symbol, FIELD_VALUE_TYPE_RAW, new Sort(Sort.Direction.ASC, FIELD_KEY_DATE));
		return quotes;
	}
}
