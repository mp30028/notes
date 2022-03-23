[Back to Contents Page](../README.md)

## Introductory notes on reactive programming in Java

### General Concepts
#### Overview
<img src="https://user-images.githubusercontent.com/78896340/159191166-05454347-045b-47c1-b406-186c1b185dd6.png" style="width:800px">

#### Operators
<img src="https://user-images.githubusercontent.com/78896340/159191513-fe7dd519-9a63-487d-93ce-c5a391afd546.png" style="width:1000px">



#### Four Key Interfaces
1. **Publisher** (read-only)
2. **Subscriber** (write-only)
3. **Subscription**
4. **Operartor** (act as both subscriber and publisher)

#### Subscriber Interface
Four Methods for reacting to situations (Events) as and when they occur
<img src="https://user-images.githubusercontent.com/78896340/159192011-9947c6af-7e84-4f92-aced-6cea4b2bfe2c.png" style="width:1000px">


#### Publisher Types:
There are 2 types of Publishers

##### Cold Publisher
- Lazy. Starts producing/emitting only when a subscriber subscribes
- Publisher creates a data producer and generates new sets of values for each new subscription
- When there are multiple observers, each observer might get different values

##### Hot Publisher
- Values are generated outside the publisher even when there are no observers.
- There will be only one data producer
- All the observers get the value from the single data producer irrespective of the time they started subscribing to the publisher. It means any new observer might not see the old value emitted by the publisher.

### Mono and Flux
Mono and Flux are implementations of the Publisher Interface.
- **Mono**: emits 0 or 1 element.
- **Flux**: emits 0 or N element(s). (Potentially unbounded)
- Possible to terminate with either **onComplete** or an **onError** events. 
- Completely asynchronous. Only emit values when there are downstream subscribers


### Subscribing to a producer

<img src="https://user-images.githubusercontent.com/78896340/159457538-ba984622-79c7-4b8e-8c68-485d6716a776.png" style="width:800px">


```
  // Example_01.java
  
  package com.zonesoft.examples.reactive_service.tryouts.flux;

  import org.junit.jupiter.api.Test;
  import org.slf4j.Logger;
  import org.slf4j.LoggerFactory;

  import reactor.core.Disposable;
  import reactor.core.publisher.Flux;

  class Example_01 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Example_01.class);

    @Test
    void runExample01() throws InterruptedException {
      System.out.println("-- Flux#empty example --");
      serveEmpty();
      Thread.sleep(10000);
    }

    private Disposable serveEmpty() {
      return Flux.empty().subscribe(i -> LOGGER.debug("Received : {}", i));
    }

  }
```

```
 // Example_02.java
 
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

      //Block execution to prevent program exiting and see the 
      //asynchronous results of the previous step
      Thread.sleep(10000);
    }

    private static void handler(Person p) {
      LOGGER.debug("Received: {}", p);
    }

    private Flux<Person> personsProducer(){
      return Flux.just(generatePerson(), generatePerson(), generatePerson(), generatePerson());
    }

  }

```

```
  // Example_03.java
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
		
		//Block execution to prevent program exiting and see the 
		//asynchronous results of the previous step
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

```

```
	// Example_04.java
	
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
		
		//Block execution to prevent program exiting in order to see the 
		//asynchronous results of the previous step
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

```

### Web Resources
1. [Introduction](http://www.vinsguru.com/reactive-programming-a-simple-introduction/)
2. [Creating Streams — Flux vs Mono](https://www.vinsguru.com/mono-vs-flux-project-reactor/)
3. [Reactor Hot Publisher vs Cold Publisher](https://www.vinsguru.com/reactor-hot-publisher-vs-cold-publisher/)
4. [Flux Create vs Flux Generate](https://vinsguru.medium.com/java-reactive-programming-flux-create-vs-flux-generate-38a23eb8c053)
5. [Combining Multiple Sources Of Flux / Mono](https://www.vinsguru.com/reactive-programming-reactor-combining-multiple-sources-of-flux-mono/)


[Back to Contents Page](../README.md)
