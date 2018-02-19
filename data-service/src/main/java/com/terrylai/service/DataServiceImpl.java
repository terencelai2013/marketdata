package com.terrylai.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.mongodb.DBObject;
import com.terrylai.constant.MongoConstants;
import com.terrylai.entity.Quote;
import com.terrylai.entity.Symbol;
import com.terrylai.repository.QuoteRepository;

@Service
@EnableMongoRepositories(basePackageClasses = QuoteRepository.class)
public class DataServiceImpl implements DataService, MongoConstants {

	private static final Logger LOGGER = LoggerFactory.getLogger(DataServiceImpl.class);

	@Value("${queue.marketdata}")
	private String queueName;
	@Autowired
	QuoteRepository quoteRepository;

	@Autowired
	MongoTemplate mongoTemplate;

	@Autowired
	private JmsTemplate jmsTemplate;

	@Override
	public void reset() {
		quoteRepository.deleteAll();
	}

	@Override
	public void reset(String symbol) {
		quoteRepository.deleteBySymbol(symbol);
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

	@Override
	public List<Symbol> getSymbols() {
		GroupOperation groupBy = Aggregation.group(FIELD_KEY_SYMBOL).count().as(OUTPUT_FIELD_KEY_COUNT)
				.min(FIELD_KEY_DATE).as(OUTPUT_FIELD_KEY_START_DATE).max(FIELD_KEY_DATE).as(OUTPUT_FIELD_KEY_END_DATE);
		Aggregation aggregation = Aggregation.newAggregation(groupBy, Aggregation.sort(Sort.Direction.ASC, Aggregation.previousOperation()));
		AggregationResults<DBObject> results = mongoTemplate.aggregate(aggregation, COLLECTION_QUOTE, DBObject.class);

		List<DBObject> dbObjects = results.getMappedResults();
		List<Symbol> symbols = dbObjects.parallelStream()
				.map(s -> {
			return new Symbol(s.get(OUTPUT_FIELD_KEY_ID).toString(), 
					s.get(OUTPUT_FIELD_KEY_START_DATE).toString(), 
					s.get(OUTPUT_FIELD_KEY_END_DATE).toString(),
					Integer.valueOf(s.get(OUTPUT_FIELD_KEY_COUNT).toString()));
		}).collect(Collectors.toList());

		return symbols;
	}

	@Override
	public List<Quote> getQuote(String symbol) {
		List<Quote> quotes = null;
		quotes = quoteRepository.findBySymbol(symbol, new Sort(Sort.Direction.ASC, FIELD_KEY_DATE));
		return quotes;
	}

	@Override
	public long retrieve(String symbol) {
		LOGGER.info("sending message='{}' to destination='{}'", symbol, queueName);
		jmsTemplate.convertAndSend(queueName, symbol);
		return 0;
	}
}
