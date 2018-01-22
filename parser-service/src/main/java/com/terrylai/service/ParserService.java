package com.terrylai.service;

public interface ParserService {
	public void reset();
	public int get(String symbol);
	public int parse(String symbol, int period);
}
