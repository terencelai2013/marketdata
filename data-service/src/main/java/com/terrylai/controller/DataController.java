package com.terrylai.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.terrylai.entity.Quote;
import com.terrylai.entity.Symbol;
import com.terrylai.service.DataService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class DataController {
	@Autowired
	DataService service;

	private static final Logger LOGGER = LoggerFactory.getLogger(DataController.class);

	private static final String CONSTANT_SYMBOL_ALL = "ALL";

	@ApiOperation(value = "resetAll"
			, nickname = "reset all symbols"
			)
	 @ApiResponses(value = {
		        @ApiResponse(code = 500, message = "Server error"),
		        @ApiResponse(code = 404, message = "Service not found"),
		        @ApiResponse(code = 200, message = "Successful retrieval",
		            response = DataController.class, responseContainer = "List") })
	@RequestMapping("/reset")
	public Symbol[] resetAll() {
		LOGGER.info("parser-service reset() invoked: [symbol,ALL]");
		service.reset();
		Symbol[] retSymbol = getSymbolsInfo();
		LOGGER.info("parser-service reset() finished: " + retSymbol.length);
		return retSymbol;
	}

	@RequestMapping("/reset/{symbol}")
	public Symbol reset(@PathVariable("symbol") String symbol) {
		LOGGER.info("parser-service reset(symbol) invoked: [symbol," + symbol + "]");
		if (symbol != null && symbol.equals(CONSTANT_SYMBOL_ALL)) {
			service.reset();
		} else {
			service.reset(symbol);
		}
		Symbol retSymbol = getSymbolInfo(symbol);
		LOGGER.info("parser-service reset(symbol) finished: " + retSymbol.getCount());
		return retSymbol;
	}

	@RequestMapping("/info/{symbol}")
	public Symbol getSymbolInfo(@PathVariable("symbol") String symbol) {
		LOGGER.info("dataService getSymbol() invoked: [symbol, " + symbol + "]");
		Symbol returnSymbol = service.getSymbol(symbol);
		LOGGER.info("dataService getSymbol() finished: ");
		return returnSymbol;
	}

	@RequestMapping("/info")
	public Symbol[] getSymbolsInfo() {
		LOGGER.info("dataService getSymbols() invoked");
		List<Symbol> symbols = service.getSymbols();
		Symbol[] returnSymbols = new Symbol[symbols.size()];
		LOGGER.info("dataService getSymbols() finished: " + returnSymbols.length);
		return symbols.toArray(returnSymbols);
	}

	@RequestMapping("/get/{symbol}")
	public Quote[] getQuote(@PathVariable("symbol") String symbol) {
		LOGGER.info("dataService bySymbol() invoked: [symbol, " + symbol + "]");
		List<Quote> quotes = service.getQuote(symbol);
		Quote[] returnQuotes = new Quote[quotes.size()];
		LOGGER.info("dataService bySymbol() finished: " + returnQuotes.length);
		return quotes.toArray(returnQuotes);
	}
	
	@RequestMapping("/retrieve/{symbol}")
	public String retrieveQuote(@PathVariable("symbol") String symbol) {
		LOGGER.info("dataService bySymbol() invoked: [symbol, " + symbol + "]");
		long retValue = service.retrieve(symbol);
		LOGGER.info("dataService bySymbol() finished: " + retValue);
		return (retValue == 0)?"Received":"Failure";
	}
}
