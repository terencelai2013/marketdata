package com.terrylai.service;

import com.terrylai.entity.Symbol;

public interface ParserService {
	public void reset();

	public void reset(String symbol);

	public int get(String symbol);

	public int parse(String symbol, int period);

	public Symbol getSymbol(String symbol);
}
