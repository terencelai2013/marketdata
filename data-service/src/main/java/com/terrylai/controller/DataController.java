package com.terrylai.controller;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.terrylai.entity.Quote;
import com.terrylai.entity.Symbol;
import com.terrylai.service.DataService;

@RestController
public class DataController {
	@Autowired
	DataService service;

	protected Logger logger = Logger.getLogger(DataController.class.getName());

	@RequestMapping("/info/symbol/{symbol}")
	public Symbol getSymbolInfo(@PathVariable("symbol") String symbol) {
		logger.info("dataService getSymbol() invoked: [symbol, " + symbol + "]");
		Symbol returnSymbol = service.getSymbol(symbol);
		logger.info("dataService getSymbol() finished: ");
		return returnSymbol;
	}

	@RequestMapping("/info/symbol")
	public Symbol[] getSymbolsInfo() {
		logger.info("dataService getSymbols() invoked");
		List<Symbol> symbols = service.getSymbols();
		Symbol[] returnSymbols = new Symbol[symbols.size()];
		logger.info("dataService getSymbols() finished: " + returnSymbols.length);
		return symbols.toArray(returnSymbols);
	}

	@RequestMapping("/raw/{symbol}")
	public Quote[] getSymbol(@PathVariable("symbol") String symbol) {
		logger.info("dataService bySymbol() invoked: [symbol, " + symbol + "]");
		List<Quote> quotes = service.getQuote(symbol);
		Quote[] returnQuotes = new Quote[quotes.size()];
		logger.info("dataService bySymbol() finished: " + returnQuotes.length);
		return quotes.toArray(returnQuotes);
	}
}
