package com.terrylai.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Symbol implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonIgnore
	private static final String CONSTANT_DATE_FORMAT = "yyyy-MM-dd";
	
	private String name;
	
	@JsonIgnore
	private Date start;
	
	@JsonIgnore
	private Date end;

	private String startDate;
	
	private String endDate;
		
	private Integer count;
	
	public Symbol(String name, Date start, Date end, Integer count) {
    	SimpleDateFormat dateFormat = new SimpleDateFormat(CONSTANT_DATE_FORMAT);
		this.name = name;
		this.start = start;
		this.end = end;
		this.startDate = dateFormat.format(this.start);
		this.endDate = dateFormat.format(this.end);
		this.count = count;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonIgnore
	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
		SimpleDateFormat dateFormat = new SimpleDateFormat(CONSTANT_DATE_FORMAT);
		this.startDate = dateFormat.format(this.start);
	}

	@JsonIgnore
	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;		
		SimpleDateFormat dateFormat = new SimpleDateFormat(CONSTANT_DATE_FORMAT);
		this.endDate = dateFormat.format(this.end);
		
	}
	
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}
