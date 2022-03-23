package com.zonesoft.examples.reactive_service.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zonesoft.examples.reactive_service.entities.Person;
import static com.zonesoft.examples.reactive_service.services.generator.PersonGenerator.*;

import reactor.core.publisher.Flux;

@Service
public class PersonService {
	private static final Logger LOGGER = LoggerFactory.getLogger(PersonService.class);
	private static final int MAX_WAIT_MILLISECONDS = 5000;
	private static final int MIN_WAIT_MILLISECONDS = 250;
	
	public Flux<Person> fetchPerson() {		
		return this.startStreaming().cache().log();
	}
	
	
	
	private Flux<Person> startStreaming(){
		return Flux.<Person> generate(sink -> {
										int waitMs = randomInt(MIN_WAIT_MILLISECONDS, MAX_WAIT_MILLISECONDS);
										LOGGER.debug("waiting for {}ms", waitMs);
										try {
											Thread.sleep(waitMs);
										} catch (InterruptedException e) {
											e.printStackTrace();
										}
										sink.next(generatePerson());
									  });
	}
	

}
