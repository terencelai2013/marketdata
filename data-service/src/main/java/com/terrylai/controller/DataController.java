package com.terrylai.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.terrylai.entity.Quote;
import com.terrylai.entity.Symbol;
import com.terrylai.service.DataService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;

@RestController
@RequestMapping(value="/symbol")
@Api(value = "Data", description = "Data Service API")
public class DataController {
	@Autowired
	DataService service;

	private static final Logger LOGGER = LoggerFactory.getLogger(DataController.class);

	private static final String CONSTANT_SYMBOL_ALL = "ALL";

	@ApiOperation(value = "resetAll"
			, nickname = "reset all symbols"
					,authorizations = @Authorization(value = "api_key")
			)
	 @ApiResponses(value = {
		        @ApiResponse(code = 500, message = "Server error"),
		        @ApiResponse(code = 404, message = "Service not found"),
		        @ApiResponse(code = 200, message = "Successful retrieval",
		            response = Symbol.class, responseContainer = "Array") })
	@RequestMapping(method = RequestMethod.GET, value = "/reset")
	public Symbol[] resetAll() {
		LOGGER.info("parser-service reset() invoked: [symbol,ALL]");
		service.reset();
		Symbol[] retSymbol = getSymbolsInfo();
		LOGGER.info("parser-service reset() finished: " + retSymbol.length);
		return retSymbol;
	}
	
	@ApiOperation(value = "resetAll"
			, nickname = "reset all symbols"
					,authorizations = @Authorization(value = "api_key")
			)
	 @ApiResponses(value = {
		        @ApiResponse(code = 500, message = "Server error"),
		        @ApiResponse(code = 404, message = "Service not found"),
		        @ApiResponse(code = 200, message = "Successful retrieval",
		            response = Symbol.class) })
	@RequestMapping(method = RequestMethod.GET, value = "/reset/{symbol}")
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
	@ApiOperation(value = "info"
			, nickname = "info all symbols"
					,authorizations = @Authorization(value = "api_key")
			)
	 @ApiResponses(value = {
		        @ApiResponse(code = 500, message = "Server error"),
		        @ApiResponse(code = 404, message = "Service not found"),
		        @ApiResponse(code = 200, message = "Successful retrieval",
		            response = Symbol.class) })
	@RequestMapping(method = RequestMethod.GET, value = "/info/{symbol}")
	public Symbol getSymbolInfo(@PathVariable("symbol") String symbol) {
		LOGGER.info("dataService getSymbol() invoked: [symbol, " + symbol + "]");
		Symbol returnSymbol = service.getSymbol(symbol);
		LOGGER.info("dataService getSymbol() finished: ");
		return returnSymbol;
	}
	
	@ApiOperation(value = "info"
			, nickname = "info all symbols"
					,authorizations = @Authorization(value = "api_key")
			)
	 @ApiResponses(value = {
		        @ApiResponse(code = 500, message = "Server error"),
		        @ApiResponse(code = 404, message = "Service not found"),
		        @ApiResponse(code = 200, message = "Successful retrieval",
		            response = Symbol.class, responseContainer = "Array") })
	@RequestMapping(method = RequestMethod.GET, value = "/info")
	public Symbol[] getSymbolsInfo() {
		LOGGER.info("dataService getSymbols() invoked");
		List<Symbol> symbols = service.getSymbols();
		Symbol[] returnSymbols = new Symbol[symbols.size()];
		LOGGER.info("dataService getSymbols() finished: " + returnSymbols.length);
		return symbols.toArray(returnSymbols);
	}

	@ApiOperation(value = "get"
			, nickname = "get all symbols"
					,authorizations = @Authorization(value = "api_key")
			)
	 @ApiResponses(value = {
		        @ApiResponse(code = 500, message = "Server error"),
		        @ApiResponse(code = 404, message = "Service not found"),
		        @ApiResponse(code = 200, message = "Successful retrieval",
		            response = Quote.class, responseContainer = "Array") })
	@RequestMapping(method = RequestMethod.GET, value = "/list/{symbol}")
	public Quote[] getQuote(@PathVariable("symbol") String symbol) {
		LOGGER.info("dataService bySymbol() invoked: [symbol, " + symbol + "]");
		List<Quote> quotes = service.getQuote(symbol);
		Quote[] returnQuotes = new Quote[quotes.size()];
		LOGGER.info("dataService bySymbol() finished: " + returnQuotes.length);
		return quotes.toArray(returnQuotes);
	}
	
	@ApiOperation(value = "retrieve"
			, nickname = "retrieve all symbols"
					,authorizations = @Authorization(value = "api_key")
			)
	 @ApiResponses(value = {
		        @ApiResponse(code = 500, message = "Server error"),
		        @ApiResponse(code = 404, message = "Service not found"),
		        @ApiResponse(code = 200, message = "Successful retrieval",
		            response = String.class) })
	@RequestMapping(method = RequestMethod.GET, value ="/add/{symbol}")
	public String retrieveQuote(@PathVariable("symbol") String symbol) {
		LOGGER.info("dataService bySymbol() invoked: [symbol, " + symbol + "]");
		long retValue = service.retrieve(symbol);
		LOGGER.info("dataService bySymbol() finished: " + retValue);
		return (retValue == 0)?"Received":"Failure";
	}
}
