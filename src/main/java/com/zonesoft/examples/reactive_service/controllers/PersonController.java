package com.zonesoft.examples.reactive_service.controllers;

import reactor.core.publisher.Flux;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zonesoft.examples.reactive_service.entities.Person;
import com.zonesoft.examples.reactive_service.services.PersonService;

@RestController
public class PersonController {

	private final PersonService service;

	public PersonController(PersonService service) {
		this.service = service;
	}

	@GetMapping(value = "/fetch-persons", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	Flux<Person> fetchPersons() {
		return this.service.fetchPerson();
	}

}

