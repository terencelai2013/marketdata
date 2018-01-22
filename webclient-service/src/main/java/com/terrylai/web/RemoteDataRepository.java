package com.terrylai.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public class RemoteDataRepository implements DataRepository {

	protected Logger logger = Logger.getLogger(RemoteDataRepository.class.getName());
	
	@Autowired
	protected RestTemplate restTemplate;
	
	protected String serviceUrl;
	
	public RemoteDataRepository(String serviceUrl) {
		this.serviceUrl = serviceUrl.startsWith("http") ? serviceUrl
				: "http://" + serviceUrl;
	}
	
	@Override
	public List<Name> getAllName() {
		logger.info("RemoteRespository getAllName invoked");
		Name[] names = restTemplate.getForObject(serviceUrl+"/symbols", Name[].class);
		logger.info("RemoteRespository getAllName finished");
		return Arrays.asList(names);
	}

	@Override
	public List<Data> getName(String symbol) {
		logger.info("RemoteRespository getName invoked: [symbol," + symbol + "]");
		Data[] data = restTemplate.getForObject(serviceUrl+"/raw/" + symbol, Data[].class);
		logger.info("RemoteRespository getName finished: [size: " + data.length + "]");
		return Arrays.asList(data);
	}
}
