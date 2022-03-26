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
import reactor.core.publisher.SynchronousSink;

class Example_05a {
	private static final Logger LOGGER = LoggerFactory.getLogger(Example_05a.class);
	private static final int REQUEST_SIZE = 3;
	private List<Person> persons = new ArrayList<>();
	

	@Test
	void runExample05a() throws InterruptedException {
		PersonsProducer producer = new PersonsProducer();		
		Flux<Person> flux = Flux.generate((SynchronousSink<Person> sink)->producer.produce(sink));
		this.wait(5, "Before-subscribe");
		PersonSubscriber subscriber =new PersonSubscriber();
		flux.subscribe(subscriber);
		this.wait(1, "Before-run-ends");
	}
	
	private interface IProducer<T>{
		public void produce(SynchronousSink<T> sink);
	}
	
	private class PersonsProducer implements IProducer<Person>{
		
		@Override
		public void produce(SynchronousSink<Person> synchronousSink){
			Person person = generatePerson();
			char initial = person.getFirstname().charAt(0);
			// Stop generating if firstname starts with S or alphabetically after S 
			if(initial >= 'S') { 
				LOGGER.debug("Finishing off. Person's firstname starts with {}. person=[ {} ]",initial, person);
				synchronousSink.complete();
			}else {
				LOGGER.debug("Continuing with person ={}", person);
				synchronousSink.next(person);
			}
		}
		
	}
	
	private void wait(int seconds, String tag) {
		LOGGER.debug("Started wait. {}", tag);
			for (int j=0; j < seconds; j++) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					LOGGER.error("Error whilst waiting: {}", e.getLocalizedMessage());
					e.printStackTrace();
				}
				LOGGER.debug(":");
			}
		LOGGER.debug("Finished wait. {}", tag);
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


