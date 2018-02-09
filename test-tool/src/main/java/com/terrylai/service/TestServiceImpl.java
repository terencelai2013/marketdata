package com.terrylai.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import com.terrylai.constant.MongoConstants;
import com.terrylai.entity.Quote;
import com.terrylai.repository.QuoteRepository;

@Service
@EnableMongoRepositories(basePackageClasses = QuoteRepository.class)
public class TestServiceImpl implements TestService, MongoConstants {

	static final String QUOTE_TYPE_RAW = "raw";
	@Autowired
	QuoteRepository quoteRepository;

	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public List<Quote> quoteBySymbol(String symbol) {
		List<Quote> quotes = null;
		quotes = quoteRepository.findQuoteBySymbol(symbol, new Sort(Sort.Direction.ASC, "date"));
		return quotes;
	}

	@Override
	public Page<Quote> quoteBySymbol(String symbol, Pageable pageable) {
		Page<Quote> quotes = quoteRepository.findBySymbol(symbol, pageable);
		return quotes;
	}
}
