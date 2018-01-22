package com.terrylai.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.terrylai.entity.Historical;
import com.terrylai.entity.Quote;
import com.terrylai.entity.Symbol;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

public class Parser {
	public static Historical parse(String symbol, int before) {
		Historical historical = new Historical();

		try {
			Calendar from = Calendar.getInstance();
			Calendar to = Calendar.getInstance();
			from.add(Calendar.MONTH, before);

			Stock stock = YahooFinance.get(symbol, from, to, Interval.DAILY);

			List<HistoricalQuote> historicalQuotes = stock.getHistory();

			HistoricalQuote historicalQuote = null;
			
			List<Quote> quotes = new ArrayList<Quote>();
			
			Symbol sym = null;
			Quote quote; 
			boolean isFirst = true;
			Date startDate = null;
			Iterator iterator = historicalQuotes.iterator();
			while (iterator.hasNext()) {
				historicalQuote = (HistoricalQuote) iterator.next();
				if (isFirst) {
					startDate = historicalQuote.getDate().getTime();
					isFirst = false;
				}
				quote = new Quote(historicalQuote.getSymbol(), 
						historicalQuote.getDate().getTime(), 
						historicalQuote.getHigh(),
						historicalQuote.getLow(),
						historicalQuote.getOpen(),
						historicalQuote.getClose(),
						historicalQuote.getAdjClose(),
						historicalQuote.getVolume());
				quotes.add(quote);
			}
			if (historicalQuote != null) {
				sym = new Symbol(historicalQuote.getSymbol(), startDate, historicalQuote.getDate().getTime());	
			}
			historical.setSymbol(sym);
			historical.setQuotes(quotes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return historical;
	}
}
