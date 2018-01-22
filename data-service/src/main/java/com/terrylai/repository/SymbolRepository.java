package com.terrylai.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.terrylai.entity.Symbol;

public interface SymbolRepository extends MongoRepository<Symbol, String> {

	Symbol findByName(final String name);
}
