package io.eventtracking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class EventTrackingApplication {
	public static void main(String[] args) {
		SpringApplication.run(EventTrackingApplication.class, args);
	}

}
