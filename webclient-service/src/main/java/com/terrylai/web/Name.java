package com.terrylai.web;

import java.io.Serializable;
import java.util.Date;

public final class Name implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String symbol;
	
	private Date start;
	
	private Date end;

	public Name() {
		super();
	}
	
	public Name(String symbol, Date start, Date end) {
		this.symbol = symbol;
		this.start = start;
		this.end = end;
	}
	
	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}
}
