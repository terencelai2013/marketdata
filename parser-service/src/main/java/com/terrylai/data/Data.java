package com.terrylai.data;

import java.io.Serializable;

public final class Data implements Serializable{

	private static final long serialVersionUID = 1L;

	private String symbol;
	
	private Integer size;
	
	public Data(String symbol, Integer size) {
		super();
		this.symbol = symbol;
		this.size = size;
	}
	
	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	@Override
	public String toString() {
		return "Data [symbol=" + symbol + ", size=" + size + "]";
	}

	
}