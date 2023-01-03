package com.zonesoft.examples.reactive_service.tryouts.basics;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Flux;

class t02_Subscribe_WithHandlers {
	private static final String description = 
		"Subcribe to trigger a publisher (ie. a Flux) to start publishing but with an lambda functions to consume and process output, handle errors and complete";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(t02_Subscribe_WithHandlers.class);
	
//	Supplier       ()    -> x
//	Consumer       x     -> ()
//	BiConsumer     x, y  -> ()
//	Callable       ()    -> x throws ex
//	Runnable       ()    -> ()
//	Function       x     -> y
//	BiFunction     x,y   -> z
//	Predicate      x     -> boolean
//	UnaryOperator  x1    -> x2
//	BinaryOperator x1,x2 -> x3
	
	@Test
	void runExample() {
		LOGGER.info(description);
		int MAX_TIMESTAMPS = 10;
		int WAIT_SECONDS = 3;
		int waitTime = MAX_TIMESTAMPS*WAIT_SECONDS;
		
		
		
		Supplier<String> timestampSupplier = () -> LocalDateTime.now().toString();
		
		Function<Integer, Flux<Integer>> check = (n) -> {
			if (n < 19)
				return Flux.just(n);
			else
				throw new RuntimeException( n + " is not a valid value");
		};
		
		Flux<String> timestampFlux = 
			Flux.range(1, MAX_TIMESTAMPS)
				.flatMap(check)
				.delayElements(Duration.ofSeconds(WAIT_SECONDS))
				.map(n -> n + " - " + timestampSupplier.get());
		
		 Consumer<String> consumeAndLog = (item) -> {
			 LOGGER.info("{}",item);
		 };
		 
		 Consumer<Throwable> errorHandler = (e) -> {
				LOGGER.error("Exception encountered: {}", e.getLocalizedMessage());
		 };
		 
		 Runnable completionHandler = () -> {
			 LOGGER.info("All Done. Bye");
		 };
		 
		 
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
