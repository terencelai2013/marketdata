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
import com.terrylai.entity.Quote;
import com.terrylai.entity.Symbol;
import com.terrylai.repository.QuoteRepository;

@Service
@EnableMongoRepositories(basePackageClasses = QuoteRepository.class)
public class DataServiceImpl implements DataService {

	static final String QUOTE_TYPE_RAW = "raw";
	@Autowired
	QuoteRepository quoteRepository;
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	@Override
	public Symbol getSymbol(String symbol) {
		Symbol returnSymbol = new Symbol("NA", null, null, 0);
    	GroupOperation groupBy = Aggregation.group("symbol").count().as("count").min("date").as("startDate").max("date").as("endDate");
    	MatchOperation isEqual = Aggregation.match(new Criteria("symbol").is(symbol));
    	Aggregation aggregation = Aggregation.newAggregation(isEqual, groupBy);
    	AggregationResults<DBObject> results = mongoTemplate.aggregate(aggregation, "quote", DBObject.class);
    	Iterator<DBObject> iterators = results.iterator();
    	DBObject object = null;
    	while (iterators.hasNext()) {
    		object = iterators.next();
    		returnSymbol.setName(object.get("_id").toString());
    		returnSymbol.setStart(new Date(object.get("startDate").toString()));
    		returnSymbol.setEnd(new Date(object.get("endDate").toString()));
    		returnSymbol.setCount(Integer.valueOf(object.get("count").toString()));
    	}
		return returnSymbol;
	}

	@Override
	public List<Symbol> getSymbols() {
		List<Symbol> symbols = new ArrayList<Symbol>();
    	GroupOperation groupBy = Aggregation.group("symbol").count().as("count").min("date").as("startDate").max("date").as("endDate");
    	Aggregation aggregation = Aggregation.newAggregation(groupBy);
    	AggregationResults<DBObject> results = mongoTemplate.aggregate(aggregation, "quote", DBObject.class);
    	Iterator<DBObject> iterators = results.iterator();
    	DBObject object = null;
    	Symbol symbol = null;
    	while (iterators.hasNext()) {
    		object = iterators.next();
    		symbol = new Symbol(object.get("_id").toString(), new Date(object.get("startDate").toString()), new Date(object.get("endDate").toString()), Integer.valueOf(object.get("count").toString()));
    		symbols.add(symbol);
    	}
		return symbols;
	}
	
	@Override
	public List<Quote> getQuote(String symbol) {
		List<Quote> quotes = null;
		quotes = quoteRepository.findBySymbolAndType(symbol, QUOTE_TYPE_RAW, new Sort(Sort.Direction.ASC, "date"));
		return quotes;
	}
}
