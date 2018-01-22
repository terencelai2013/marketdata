package com.terrylai.controller;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.terrylai.entity.Quote;
import com.terrylai.response.Data;
import com.terrylai.service.DataService;

@RestController
public class DataController {

	@Autowired
	DataService service;
	
	protected Logger logger = Logger.getLogger(DataController.class.getName());

	@RequestMapping("/raw/{symbol}")
	public Data[] bySymbol(@PathVariable("symbol") String symbol) {
		logger.info("dataService bySymbol() invoked: [symbol, " + symbol + "]");
		List<Quote> quotes = service.get(symbol);
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
