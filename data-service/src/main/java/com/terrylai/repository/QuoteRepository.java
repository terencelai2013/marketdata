package com.terrylai.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.terrylai.entity.Quote;

public interface QuoteRepository extends MongoRepository<Quote, String> {
	
	void deleteBySymbol(final String symbol);
	
	List<Quote> findBySymbol(final String symbol);

	List<Quote> findBySymbol(final String symbol, Sort sort);
}
