package com.terrylai.application;

import java.util.List;
import java.util.Iterator;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.terrylai.entities.Historical;
import com.terrylai.entities.Quote;
import com.terrylai.entities.Symbol;
import com.terrylai.parser.Parser;
import com.terrylai.repositories.QuoteRepository;
import com.terrylai.repositories.SymbolRepository;

@RestController
@EnableMongoRepositories(basePackageClasses = QuoteRepository.class)
public class QuoteController {
	
	@Autowired
	QuoteRepository quoteRepository;
	
	@Autowired
	SymbolRepository symbolRepository;	
	
	protected Logger logger = Logger.getLogger(QuoteController.class.getName());
	
	@RequestMapping("/reset")
	public Data reset() {
		logger.info("parser-service parse() invoked: " + "NA");		
		quoteRepository.deleteAll();
		Data data = new Data("NA", 0);		
		logger.info("parser-service parse() finished: " + 0);
		return data;
	}
	
	@RequestMapping("/parse")
	public Data parse() {
		logger.info("parser-service parse() invoked: " + "ALL");		
		List<Quote> quotes = quoteRepository.findAll();
		Data data = new Data("ALL", quotes.size());		
		logger.info("parser-service parse() finished: " + quotes.size());
		return data;
	}
	
	@RequestMapping("/parse/{symbol}")
	public Data parse(@PathVariable("symbol") String symbol) {
		logger.info("parser-service parse(symbol) invoked: " + symbol);		
		Historical historical;
		List<Quote> quotes;
		Symbol sym;
		Data data;
		
		historical = Parser.parse(symbol);
		sym = historical.getSymbol();
		quotes = historical.getQuotes();
		if (quotes != null && quotes.size() > 0) {
			
			Iterator iterator = quotes.iterator();

			Quote quote;
			while (iterator.hasNext()) {
				quote = (Quote) iterator.next();
				quoteRepository.save(quote);
			}
			if (sym != null) {
				symbolRepository.save(sym);
			}
			data = new Data(symbol, quotes.size());
			logger.info("parser-service parse(symbol) finished: " + quotes.size());
			
		} else {
			
			data = new Data(symbol, 0);
			logger.info("parser-service parse(symbol) finished: 0");
			
		}



		return data;
	}
}
