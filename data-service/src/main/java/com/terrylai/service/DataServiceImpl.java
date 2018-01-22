package com.terrylai.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import com.terrylai.entity.Quote;
import com.terrylai.entity.Symbol;
import com.terrylai.repository.QuoteRepository;
import com.terrylai.repository.SymbolRepository;
import com.terrylai.response.Name;

@Service
@EnableMongoRepositories(basePackageClasses = QuoteRepository.class)
public class DataServiceImpl implements DataService {

	@Autowired
	QuoteRepository quoteRepository;
	
	@Autowired
	SymbolRepository symbolRepository;
	
	@Override
	public List<Symbol> getSymbols() {
		List<Symbol> symbols = null;
		symbols = symbolRepository.findAll();
		return symbols;
	}
	@Override
	public List<Quote> getQuote(String symbol) {
		List<Quote> quotes = null;
		quotes = quoteRepository.findBySymbol(symbol);
		return quotes;
	}
}
