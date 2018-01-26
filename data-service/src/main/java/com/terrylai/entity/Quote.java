package com.terrylai.entity;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class Quote {
	
	@Id
	private String id;
    
	private String symbol;
	private Date date;
	private String type;
    private BigDecimal open;
    private BigDecimal low;
    private BigDecimal high;
    private BigDecimal close;
    
    private BigDecimal adjClose;
    
    private Long volume;
    
    public Quote() {}

    public Quote(String symbol, Date date, String type, BigDecimal high, BigDecimal low,  BigDecimal open,  BigDecimal close, BigDecimal adjClose, Long volume) {
    	this.symbol = symbol;
    	this.date = date;
    	
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = dateFormat.format(this.date);
    	this.id = symbol + "@" + dateStr;
    	this.type = type;
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
   
    public Date getDate() {
    	return date;    	
    }
    
    public String getType() {
    	return type;
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = dateFormat.format(this.date);
        return "@" + dateStr + ": " + this.low + "-" + this.high + ", " + 
                this.open + "->" + this.close + " (" + this.adjClose + ")";
    }
}