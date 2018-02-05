package com.terrylai.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class Quote implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonIgnore
	private static final String CONSTANT_DATE_FORMAT = "yyyy-MM-dd";
	
	@JsonIgnore
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(CONSTANT_DATE_FORMAT);
	
	@Id
	private String id;
    
	private String symbol;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = CONSTANT_DATE_FORMAT)
	private Date date;
	
	@JsonIgnore
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
    	this.id = symbol + "@" + dateFormat.format(this.date);
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
    
    public void setDate(Date date) {
		this.date = date;
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
        return this.getClass().getSimpleName() + " - " + this.symbol + "@" + dateFormat.format(this.date) + ": " + this.low + "-" + this.high + ", " + 
                this.open + "->" + this.close + " (" + this.adjClose + ")";
    }
}