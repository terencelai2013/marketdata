package com.terrylai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.terrylai.web.DataRepository;
import com.terrylai.web.RemoteDataRepository;

@SpringBootApplication
@EnableDiscoveryClient
public class WebclientServiceApplication {
	public static final String DATA_SERVICE_URL = "http://DATA-SERVICE";

	public static void main(String[] args) {
		SpringApplication.run(WebclientServiceApplication.class, args);
	}
	
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	@Bean
	public DataRepository dataRepository(){
		return new RemoteDataRepository(DATA_SERVICE_URL);
	}
}
