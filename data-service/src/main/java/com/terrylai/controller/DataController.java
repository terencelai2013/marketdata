package com.terrylai.controller;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.terrylai.entity.Quote;
import com.terrylai.entity.Symbol;
import com.terrylai.response.Data;
import com.terrylai.response.Name;
import com.terrylai.service.DataService;

@RestController
public class DataController {

	@Autowired
	DataService service;
	
	protected Logger logger = Logger.getLogger(DataController.class.getName());

	@RequestMapping("/symbols") 
	public Name[] getAllSymbol() {
		logger.info("dataService getAllSymbol() invoked");
		List<Symbol> symbols = service.getSymbols();
		Name[] nameArray = new Name[symbols.size()];
		Name name = null;
		int i = 0;
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String startDateStr = null;
        String endDateStr = null;
		for (Symbol symbol: symbols) {
			startDateStr = dateFormat.format(symbol.getStart());
			endDateStr = dateFormat.format(symbol.getEnd());
			name = new Name(symbol.getName(), startDateStr, endDateStr);
			nameArray[i] = name;
			i++;
		}
		logger.info("dataService getAllSymbol() finished: " + nameArray.length);
		return nameArray;
	}
	
	@RequestMapping("/raw/{symbol}")
	public Data[] bySymbol(@PathVariable("symbol") String symbol) {
		logger.info("dataService bySymbol() invoked: [symbol, " + symbol + "]");
		List<Quote> quotes = service.getQuote(symbol);
		Data[] dataArray = new Data[quotes.size()];
		Data data = null;
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = null;
		int i = 0;
		for (Quote quote: quotes) {
			dateStr = dateFormat.format(quote.getDate());
			data = new Data(quote.getSymbol(), dateStr, quote.getOpen(), quote.getHigh(), quote.getLow(), quote.getClose(), quote.getAdjClose(), quote.getVolume());
			dataArray[i] = data;
			i++;
		}
		logger.info("dataService bySymbol() finished: " + dataArray.length);
		return dataArray;
	}
}
