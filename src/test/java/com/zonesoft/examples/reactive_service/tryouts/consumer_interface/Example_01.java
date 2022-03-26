package com.zonesoft.examples.reactive_service.tryouts.consumer_interface;

import java.util.function.Consumer;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zonesoft.examples.reactive_service.entities.Person;
import static com.zonesoft.examples.reactive_service.services.generator.PersonGenerator.generatePerson;


class Example_01 {
	private static final Logger LOGGER = LoggerFactory.getLogger(Example_01.class);
	
	@Test
	void runExample01(){
		Person person = generatePerson();
		LOGGER.debug("Person to be copy refreshed=[ {} ]", person);
		refresh(person, new CopyBasedRefresher());
		LOGGER.debug("Person after copy refresh=[ {} ]", person);
		
		LOGGER.debug("Person to be update refreshed=[ {} ]", person);
		refresh(person, new UpdateBasedRefresher());
		LOGGER.debug("Person after update refresh=[ {} ]", person);	
	}
	
	private void refresh(Person person, Consumer<Person> refresher) {
		refresher.accept(person);	
	}
	

	private class CopyBasedRefresher implements Consumer<Person> {
		
		@Override //functional method
		public void accept(Person person) {
			//side effect - person is changed
			Person newPerson = generatePerson();
			deepCopy(newPerson, person);
		}
		
		private void deepCopy(Person copyFrom, Person copyTo) {
			copyTo.setId(copyFrom.getId());
			copyTo.setFirstname(copyFrom.getFirstname());
			copyTo.setLastname(copyFrom.getLastname());
			copyTo.setDateOfBirth(copyFrom.getDateOfBirth());
			copyTo.setGender(copyFrom.getGender());
		}
		
	}
	
	private class UpdateBasedRefresher implements Consumer<Person> {
		
		@Override //functional method
		public void accept(Person person) {
			//side effect - person is changed
			update(person);
		}
		
		private void update(Person person) {
			person.setFirstname(person.getFirstname() + "_UPDATED");
			person.setLastname(person.getLastname() + "_UPDATED");
		}
		
	}
}
