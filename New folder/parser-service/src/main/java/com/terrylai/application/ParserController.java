package com.terrylai.application;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.terrylai.service.ParserService;

@RestController
public class ParserController {

    private final ParserService service;
    
	protected Logger logger = Logger.getLogger(ParserController.class.getName());

    @Autowired
    ParserController(ParserService service) {
        this.service = service;
    }
    
	@RequestMapping("/reset")
	public Data reset() {
		logger.info("parser-service parse() invoked: " + "NA");
		service.reset();
		Data data = new Data("NA", 0);
		logger.info("parser-service parse() finished: " + 0);
		return data;
	}

	@RequestMapping("/findAll")
	public Data findAll() {
		logger.info("parser-service parse() invoked: " + "ALL");
		int size = service.getAll();
		Data data = new Data("ALL", size);
		logger.info("parser-service parse() finished: " + size);
		return data;
	}

	@RequestMapping("/parse/{symbol}")
	public Data parse(@PathVariable("symbol") String symbol) {
		logger.info("parser-service parse(symbol) invoked: " + symbol);
		Data data;
		int size = service.parse(symbol);
		data = new Data(symbol, size);
		logger.info("parser-service parse(symbol) finished: " + size);
		return data;
	}
}
