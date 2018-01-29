package com.terrylai.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

@Configuration
public class MongoConfig extends AbstractMongoConfiguration {
  
	static final String MAPPING_BASE_PACKAGE = "com.terrylai";
	
	@Value( "${default.databasename}" )
	private String databaseName;
	
	@Value( "${default.hostname}" )
	private String hostName; 
	
	@Value( "${default.portnumber}" )
	private Integer portNumber;
	
    @Override
    protected String getDatabaseName() {
        return databaseName;
    }
  
    @Override
    public Mongo mongo() throws Exception {
        return new MongoClient(hostName, portNumber);
    }
  
    @Override
    protected String getMappingBasePackage() {
        return MAPPING_BASE_PACKAGE;
    }
}