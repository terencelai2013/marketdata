package com.terrylai.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import com.terrylai.entity.Quote;
import com.terrylai.repository.QuoteRepository;

@Service
@EnableMongoRepositories(basePackageClasses = QuoteRepository.class)
public class DataServiceImpl implements DataService {

	@Autowired
	QuoteRepository quoteRepository;
	
	@Override
	public List<Quote> get(String symbol) {
		List<Quote> quotes = null;
		quotes = quoteRepository.findBySymbol(symbol);
		return quotes;
	}

}
