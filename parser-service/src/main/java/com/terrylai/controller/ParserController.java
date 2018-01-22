package com.terrylai.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.terrylai.data.Data;
import com.terrylai.service.ParserService;

@RestController
public class ParserController {
	
	private static int DEFAULT_PERIOD = -2;
	
	@Autowired
	ParserService service;
	
	protected Logger logger = Logger.getLogger(ParserController.class.getName());
	
	@RequestMapping("/reset")
	public Data reset() {
		logger.info("parser-service parse() invoked: " + "NA");		
		service.reset();
		Data data = new Data("NA", 0);		
		logger.info("parser-service parse() finished: " + 0);
		return data;
	}
	
	@RequestMapping("/size")
	public Data parse() {
		logger.info("parser-service parse() invoked: " + "ALL");		
		int retValue = service.size();
		Data data = new Data("ALL", retValue);	
		logger.info("parser-service parse() finished: " + retValue);
		return data;
	}
	
	@RequestMapping("/parse/{symbol}")
	public Data parse(@PathVariable("symbol") String symbol) {
		logger.info("parser-service parse(symbol) invoked: " + symbol);		
		int size = service.parse(symbol, DEFAULT_PERIOD);
		Data data = new Data(symbol, size);
		logger.info("parser-service parse(symbol) finished: " + size);
		return data;
	}
	
	@RequestMapping("/parse/{symbol}/{period}")
	public Data parse(@PathVariable("symbol") String symbol, @PathVariable("period") Integer period) {
		logger.info("parser-service parse(symbol) invoked: " + symbol);
		if (period > 0) period *= -1;
		int size = service.parse(symbol, period);
		Data data = new Data(symbol, size);
		logger.info("parser-service parse(symbol) finished: " + size);
		return data;
	}
}
