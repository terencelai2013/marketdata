package com.terrylai.response;

import java.io.Serializable;

public final class Name implements Serializable {

	private static final long serialVersionUID = 1L;

	private String symbol;
	
	private String start;
	
	private String end;

	private Integer count;
	
	public Name(String symbol, String start, String end, Integer count) {
		this.symbol = symbol;
		this.start = start;
		this.end = end;
		this.count = count;
	}
	
	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}	
	
}