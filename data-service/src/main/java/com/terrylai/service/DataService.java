package com.terrylai.service;

import java.util.List;

import com.terrylai.entity.Quote;
import com.terrylai.entity.Symbol;

public interface DataService {

	public void reset();

	public void reset(String symbol);

	public Symbol getSymbol(String symbol);

	public List<Symbol> getSymbols();

	public List<Quote> getQuote(String symbol);

	public long retrieve(String symbol);
}