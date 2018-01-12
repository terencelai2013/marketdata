package com.terrylai.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.terrylai.entities.Quote;

public interface QuoteRepository extends MongoRepository<Quote, String> {

	List<Quote> findBySymbol(final String symbol);
}