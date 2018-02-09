package com.terrylai.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
			long t0 = System.nanoTime();
			
			Stock stock = YahooFinance.get(symbol, from, to, Interval.DAILY);
			
			long t1 = System.nanoTime();
			long millis1 = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
			System.out.println(String.format("time to retrieve data: %d ms", millis1));
			
			List<HistoricalQuote> historicalQuotes = stock.getHistory();

			quotes = historicalQuotes.parallelStream()
			.map(p -> { return new Quote(p.getSymbol(), 
						p.getDate().getTime(), 
						QUOTE_TYPE_RAW,
						p.getHigh(),
						p.getLow(),
						p.getOpen(),
						p.getClose(),
						p.getAdjClose(),
						p.getVolume()); })
			.collect(Collectors.toList());
			
			long t3 = System.nanoTime();
			long millis2 = TimeUnit.NANOSECONDS.toMillis(t3 - t1);
			System.out.println(String.format("time to convert data: %d ms", millis2));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return quotes;
	}
}
