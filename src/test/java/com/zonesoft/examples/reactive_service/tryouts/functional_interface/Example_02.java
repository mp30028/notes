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
