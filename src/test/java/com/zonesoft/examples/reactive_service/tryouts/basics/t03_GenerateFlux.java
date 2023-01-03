package com.zonesoft.examples.reactive_service.tryouts.basics;

import java.time.LocalDateTime;
import java.util.function.Consumer;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;

class t03_GenerateFlux {
	private static final String description = 
		"Create a synchronous flux with generate";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(t03_GenerateFlux.class);
		
	@Test
	void runExample() {
		LOGGER.info(description);
		int MAX_TIMESTAMPS = 10;
		int WAIT_SECONDS = 3;
		int waitTime = MAX_TIMESTAMPS*WAIT_SECONDS;
		
		Consumer<SynchronousSink<String>> generateTimestamp = (SynchronousSink<String> sink) ->{
			String timestamp = LocalDateTime.now().toString();
			try {
				Thread.sleep(WAIT_SECONDS*1000L);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			sink.next(timestamp);
		};
		
		 Consumer<String> consumeAndLog = (item) -> {
			 LOGGER.info("{}",item);
		 };
		 
		 Consumer<Throwable> errorHandler = (e) -> {
				LOGGER.error("Exception encountered: {}", e.getLocalizedMessage());
		 };
		 
		 Runnable completionHandler = () -> {
			 LOGGER.info("All Done. Bye");
		 };
		
		 
		 
		 Flux<String> timestampFlux = Flux.generate(generateTimestamp);
		 timestampFlux.subscribe(consumeAndLog, errorHandler, completionHandler); 
		 tickingWait(waitTime, ""); // This is to allow the flux to complete
	}
	
	
	private static void tickingWait(int seconds, String tag) {
		LOGGER.debug("{} Started {} seconds wait. {}", tag, seconds);
		final int TICK_INTERVAL = 3;
		for (int j = 0; j < seconds; j++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				LOGGER.error("Error whilst waiting: {}", e.getLocalizedMessage());
				e.printStackTrace();
			}
			if (j%TICK_INTERVAL == 0) {
				LOGGER.debug(":");
			}
		}
		LOGGER.debug("{} Finished {} seconds wait. {}", tag, seconds);
	}

}
