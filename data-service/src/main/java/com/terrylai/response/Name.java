package com.terrylai.response;

import java.io.Serializable;
import java.util.Date;

public final class Name implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	
	private Date start;
	
	private Date end;

	public Name(String name, Date start, Date end) {
		this.name = name;
		this.start = start;
		this.end = end;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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