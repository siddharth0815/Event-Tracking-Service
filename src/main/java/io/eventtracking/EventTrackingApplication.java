package io.eventtracking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan(basePackages = { "io.eventtracking.controllers.*",
//		"io.eventtracking.service.*", "io.eventtracking.cacheConfig.*",
//		"io.eventtracking.repositories.*", "com.google.common.cache.*"})
public class EventTrackingApplication {
	public static void main(String[] args) {
		SpringApplication.run(EventTrackingApplication.class, args);
	}

}
