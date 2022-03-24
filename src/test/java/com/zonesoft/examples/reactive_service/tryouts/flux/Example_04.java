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

class Example_04 {
	private static final Logger LOGGER = LoggerFactory.getLogger(Example_04.class);
	private static final int NO_OF_PERSONS = 11;
	private static final int REQUEST_SIZE = 3;
	private List<Person> persons = new ArrayList<>();
	
	@Test
	void runExample04() throws InterruptedException {
		personsProducer().subscribe(new PersonSubscriber());
		Thread.sleep(10000);
	}

	private Flux<Person> personsProducer(){
		Person[] personsArray =  new Person[NO_OF_PERSONS];
		int j;
		for(j=0; j < NO_OF_PERSONS; j++) {
			personsArray[j]= generatePerson();
		}
		return Flux.just(personsArray).log();
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


