package com.terrylai.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.terrylai.response.Data;
import com.terrylai.service.ParserService;

@RestController
public class ParserController {
	
	static final String CONSTANT_SYMBOL_ALL = "ALL";
	
	@Value( "${default.period}" )
	private int DEFAULT_PERIOD;
	
	@Autowired
	ParserService service;
	
	protected Logger logger = Logger.getLogger(ParserController.class.getName());
	
	@RequestMapping("/reset")
	public Data resetAll() {
		logger.info("parser-service reset() invoked: [symbol,ALL]");
			service.reset();
		Data data = getSymbol(CONSTANT_SYMBOL_ALL);		
		logger.info("parser-service reset() finished: " + data.getSize());
		return data;
	}
	
	@RequestMapping("/reset/{symbol}")
	public Data reset(@PathVariable("symbol") String symbol) {
		logger.info("parser-service reset(symbol) invoked: [symbol," + symbol + "]");
		if (symbol != null && symbol.equals(CONSTANT_SYMBOL_ALL)) {
			service.reset();
		} else {
			service.reset(symbol);
		}
		Data data = getSymbol(symbol);		
		logger.info("parser-service reset(symbol) finished: " + data.getSize());
		return data;
	}
	
	@RequestMapping("/size")
	public Data get() {
		return getSymbol(null);
	}

	@RequestMapping("/size/{symbol}")
	public Data get(@PathVariable("symbol") String symbol) {
		return getSymbol(symbol);
	}
	
	private Data getSymbol(String symbol) {
		logger.info("parser-service get() invoked: [symbol," + symbol + "]");		
		long retValue = service.get(symbol);
		String retSymbol = symbol;
		if (retSymbol == null) {
			retSymbol = CONSTANT_SYMBOL_ALL;
		}
		Data data = new Data(retSymbol, retValue);	
		logger.info("parser-service get() finished: " + retValue);
		return data;
	}
	
	@RequestMapping("/parse/{symbol}")
	public Data parse(@PathVariable("symbol") String symbol) {
		return parseSymbol(symbol, DEFAULT_PERIOD);
	}
	
	@RequestMapping("/parse/{symbol}/{period}")
	public Data parse(@PathVariable("symbol") String symbol, @PathVariable("period") Integer period) {
		return parseSymbol(symbol, period);
	}
	
	private Data parseSymbol(String symbol, Integer period) {
		logger.info("parser-service parse(symbol, period) invoked: [symbol," + symbol + "] ,[period," + period + "]");
		if (period > 0) period *= -1;
		long size = service.parse(symbol, period);
		Data data = new Data(symbol, size);
		logger.info("parser-service parse(symbol, period) finished: " + size);
		return data;
	}
}
