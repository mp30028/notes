[Back to Contents Page](./contents.md)

## Introductory notes on reactive programming in Java

### General Concepts
#### Overview
<img src="https://user-images.githubusercontent.com/78896340/159191166-05454347-045b-47c1-b406-186c1b185dd6.png" style="width:800px">

- A **Publisher**, only when a **Subscriber** subscribes, will stream data to the **Subscriber**. 
- When a **Subscriber** subscribes a **Subscription** is created
- In Java Spring WebFlux Framework (which is built on top of Project Reactor) **Publisher, Subscriber, Subscription and Operator** are all Interfaces.
- **Flux** and **Mono** are two implementations of the Publisher Interface

#### Operators
<img src="https://user-images.githubusercontent.com/78896340/159191513-fe7dd519-9a63-487d-93ce-c5a391afd546.png" style="width:1000px">

- An **Operator** transforms a data stream and creates new data stream of the transformed data
- An **Operator** has both the **Publisher** and  **Subscriber** Interfaces.


#### Four Key Interfaces
1. **Publisher** (read-only)
2. **Subscriber** (write-only)
3. **Subscription**
4. **Operator** (act as both subscriber and publisher)

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


<img src="https://user-images.githubusercontent.com/78896340/159746546-5ee370bc-694c-43df-948e-7bda41f9e30f.png" style="width:800px">



```java
  // Example_01.java
  // Will not output anything from the stream as only empty objects 
  // are being streamed
  
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
      LOGGER.debug("-- Flux#empty example --");
      serveEmpty();
      Thread.sleep(10000);
    }

    private Disposable serveEmpty() {
      return Flux.empty().subscribe(i -> LOGGER.debug("Received : {}", i));
    }

  }
```




<img src="https://user-images.githubusercontent.com/78896340/159751947-03893b0d-e7e4-4475-ad9f-9c3049701230.png" style="width:800px">


---


```java
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
.
<img src="https://user-images.githubusercontent.com/78896340/159761501-7c73547e-e20a-4590-b539-ce63a4111826.png" style="width: 800px">

. 
<img src="https://user-images.githubusercontent.com/78896340/159763937-102e5866-afac-4f9d-9774-53d501b0cdc8.png" style="width: 800px">
 



---


```java
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

. 
<img src="https://user-images.githubusercontent.com/78896340/159847778-6f281199-3f70-47d7-9b92-1251f7bb5827.png" style="width: 800px">


---


```java
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



<img src="https://user-images.githubusercontent.com/78896340/159865029-ccf32a76-9917-4a0f-a299-9e391aa99e5d.png" style="width: 800px">
.

<img src="https://user-images.githubusercontent.com/78896340/159865093-f25b8817-df81-43b5-9196-c22d45aac857.png" style="width: 1200px">
.

<img src="https://user-images.githubusercontent.com/78896340/159864441-12c1e21e-1ef3-4797-b1ca-9448af9103f5.png" style="width: 800px">

### Creating stream sequences programmatically.

---
In the preceding examples the sequences generated are all from a fixed list. In reality sequences would be very dynamic and generated programmatically. There are several ways to do this but of these the **Flux.create** and **Flux.generate** are the most important
.

<img src="https://user-images.githubusercontent.com/78896340/160075173-26d77732-e408-4592-b222-c55e1dfe0aaf.png" style="width: 800px">

#### Flux.generate


```java

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
		Flux<Person> flux = Flux.generate((SynchronousSink<Person> sink)->new PersonsProducer().produce(sink));
		flux.subscribe(new PersonSubscriber());
		Thread.sleep(10000);
	}
	
	private interface IProducer<T>{
		public void produce(SynchronousSink<T> sink);
	}
	
	private class PersonsProducer implements IProducer<Person>{
		
		@Override
		public void produce(SynchronousSink<Person> synchronousSink){
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
	}
	
	private class PersonSubscriber implements Subscriber<Person>{
	
		/* ----   Details not shown here  ---*/
	}
}



```


### Web Resources
1. [Introduction](http://www.vinsguru.com/reactive-programming-a-simple-introduction/)
2. [Creating Streams - Flux vs Mono](https://www.vinsguru.com/mono-vs-flux-project-reactor/)
3. [Reactor Hot Publisher vs Cold Publisher](https://www.vinsguru.com/reactor-hot-publisher-vs-cold-publisher/)
4. [Flux Create vs Flux Generate](https://vinsguru.medium.com/java-reactive-programming-flux-create-vs-flux-generate-38a23eb8c053)
5. [Combining Multiple Sources Of Flux / Mono](https://www.vinsguru.com/reactive-programming-reactor-combining-multiple-sources-of-flux-mono/)
6. [reactor javadocs](https://projectreactor.io/docs/core/release/api/overview-summary.html)
7. [Reactor 3 Reference Guide](https://projectreactor.io/docs/core/release/reference/)

[Back to Contents Page](./contents.md)
