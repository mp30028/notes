package com.zonesoft.examples.reactive_service.tryouts.basics;

import java.time.LocalDateTime;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;

class t04_GenerateFluxWithState {
	private static final String description = "Create a synchronous flux with generate using state";

	private static final Logger LOGGER = LoggerFactory.getLogger(t04_GenerateFluxWithState.class);

	@Test
	void runExample() {
		LOGGER.info(description);
		int MAX_TIMESTAMPS = 10;
		int WAIT_SECONDS = 3;

		Consumer<String> consumeAndLog = (item) -> {
			LOGGER.info("{}", item);
		};

		Consumer<Throwable> errorHandler = (e) -> {
			LOGGER.error("Exception encountered: {}", e.getLocalizedMessage());
		};

		Runnable completionHandler = () -> {
			LOGGER.info("All Done. Bye");
		};

		Callable<Integer> initialiseState = () -> MAX_TIMESTAMPS;

		Consumer<SynchronousSink<String>> generateTimestamp = (SynchronousSink<String> sink) -> {
			String timestamp = LocalDateTime.now().toString();
			try {
				Thread.sleep(WAIT_SECONDS * 1000L);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			sink.next(timestamp);
		};

		BiFunction<Integer, SynchronousSink<String>, Integer> generator = (state, sink) -> {
			if (state > 0) {
				generateTimestamp.accept(sink);
				state = state - 1;
			} else {
				sink.complete();
			}
			return state;
		};
		
		Consumer<Integer> logAndCleanUp = (state) ->{
			LOGGER.info("Resetting state to 10, but is currently={}",state);
			state =10;
		};
		
		Flux<String> timestampFlux = Flux.generate(initialiseState, generator,logAndCleanUp);
		timestampFlux.subscribe(consumeAndLog, errorHandler, completionHandler);
	}

}
