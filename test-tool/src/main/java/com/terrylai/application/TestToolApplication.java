package com.terrylai.application;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

import com.mongodb.DBObject;
import com.terrylai.entity.Quote;
import com.terrylai.entity.Symbol;
import com.terrylai.service.DataService;
import com.terrylai.service.TestService;

@SpringBootApplication
@ComponentScan(basePackages = "com.terrylai")
public class TestToolApplication implements CommandLineRunner {
    @Autowired
    private ApplicationContext appContext;

    @Autowired
    private TestService service;
    
    @Autowired
    private DataService dataService;
    
	@Autowired
	MongoTemplate mongoTemplate;
	
	public static void main(String[] args) {
		SpringApplication.run(TestToolApplication.class, args);
		System.out.println("TERRY LAI");

	}
	
    @Override
    public void run(String... args) throws Exception {
//    	getSymbol("GOOG");
//    	getSymbol5("GOOG");
//    	System.out.println(dataService.getSymbol("GOOG"));
//    	getSymbols();
    	getClosing("GOOG");
        System.exit(0);
    }
    
    private void getClosing(String symbol) {
    	List<Double> closings = dataService.getClosing(symbol);
    	closings.stream().forEach(System.out::println);

    }
    
    private void getSymbols() {
    	List<Symbol> symbols = dataService.getSymbols();
    	for (int i = 0; i < symbols.size(); i++) {
    		System.out.println(symbols.get(i));
    	}
    }
    private void displayBeans() {
        String[] beans = appContext.getBeanDefinitionNames();
        Arrays.sort(beans);
        for (String bean : beans) {
            System.out.println(bean);
        }
    }
    
    private void getSymbol1(String symbol) {
//        TestService testService = appContext.getBean(TestService.class);
        List<Quote> quotes = dataService.getQuote(symbol);
        for (Quote quote: quotes) {
        	System.out.println("Quote:" + quote.getSymbol() + ":[date," + quote.getDate() + "] [close," + quote.getClose() + "]");
        }
    }
    
    private void getSymbol2(String symbol) {
//        TestService testService = appContext.getBean(TestService.class);
        List<Quote> quotes = service.quoteBySymbol(symbol);
        for (Quote quote: quotes) {
        	System.out.println("Quote:" + quote.getSymbol() + ":[date," + quote.getDate() + "] [close," + quote.getClose() + "]");
        }
    }
    
    private void getSymbol3(String symbol) {
    	GroupOperation groupBy = group("symbol").count().as("count").min("date").as("startDate").max("date").as("endDate");
    	Aggregation aggregation = newAggregation(groupBy);
    	AggregationResults<DBObject> results = mongoTemplate.aggregate(aggregation, "quote", DBObject.class);
    	Iterator<DBObject> iterators = results.iterator();
    	DBObject object = null;
    	while (iterators.hasNext()) {
    		object = iterators.next();
    		System.out.print("OBJECT:[_id, " + object.get("_id") + "] ");
    		System.out.print("[count, " + object.get("count") + "] ");
    		System.out.print("[startDate, " + object.get("startDate") + "] ");
    		System.out.println("[endDate, " + object.get("endDate") + "] ");
    	}
    }
    
    private void getSymbol4(String symbol) {
//        TestService testService = appContext.getBean(TestService.class);
        Page<Quote> pages = service.quoteBySymbol(symbol, new PageRequest(2, 10, new Sort(Sort.Direction.ASC, "date")));
        System.out.println("Total Page:" + pages.getTotalPages());
        for (Quote quote: pages) {
        	System.out.println("Quote:" + quote.getSymbol() + ":[date," + quote.getDate() + "] [close," + quote.getClose() + "]");
        }
    }
    
    private void getSymbol5(String symbol) {
    	Calendar cal = Calendar.getInstance();
    	cal.set(2018, Calendar.JANUARY, 1); //Year, month and day of month
    	Date after = cal.getTime();

    	System.out.println("After Date:" + after);
    	 List<Quote> quotes = dataService.getQuote(symbol);
    	 System.out.println("Number of record:" + quotes.size());
    	 List<Quote> filtered =
    			 quotes
    			        .stream()
    			        .filter(p -> p.getDate().after(after))
    			        .collect(Collectors.toList());
    	 System.out.println("Number of filtered record:" + filtered.size());
    }
}
