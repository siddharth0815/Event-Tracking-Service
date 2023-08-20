package io.eventtracking.service;

import io.eventtracking.exceptions.EventNotFoundException;
import io.eventtracking.models.entity.Event;
import io.eventtracking.models.entity.EventTrackingPlanMapping;
import io.eventtracking.repositories.EventRepository;
import io.eventtracking.repositories.EventTrackingPlanMappingRepository;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class EventTransactionHandler implements TransactionHandler<String, Map<String, Object>>{

  private final EventRepository eventRepository;
  private final EventTrackingPlanMappingRepository eventTrackingPlanMappingRepository;

  public EventTransactionHandler(EventRepository eventRepository,
      EventTrackingPlanMappingRepository eventTrackingPlanMappingRepository) {
    this.eventRepository = eventRepository;
    this.eventTrackingPlanMappingRepository = eventTrackingPlanMappingRepository;
  }

  @Override
  @Transactional
  public void handleTransaction(String eventName, Map<String, Object> properties) {
    Optional<List<EventTrackingPlanMapping>> eventTrackingPlanMappings =
        eventTrackingPlanMappingRepository.findByEventId(eventName);
    if(eventTrackingPlanMappings.isPresent())
       eventTrackingPlanMappings.get().stream().forEach(eventTrackingPlanMapping -> {
         eventTrackingPlanMapping.setContentJson(properties);
         eventTrackingPlanMappingRepository.save(eventTrackingPlanMapping);
       });
    findAndUpdateEventIfExists(eventName, properties);
  }

 @Transactional
  private void findAndUpdateEventIfExists(String eventId, Map<String, Object> properties){
    Optional<Event> eventOptional = eventRepository.findById(eventId);
    if(!eventOptional.isPresent())
      throw new EventNotFoundException("Event Not Found");
    eventOptional.get().setContentJson(properties);
    eventRepository.save(eventOptional.get());

  }

}
