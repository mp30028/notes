[Back to Contents Page](../README.md)

## Functional Interfaces
Any interface that has a single unimplemented method can be called a functional interface. It can contain default and static methods which have an implementation. However there must only be one unimplemented method.

### Uses of a functional interface.
Because there is only one unimplemented method whenever an implementation of the interface is required it can be supplied by a Lambda expression. This is possible because the lambda expression can be mapped to the unimplemented method as there will only be a single candidate available.

In Example_01 the `class Handler` implements the interface `IHandler`. But `IHandler` has exactly one unimplemented method. Therefore `IHandler` is a functional interface.

```java
package com.zonesoft.examples.reactive_service.tryouts.functional_interface;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


class Example_01 {
	private static final Logger LOGGER = LoggerFactory.getLogger(Example_01.class);
	
	@Test
	void runExample01() throws InterruptedException {
		invokeHandler(new Handler());
	}
	
	private void invokeHandler(IHandler handler) {
		String returnValue = handler.handle(1, "First");
		LOGGER.debug("FROM Example_01.invokeHandler: {}",returnValue);		
	}
	
	
	private interface IHandler {
		String handle(int i, String s);
	}

	private class Handler implements IHandler {

		@Override
		public String handle(int i, String s) {
			StringBuilder sb = new StringBuilder();
			sb.append("[NUMBER: "); sb.append(i); sb.append(" | VALUE: "); sb.append(s); sb.append("]");
			return sb.toString();
		}

	}
}

```


In Example_02 the class `Handler` does not exist. Instead a Lambda function is passed to the `invokeHandler()` function, which requires an implementation of `IHandler` as one of it's parameter. This is possible because `IHandler` is a functional interface and the Lambda expression can be mapped to the unimplemented `handle` method in the interface.

```java
package com.zonesoft.examples.reactive_service.tryouts.functional_interface;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


class Example_02 {
	private static final Logger LOGGER = LoggerFactory.getLogger(Example_02.class);
	
	@Test
	void runExample02() throws InterruptedException {
		invokeHandler((i,s)->{
								StringBuilder sb = new StringBuilder();
								sb.append("[NUMBER: "); sb.append(i); sb.append(" | VALUE: "); sb.append(s); sb.append("]");
								return sb.toString();
							}
		);
	}
	
	private void invokeHandler(IHandler handler) {
		String returnValue = handler.handle(1, "First");
		LOGGER.debug("FROM Example_02.invokeHandler: {}",returnValue);		
	}
	
	
	private interface IHandler {
		String handle(int i, String s);
	}


}


```

### Web Resources
1. [Introduction & Tutorial](http://tutorials.jenkov.com/java-functional-programming/functional-interfaces.html)

[Back to Contents Page](../README.md)