package com.terrylai.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import com.terrylai.entity.Quote;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

public class Parser {
	public static final String QUOTE_TYPE_RAW = "raw";
	
	public static List<Quote> parse(String symbol, int before) {
		List<Quote> quotes = new ArrayList<Quote>();
		try {
			Calendar from = Calendar.getInstance();
			Calendar to = Calendar.getInstance();
			from.add(Calendar.MONTH, before);

			Stock stock = YahooFinance.get(symbol, from, to, Interval.DAILY);

			List<HistoricalQuote> historicalQuotes = stock.getHistory();

			HistoricalQuote historicalQuote = null;
			
			Quote quote; 
			Iterator<HistoricalQuote> iterator = historicalQuotes.iterator();
			while (iterator.hasNext()) {
				historicalQuote = (HistoricalQuote) iterator.next();
				quote = new Quote(historicalQuote.getSymbol(), 
						historicalQuote.getDate().getTime(), 
						QUOTE_TYPE_RAW,
						historicalQuote.getHigh(),
						historicalQuote.getLow(),
						historicalQuote.getOpen(),
						historicalQuote.getClose(),
						historicalQuote.getAdjClose(),
						historicalQuote.getVolume());
				quotes.add(quote);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return quotes;
	}
}
