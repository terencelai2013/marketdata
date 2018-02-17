package com.terrylai.processor;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.terrylai.constant.CommonConstants;
import com.terrylai.entity.Quote;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

public class HistoricalQuoteProcessor implements CommonConstants {

	private static final Logger LOGGER = LoggerFactory.getLogger(HistoricalQuoteProcessor.class);

	public static List<Quote> parse(String symbol, Calendar startDate) {
		List<Quote> quotes = new ArrayList<Quote>();
		try {
			if (startDate == null) {
				startDate = Calendar.getInstance();
				startDate.set(Calendar.YEAR, 2000);
				startDate.set(Calendar.MONTH, Calendar.JANUARY);
				startDate.set(Calendar.DAY_OF_MONTH, 1);
				startDate.set(Calendar.HOUR_OF_DAY, 0);
				startDate.set(Calendar.MINUTE, 0);
				startDate.set(Calendar.SECOND, 0);
			}
			long t0 = System.nanoTime();

			Stock stock = YahooFinance.get(symbol, startDate, Interval.DAILY);

			long t1 = System.nanoTime();
			long millis1 = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
			LOGGER.info(String.format("time to retrieve data: %d ms", millis1));

			List<HistoricalQuote> historicalQuotes = stock.getHistory();

			quotes = historicalQuotes.parallelStream().map(p -> {
				return new Quote(p.getSymbol(),
						new SimpleDateFormat(CONSTANT_DATE_FORMAT).format(p.getDate().getTime()),
//						String.valueOf(p.getDate().get(Calendar.YEAR)) + "-"
//								+ String.valueOf(101 + p.getDate().get(Calendar.MONTH)).substring(1) + "-"
//								+ String.valueOf(100 + p.getDate().get(Calendar.DAY_OF_MONTH)).substring(1),
						p.getHigh(), p.getLow(), p.getOpen(), p.getClose(), p.getAdjClose(), p.getVolume());
			}).collect(Collectors.toList());

			long t3 = System.nanoTime();
			long millis2 = TimeUnit.NANOSECONDS.toMillis(t3 - t1);
			LOGGER.info(String.format("time to convert data: %d ms", millis2));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return quotes;
	}
}
