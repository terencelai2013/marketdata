package com.terrylai.service;

import java.util.List;

import com.terrylai.entity.Quote;

public interface DataService {
	public List<Quote> get(String symbol);
}