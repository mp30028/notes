package com.zonesoft.examples.reactive_service.tryouts.flux;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zonesoft.examples.reactive_service.entities.Person;
import static com.zonesoft.examples.reactive_service.services.generator.PersonGenerator.generatePerson;

import reactor.core.publisher.Flux;

class Example_03 {
	private static final Logger LOGGER = LoggerFactory.getLogger(Example_03.class);
	private static final int NO_OF_PERSONS = 5;
	
	
	@Test
	void runExample03() throws InterruptedException {
		personsProducer().subscribe(Example_03::handler);
		Thread.sleep(10000);
	}
	
	private static void handler(Person p) {
		LOGGER.debug("Received: {}", p);
	}

	private Flux<Person> personsProducer(){
		Person[] persons = new Person[NO_OF_PERSONS];
		int j;
		for(j=0; j < NO_OF_PERSONS; j++) {
			persons[j] = generatePerson();
		}
		return Flux.just(persons).log();
	}

}
