package io.eventtracking.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaConsumer {

  @KafkaListener(topics = "postgres.public.event")
  public void flightEventConsumer(String message) {
    log.info("Consumer consume Kafka message -> {}", message);

  }

}