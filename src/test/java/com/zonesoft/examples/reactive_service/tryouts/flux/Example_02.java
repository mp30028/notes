package com.zonesoft.examples.reactive_service.tryouts.flux;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zonesoft.examples.reactive_service.entities.Person;
import static com.zonesoft.examples.reactive_service.services.generator.PersonGenerator.generatePerson;

import reactor.core.publisher.Flux;

class Example_02 {
	private static final Logger LOGGER = LoggerFactory.getLogger(Example_02.class);
	
	@Test
	void runExample02() throws InterruptedException {		
		personsProducer().subscribe(Example_02::handler);
		Thread.sleep(10000);
	}
	
	private static void handler(Person p) {
		LOGGER.debug("Received: {}", p);
	}

	private Flux<Person> personsProducer(){
		return Flux.just(
							generatePerson(), 
							generatePerson(), 
							generatePerson(), 
							generatePerson()
						);
	}

}

