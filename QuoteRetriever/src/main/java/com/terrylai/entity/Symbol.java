package com.terrylai.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Symbol implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonIgnore
	private static final String CONSTANT_DATE_FORMAT = "yyyy-MM-dd";

	private String name;

	private String start;

	private String end;

	private Integer count;

	public Symbol(String name, String start, String end, Integer count) {
		this.name = name;
		this.start = start;
		this.end = end;
		this.count = count;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " - [name, " + this.name + "], [count, " + this.count + "], [start, " + this.start + "], [end, " + 
                this.end + "]";
    }
}
