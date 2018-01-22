package com.terrylai.service;

import java.util.List;

import com.terrylai.entity.Quote;
import com.terrylai.entity.Symbol;

public interface DataService {
	public List<Symbol> getSymbols();
	public List<Quote> getQuote(String symbol);
}