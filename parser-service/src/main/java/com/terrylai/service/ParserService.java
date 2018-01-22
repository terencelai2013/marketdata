package com.terrylai.service;

public interface ParserService {
	public void reset();
	public int size();
	public int parse(String symbol, int period);
}
