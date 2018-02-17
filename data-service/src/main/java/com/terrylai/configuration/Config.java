package com.terrylai.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

@Configuration
public class Config extends AbstractMongoConfiguration {
  
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
    
    @SuppressWarnings("deprecation")
	@Bean
    public MongoDbFactory mongoDbFactory() throws Exception {
        Mongo mongoClient = mongo();
        UserCredentials userCredentials = new UserCredentials("", "");
        return new SimpleMongoDbFactory(mongoClient, databaseName, userCredentials);
    }
 
    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
        return mongoTemplate;
    }
    @Override
    protected String getMappingBasePackage() {
        return MAPPING_BASE_PACKAGE;
    }
}