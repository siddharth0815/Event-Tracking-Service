package io.eventtracking.controllers;

import io.eventtracking.models.entity.Event;
import io.eventtracking.models.request.EventCreationRequest;
import io.eventtracking.models.request.EventUpdationRequest;
import io.eventtracking.service.EventService;
import java.util.concurrent.CompletionStage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/events")
public class EventController {

  @Autowired
  private final EventService eventService;


  public EventController(EventService eventService) {
    this.eventService = eventService;
  }

  @PostMapping
  public EventCreationRequest createEvent(
      @RequestBody EventCreationRequest eventCreationRequest) {
    return eventService.createEvent(eventCreationRequest);
  }

  @GetMapping("/{eventId}")
  public Event getEvent(@PathVariable("eventId") String eventId) {
    return eventService.getEventById(eventId);
  }

  @PutMapping("/{eventId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updateEvent(
      @PathVariable("eventId") String eventId,
      @RequestBody EventUpdationRequest request) {
    eventService.updateEvent(eventId, request);
  }

  @PostMapping("/{eventId}/trackingPlans/{trackingPlanId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void linkTrackingPlanAndEvent(
      @PathVariable("eventId") String eventId,
      @PathVariable("trackingPlanId") String trackingPlanId) {
    eventService.linkPlanAndEvent(eventId, trackingPlanId);
  }


}
