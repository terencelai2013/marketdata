package com.terrylai.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.terrylai.entities.Symbol;

public interface SymbolRepository extends MongoRepository<Symbol, String> {

	Symbol findByName(final String name);
}
