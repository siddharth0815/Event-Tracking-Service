package io.eventtracking.service;

import io.eventtracking.models.entity.Event;
import io.eventtracking.models.request.EventCreationRequest;
import io.eventtracking.models.request.EventUpdationRequest;

public interface EventService {

  EventCreationRequest createEvent(EventCreationRequest request);

  Event getEventById(String eventId);

  void updateEvent(String eventId, EventUpdationRequest request);

  void linkPlanAndEvent(String eventId, String trackingPlanId);

}
