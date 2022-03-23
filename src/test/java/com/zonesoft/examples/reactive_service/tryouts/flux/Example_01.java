package com.zonesoft.examples.reactive_service.tryouts.flux;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.Disposable;
import reactor.core.publisher.Flux;

class Example_01 {
	private static final Logger LOGGER = LoggerFactory.getLogger(Example_01.class);
	
	@Test
	void runExample01() throws InterruptedException {
		System.out.println("-- Flux#empty example --");
		serveEmpty();
		Thread.sleep(10000);
	}
	
	private Disposable serveEmpty() {
		return Flux.empty().subscribe(i -> LOGGER.debug("Received : {}", i));
	}

}
