package io.eventtracking.service;

import io.eventtracking.models.entity.EventTrackingPlanMapping;
import io.eventtracking.models.request.TrackingPlanCreationRequest;
import io.eventtracking.repositories.EventTrackingPlanMappingRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TrackingPlanServiceImpl implements TrackingPlanService {

  private final TrackingPlanTransactionHandler transactionHandler;
  private final EventTrackingPlanMappingRepository eventTrackingPlanMappingRepository;

  public TrackingPlanServiceImpl(
      TrackingPlanTransactionHandler transactionHandler,
      EventTrackingPlanMappingRepository eventTrackingPlanMappingRepository) {
    this.transactionHandler = transactionHandler;
    this.eventTrackingPlanMappingRepository = eventTrackingPlanMappingRepository;
  }

  @Override
  public TrackingPlanCreationRequest createTrackingPlan(TrackingPlanCreationRequest request) {
   transactionHandler.handleTransaction(request, null);
   return request;
  }

  @Override
  public TrackingPlanCreationRequest getTrackingPlan(String trackingPlan, Integer pageNumber,
      Integer pageSize) {
    Pageable pageable = PageRequest.of(pageNumber, pageSize);
    Optional<List<EventTrackingPlanMapping>> eventTrackingPlanMappings =
        eventTrackingPlanMappingRepository.findByPlanId(trackingPlan, pageable);
    return TrackingPlanCreationRequest.from(trackingPlan, eventTrackingPlanMappings);
  }




}
