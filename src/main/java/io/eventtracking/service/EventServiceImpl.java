package io.eventtracking.service;

import io.eventtracking.cacheConfig.ServiceCacheStore;
import io.eventtracking.exceptions.EventNotFoundException;
import io.eventtracking.exceptions.TrackingPlanNotFoundException;
import io.eventtracking.models.entity.Event;
import io.eventtracking.models.entity.EventTrackingPlanMapping;
import io.eventtracking.models.entity.TrackingPlan;
import io.eventtracking.models.request.EventCreationRequest;
import io.eventtracking.models.request.EventUpdationRequest;
import io.eventtracking.repositories.EventRepository;
import io.eventtracking.repositories.EventTrackingPlanMappingRepository;
import io.eventtracking.repositories.TrackingPlanRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;


@Service
public class EventServiceImpl implements EventService {

  private final EventRepository eventRepository;
  private final TrackingPlanRepository trackingPlanRepository;
  private final EventTrackingPlanMappingRepository eventTrackingPlanMappingRepository;
  private final EventTransactionHandler transactionHandler;
//  private final ServiceCacheStore serviceCacheStore;

  public EventServiceImpl(EventRepository eventRepository,
      TrackingPlanRepository trackingPlanRepository,
      EventTrackingPlanMappingRepository eventTrackingPlanMappingRepository,
      EventTransactionHandler transactionHandler) {
    this.eventRepository = eventRepository;
    this.trackingPlanRepository = trackingPlanRepository;
    this.eventTrackingPlanMappingRepository = eventTrackingPlanMappingRepository;
    this.transactionHandler = transactionHandler;
//    this.serviceCacheStore = serviceCacheStore;
  }

  @Override
  public EventCreationRequest createEvent(EventCreationRequest request) {
    transactionHandler.handleTransaction(request.getName(), request.getProperties());
    return request;
  }

  @Override
  public Event getEventById(String eventId) {
    Optional<Event> eventOptional = eventRepository.findById(eventId);
    if (!eventOptional.isPresent()) {
      throw new EventNotFoundException("Event Not Found");
    }
    return eventOptional.get();
  }

  @Override
  public void updateEvent(String eventId, EventUpdationRequest request) {
    transactionHandler.handleTransaction(eventId, request.getProperties());
  }

  @Override
  public void linkPlanAndEvent(String eventId, String trackingPlanId) {
    Optional<TrackingPlan> trackingPlanOptional = trackingPlanRepository.findById(trackingPlanId);
    Optional<Event> eventOptional = eventRepository.findById(eventId);
    if (!trackingPlanOptional.isPresent()) {
      throw new TrackingPlanNotFoundException("Tracking Plan Not Found");
    } else if (!eventOptional.isPresent()) {
      throw new EventNotFoundException("Event Not Found");
    }
    eventTrackingPlanMappingRepository.save(createMappingEntity(trackingPlanOptional.get(),
        eventOptional.get()));
  }


  private EventTrackingPlanMapping createMappingEntity(TrackingPlan trackingPlan, Event event) {
    return EventTrackingPlanMapping.builder()
        .eventId(event.getEventName())
        .contentJson(event.getContentJson())
        .planId(trackingPlan.getPlanName())
        .eventDescription(event.getDescription())
        .build();
  }

}
