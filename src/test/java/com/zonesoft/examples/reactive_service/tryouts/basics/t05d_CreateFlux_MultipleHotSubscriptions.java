package com.zonesoft.examples.reactive_service.tryouts.basics;

import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Schedulers;

class t05d_CreateFlux_MultipleHotSubscriptions {
	private static final String description = 
			"Create an asynchronous flux with flux.create and stream the ticks to two subscribers. " +
			"The second subscriber subcribes a little later than the first but both of them DON'T " + 
			"get all the ticks. The second subscriber only gets the ticks emitted after it subscribed" + 
			", i.e. the behaviour is that of a hot publisher";

	private static final Logger LOGGER = LoggerFactory.getLogger(t05d_CreateFlux_MultipleHotSubscriptions.class);
	
	private static final int NUMBER_OF_TICKS = 30;
	private static final int NUMBER_OF_SUBSCRIBERS =5;
	
	private Consumer<FluxSink<TickerEvent>> tickEmitter = (FluxSink<TickerEvent> sink) -> {
		Ticker ticker = new Ticker("t05c_CreateFlux_MultipleColdSubscriptions", NUMBER_OF_TICKS, new TickerFluxListener(sink));
		ticker.run();
	};
	
	
		
	@Test
	void runExample() throws InterruptedException {
		LOGGER.info(description);
		Flux<TickerEvent> sourceFlux = Flux.create(tickEmitter).subscribeOn(Schedulers.parallel());
		CountDownLatch latch = new CountDownLatch(NUMBER_OF_SUBSCRIBERS);
		
		LOGGER.info("About to publish source-flux and create a connectable-flux");
			ConnectableFlux<TickerEvent> connectableFlux = sourceFlux.publish();
			connectableFlux.connect();
		LOGGER.info("Published source-flux and created(i.e. published) and connected to the connectable-flux ");

		for (int j=0; j<NUMBER_OF_SUBSCRIBERS; j++) {
			LOGGER.info("About to put current thread to sleep for 2 seconds before subscription");
			 Thread.sleep(2000);
			LOGGER.info("Current thread sleep completed");
			
			LOGGER.info("About to subscribe to source flux. Subscription#={}", j+1);
				connectableFlux.log().subscribe(null,null,() -> latch.countDown());
			LOGGER.info("Subscription#={} completed", j+1);
		}
		latch.await();
	}

}
