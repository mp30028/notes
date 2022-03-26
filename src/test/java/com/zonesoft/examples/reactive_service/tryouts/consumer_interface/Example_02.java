package com.zonesoft.examples.reactive_service.tryouts.consumer_interface;

import java.util.function.Consumer;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zonesoft.examples.reactive_service.entities.Person;
import static com.zonesoft.examples.reactive_service.services.generator.PersonGenerator.generatePerson;


class Example_02 {
	private static final Logger LOGGER = LoggerFactory.getLogger(Example_02.class);
	
	@Test
	void runExample02(){
		Person person = generatePerson();
		LOGGER.debug("Person to be copy refreshed=[ {} ]", person);
		refresh(
				person,
				(Person p)->{
					Person copyFrom = generatePerson();
					p.setId(copyFrom.getId());
					p.setFirstname(copyFrom.getFirstname());
					p.setLastname(copyFrom.getLastname());
					p.setDateOfBirth(copyFrom.getDateOfBirth());
					p.setGender(copyFrom.getGender());
				}
		);
		LOGGER.debug("Person after copy refresh=[ {} ]", person);
		
		LOGGER.debug("Person to be update refreshed=[ {} ]", person);
		refresh(person, 
				(Person p)->{ 	
					person.setFirstname(person.getFirstname() + "_UPDATED");
					person.setLastname(person.getLastname() + "_UPDATED");
				}
		);
		LOGGER.debug("Person after update refresh=[ {} ]", person);	
		
	}
	
	private void refresh(Person person, Consumer<Person> refresher) {
		refresher.accept(person);	
	}
}
