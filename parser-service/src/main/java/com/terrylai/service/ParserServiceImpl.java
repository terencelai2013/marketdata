package com.terrylai.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import com.terrylai.entities.Historical;
import com.terrylai.entities.Quote;
import com.terrylai.entities.Symbol;
import com.terrylai.parser.Parser;
import com.terrylai.repositories.QuoteRepository;
import com.terrylai.repositories.SymbolRepository;

@Service
@EnableMongoRepositories(basePackageClasses = QuoteRepository.class)
public class ParserServiceImpl implements ParserService {

	@Autowired
	QuoteRepository quoteRepository;
	
	@Autowired
	SymbolRepository symbolRepository;

	@Override
	public void reset() {
		quoteRepository.deleteAll();
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
		int retValue = 0;
		
		Historical historical;
		List<Quote> quotes;
		Symbol sym;
		
		historical = Parser.parse(symbol, period);
		sym = historical.getSymbol();
		quotes = historical.getQuotes();
		if (quotes != null && quotes.size() > 0) {
			
			Iterator<Quote> iterator = quotes.iterator();

			Quote quote;
			while (iterator.hasNext()) {
				quote = (Quote) iterator.next();
				quoteRepository.save(quote);
			}
			if (sym != null) {
				symbolRepository.save(sym);
			}
			retValue = quotes.size();			
		}
		return retValue;
	}
	
}
