package com.terrylai.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.terrylai.service.RetrieverService;

@Component
public class Receiver {

	private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

	@Autowired
	RetrieverService service;

	@JmsListener(destination = "${queue.marketdata}")
	public void receive(String message) {
		LOGGER.info("received message='{}'", message);
		service.retrieve(message);
	}
}
