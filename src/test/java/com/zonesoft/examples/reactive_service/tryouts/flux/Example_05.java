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

class Example_05 {
	private static final Logger LOGGER = LoggerFactory.getLogger(Example_05.class);
	private static final int REQUEST_SIZE = 3;
	private List<Person> persons = new ArrayList<>();
	

	@Test
	void runExample05() throws InterruptedException {
		Flux<Person> flux = Flux.generate(
				(synchronousSink) -> {
					Person person = generatePerson();
					// Stop generating if firstname starts with S or alphabetically after S 
					if(person.getFirstname().charAt(0) >= 'S') { 
						LOGGER.debug("Finishing off with person ={}", person);
						synchronousSink.complete();
					}else {
						LOGGER.debug("Continuing with person ={}", person);
						synchronousSink.next(person);
					}
				}
			);
		flux.subscribe(new PersonSubscriber());
		Thread.sleep(10000);
	}
	
	
	private class PersonSubscriber implements Subscriber<Person>{
	
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
			LOGGER.debug("{}: Received: {}",requestCount, person);
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


