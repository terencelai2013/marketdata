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
public class MongoParserService implements ParserService {
	
	@Autowired
	QuoteRepository quoteRepository;
	
	@Autowired
	SymbolRepository symbolRepository;

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		quoteRepository.deleteAll();
	}

	@Override
	public int getAll() {
		// TODO Auto-generated method stub
		int retValue = 0;
		List<Quote> quotes = quoteRepository.findAll();
		if (quotes != null) {
			retValue = quotes.size();
		}
		return retValue;
	}

	@Override
	public int parse(String symbol) {
		// TODO Auto-generated method stub
		Historical historical;
		List<Quote> quotes;
		Symbol sym;
		int retValue = 0;
		
		historical = Parser.parse(symbol);
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
