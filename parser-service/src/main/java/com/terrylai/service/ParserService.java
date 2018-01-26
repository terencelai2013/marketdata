package com.terrylai.service;

public interface ParserService {
	public void reset();
	public void reset(String symbol);
	public int get(String symbol);
	public int parse(String symbol, int period);
}
