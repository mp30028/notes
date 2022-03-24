package com.zonesoft.examples.reactive_service.tryouts.flux;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.zonesoft.examples.reactive_service.entities.Person;
import static com.zonesoft.examples.reactive_service.services.generator.PersonGenerator.generatePerson;
import java.util.ArrayList;
import java.util.List;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

class Example_07 {
	private static final Logger LOGGER = LoggerFactory.getLogger(Example_07.class);
	private static final int NO_OF_PERSONS = 11;
	private static final int REQUEST_SIZE = 3;


	
	@Test
	void runExample07() throws InterruptedException {
		Flux<Person> flux = Flux.create(
					(FluxSink<Person> sink) -> {
						for (int j=1; j <= NO_OF_PERSONS; j++) {
							Person person = generatePerson();
//							LOGGER.debug("j: {}: Generated-Person: {}", j,person);
							sink.next(person);
						}
						sink.complete();
					}
				);
		flux.log().subscribe(new PersonSubscriber());
		Thread.sleep(10000);
	}
	
	
	
	
	private class PersonSubscriber implements Subscriber<Person>{
		private List<Person> persons = new ArrayList<>();
		private Subscription subscription;
		private int requestCount = 0;
		
		@Override
		public void onSubscribe(Subscription s) {
			this.subscription = s;
			this.subscription.request(REQUEST_SIZE);
		}
	
		@Override
		public void onNext(Person person) {
			this.requestCount++;
//			LOGGER.debug("requestCount: {}: Received-Person: {}",requestCount, person);
			persons.add(person);
			if (requestCount % REQUEST_SIZE == 0) {
				this.subscription.request(REQUEST_SIZE);
			}
		}
	
		@Override
		public void onError(Throwable t) {
			LOGGER.error("A {} error occurred whilst processing stream", t.getLocalizedMessage());
		}
	
		@Override
		public void onComplete() {
			LOGGER.info("Stream processing completed");
		}	
	}
}


