package com.zonesoft.examples.reactive_service.tryouts.basics;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Flux;

class t01_Subscribe_Simple {
	private static final String description = 
		"Simple subcribe to trigger a publisher (ie. a Flux) to start publishing";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(t01_Subscribe_Simple.class);
	
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
		Supplier<String> timestampSupplier = () -> LocalDateTime.now().toString();
		Flux<String> timestampFlux = 
				Flux
				.range(1, MAX_TIMESTAMPS)
				.delayElements(Duration.ofSeconds(WAIT_SECONDS))
				.map(n -> n + " - " + timestampSupplier.get())
				.map(s -> {LOGGER.info("{}", s); return s;});
		timestampFlux.subscribe(); 
		tickingWait(MAX_TIMESTAMPS*WAIT_SECONDS, "");
	}
	
	
	private static void tickingWait(int seconds, String tag) {
		LOGGER.debug("{} Started {} seconds wait. {}", tag, seconds);
		for (int j = 0; j < seconds; j++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				LOGGER.error("Error whilst waiting: {}", e.getLocalizedMessage());
				e.printStackTrace();
			}
			LOGGER.debug(":");
		}
		LOGGER.debug("{} Finished {} seconds wait. {}", tag, seconds);
	}

}
