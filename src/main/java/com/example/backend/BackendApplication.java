package com.example.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static org.springframework.boot.SpringApplication.*;

@SpringBootApplication
public class BackendApplication {

	private static final Logger LOG = LoggerFactory.getLogger("Backend");

	public static void main(String[] args) {
		run(BackendApplication.class, args);
	}

}
