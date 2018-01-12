package com.terrylai.entities;

import java.util.List;

public class Historical {

	private Symbol symbol;
	private List<Quote> quotes;
	
	public Historical() {
		
	}
	
	public Historical(Symbol symbol, List<Quote> quotes) {
		this.symbol = symbol;
		this.quotes = quotes;
	}
	
	public Symbol getSymbol() {
		return symbol;
	}
	
	public void setSymbol(Symbol symbol) {
		this.symbol = symbol;
	}
	
	public List<Quote> getQuotes() {
		return quotes;
	}
	
	public void setQuotes(List<Quote> quotes) {
		this.quotes = quotes;
	}	
 }
