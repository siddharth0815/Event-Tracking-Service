package io.eventtracking.service;

import io.eventtracking.models.entity.EventTrackingPlanMapping;
import io.eventtracking.models.entity.TrackingPlan;
import io.eventtracking.models.request.TrackingPlanCreationRequest;
import io.eventtracking.repositories.EventRepository;
import io.eventtracking.repositories.EventTrackingPlanMappingRepository;
import io.eventtracking.repositories.TrackingPlanRepository;
import javax.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class TrackingPlanTransactionHandler implements
    TransactionHandler<TrackingPlanCreationRequest, String>{

  private final EventRepository eventRepository;
  private final TrackingPlanRepository trackingPlanRepository;
  private final EventTrackingPlanMappingRepository eventTrackingPlanMappingRepository;

  public TrackingPlanTransactionHandler(
      EventRepository eventRepository,
      TrackingPlanRepository trackingPlanRepository,
      EventTrackingPlanMappingRepository eventTrackingPlanMappingRepository) {
    this.eventRepository = eventRepository;
    this.trackingPlanRepository = trackingPlanRepository;
    this.eventTrackingPlanMappingRepository = eventTrackingPlanMappingRepository;
  }

  @Override
  @Transactional
  public void handleTransaction(TrackingPlanCreationRequest request, String param) {
     trackingPlanRepository.save(TrackingPlan.from(request));
     request.getEvents().forEach(event -> {
       eventRepository.save(event);
       eventTrackingPlanMappingRepository.save(EventTrackingPlanMapping.from(request.getDisplayName(),
           event));
     });
  }
}
