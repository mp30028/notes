package com.zonesoft.examples.reactive_service.tryouts.basics;

import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

class t05c_CreateFlux_MultipleColdSubscriptions {
	private static final String description = "Create an asynchronous flux with flux.create and stream the ticks to two subscribers. "
			+ "in two seperat threads. The second subscriber subcribes a little later than the first"
			+ "but both of them get all the ticks, i.e. the behaviour is that of a cold publisher";

	private static final Logger LOGGER = LoggerFactory.getLogger(t05c_CreateFlux_MultipleColdSubscriptions.class);
	
	private Consumer<FluxSink<TickerEvent>> tickEmitter = (FluxSink<TickerEvent> sink) -> {
		
		Ticker ticker = new Ticker("t05c_CreateFlux_MultipleColdSubscriptions", 15, new TickerFluxListener(sink));
		ticker.run();
	};
	
	
		
	@Test
	void runExample() throws InterruptedException {
		LOGGER.info(description);
		Flux<TickerEvent> flux = Flux.create(tickEmitter);
		CountDownLatch latch = new CountDownLatch(2);
		
		Thread threadSubscription1 = new Thread(() -> flux.log("threadSubscription1").subscribe(null,null,() -> latch.countDown()));
		Thread threadSubscription2 = new Thread(() -> flux.log("threadSubscription2").subscribe(null,null,() -> latch.countDown()));
		
		
		LOGGER.info("About to start threadSubscription1");
			threadSubscription1.start();
		LOGGER.info("Started threadSubscription1");
		
		 LOGGER.info("about to go sleep");
		 Thread.sleep(5000);
		 LOGGER.info("sleep finished");
		 
		LOGGER.info("About to start threadSubscription2");
		threadSubscription2.start();
		LOGGER.info("Started threadSubscription2");
		
		LOGGER.info("Waiting for latch to count down");
		latch.await();
		LOGGER.info("latch to count down wait completed");
	}

}
