package com.terrylai.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import com.terrylai.entity.Quote;
import com.terrylai.parser.Parser;
import com.terrylai.repository.QuoteRepository;

@Service
@EnableMongoRepositories(basePackageClasses = QuoteRepository.class)
public class ParserServiceImpl implements ParserService {

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
		int retValue = 0;

		List<Quote> quotes = Parser.parse(symbol, period);
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
		return retValue;
	}
}
