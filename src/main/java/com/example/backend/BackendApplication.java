package com.example.backend;

import Controllers.FilmGalleryController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import static org.springframework.boot.SpringApplication.*;

@SpringBootApplication
@ComponentScan(basePackages = {"Controllers","Services"})
public class BackendApplication {

	private static final Logger LOG = LoggerFactory.getLogger("Backend");

	public static void main(String[] args) {
		run(BackendApplication.class, args);
	}

}
