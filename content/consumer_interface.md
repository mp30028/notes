[Back to Contents Page](./content.md)

## Consumer Interfaces
Consumer Interfaces are typically used to perform an operation on an object passed in as input but without returning anything. 

In Example_01 the `refresh()` function takes an existing Person object and updates it by changing it's attributes. How and which attributes are changed by the `refresh()` function depends on the implementation of the consumer interface passed for the parameter `Consumer<Person> refresher`. The example has two implementations to illustrate the concept.


### Example_01

```java

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

```

### Example_02
Example_02 does the same thing as Example_01 but is more succinct as the implementations for `Consumer<Person> refresher` are as in-line Lambda expressions. The use of Consumer Interfaces allows us to provide implementations without having declare and define them separately as was done in Example_01.


```java

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


```


### Web Resources
1. [Introductory Tutorial](https://www.geeksforgeeks.org/java-8-consumer-interface-in-java-with-examples/)
2. [More extensive Tutorial](https://www.javabrahman.com/java-8/java-8-java-util-function-consumer-tutorial-with-examples/) that covers `andThen()`

[Back to Contents Page](./content.md)
