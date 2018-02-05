package com.terrylai.service;

import com.terrylai.entity.Symbol;

public interface ParserService {
	public void reset();

	public void reset(String symbol);

	public long get(String symbol);

	public long parse(String symbol, int period);

	public Symbol getSymbol(String symbol);
}
