package com.terrylai.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Symbol implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonIgnore
	private static final String CONSTANT_DATE_FORMAT = "yyyy-MM-dd";

	private String name;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = CONSTANT_DATE_FORMAT)
	private Date start;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = CONSTANT_DATE_FORMAT)
	private Date end;

	private Integer count;

	public Symbol(String name, Date start, Date end, Integer count) {
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

	@JsonIgnore
	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	@JsonIgnore
	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
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
