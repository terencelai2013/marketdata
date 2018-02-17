package com.terrylai.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import org.springframework.data.annotation.Id;

public final class Quote implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
    
	private String symbol;

	private String date;

    private BigDecimal open;
    private BigDecimal low;
    private BigDecimal high;
    private BigDecimal close;    
    private BigDecimal adjClose;    
    private Long volume;
    
    public Quote() {}

    public Quote(String symbol, String date, BigDecimal high, BigDecimal low,  BigDecimal open,  BigDecimal close, BigDecimal adjClose, Long volume) {
    	this.symbol = symbol;
    	this.date = date;
    	this.id = symbol + "@" + this.date;
        this.open = open;
        this.low = low;
        this.high = high;
        this.close = close;
        this.adjClose = adjClose;
        this.volume = volume;
    }

    public String getSymbol() {
    	return symbol;
    }
   
    public String getDate() {
    	return date;    	
    }
    
    public void setDate(String date) {
		this.date = date;
	}

    public BigDecimal getOpen() {
        return open;
    }

    public void setOpen(BigDecimal open) {
        this.open = open;
    }

    public BigDecimal getLow() {
        return low;
    }

    public void setLow(BigDecimal low) {
        this.low = low;
    }

    public BigDecimal getHigh() {
        return high;
    }

    public void setHigh(BigDecimal high) {
        this.high = high;
    }

    public BigDecimal getClose() {
        return close;
    }

    public void setClose(BigDecimal close) {
        this.close = close;
    }

    public BigDecimal getAdjClose() {
        return adjClose;
    }

    public void setAdjClose(BigDecimal adjClose) {
        this.adjClose = adjClose;
    }

    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " - " + this.id + ": " + this.low + "-" + this.high + ", " + 
                this.open + "->" + this.close + " (" + this.adjClose + ")";
    }
}