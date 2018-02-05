package com.terrylai.response;

import java.io.Serializable;

public final class Data implements Serializable {

	private static final long serialVersionUID = 1L;

	private String symbol;

	private Long size;

	public Data(String symbol, Long size) {
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

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	@Override
	public String toString() {
		return "Data [symbol=" + symbol + ", size=" + size + "]";
	}

}