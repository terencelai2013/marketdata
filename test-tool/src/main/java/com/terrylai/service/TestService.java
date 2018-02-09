package com.terrylai.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.terrylai.entity.Quote;

public interface TestService {
	
	public List<Quote> quoteBySymbol(String symbol);
	
	public Page<Quote> quoteBySymbol(String symbol, Pageable pageable);
}