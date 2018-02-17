package com.terrylai.service;

import com.terrylai.entity.Symbol;

public interface RetrieverService {
	public void reset();

	public void reset(String symbol);

	public long retrieve(String symbol);

	public Symbol getSymbol(String symbol);
}