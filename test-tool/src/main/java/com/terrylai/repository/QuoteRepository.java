package com.terrylai.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.terrylai.entity.Quote;

public interface QuoteRepository extends MongoRepository<Quote, String> {

	void deleteBySymbol(final String symbol);
	
	List<Quote> findBySymbol(final String symbol);
	
	List<Quote> findBySymbolAndType(final String symbol, final String type, Sort sort);

	@Query("{ 'symbol' : ?0 }")
	List<Quote> findQuoteBySymbol(final String symbol, Sort sort);

	Page<Quote> findBySymbol(final String symbol, Pageable pageable);
	
}
