package io.eventtracking.service;

import io.eventtracking.exceptions.TrackingPlanNotFoundException;
import io.eventtracking.models.entity.EventTrackingPlanMapping;
import io.eventtracking.models.entity.TrackingPlan;
import io.eventtracking.models.request.TrackingPlanCreationRequest;
import io.eventtracking.repositories.EventTrackingPlanMappingRepository;
import io.eventtracking.repositories.TrackingPlanRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TrackingPlanServiceImpl implements TrackingPlanService {

  private final TrackingPlanTransactionHandler transactionHandler;
  private final EventTrackingPlanMappingRepository eventTrackingPlanMappingRepository;
  private final TrackingPlanRepository trackingPlanRepository;

  public TrackingPlanServiceImpl(
      TrackingPlanTransactionHandler transactionHandler,
      EventTrackingPlanMappingRepository eventTrackingPlanMappingRepository,
      TrackingPlanRepository trackingPlanRepository) {
    this.transactionHandler = transactionHandler;
    this.eventTrackingPlanMappingRepository = eventTrackingPlanMappingRepository;
    this.trackingPlanRepository = trackingPlanRepository;
  }

  @Override
  public TrackingPlanCreationRequest createTrackingPlan(TrackingPlanCreationRequest request) {
    transactionHandler.handleTransaction(request, null);
    return request;
  }

  @Override
  public TrackingPlanCreationRequest getTrackingPlan(String trackingPlan, Integer pageNumber,
      Integer pageSize) {
    Optional<TrackingPlan> trackingPlanOptional = trackingPlanRepository.findById(trackingPlan);
    if (!trackingPlanOptional.isPresent())
      throw new TrackingPlanNotFoundException("Tracking Plan Not Found");

      Pageable pageable = PageRequest.of(pageNumber, pageSize);
      Optional<List<EventTrackingPlanMapping>> eventTrackingPlanMappings =
          eventTrackingPlanMappingRepository.findByPlanId(trackingPlan, pageable);
      return TrackingPlanCreationRequest.from(trackingPlan, eventTrackingPlanMappings);
  }

    @Override
    public List<TrackingPlanCreationRequest> getAllTrackingPlans () {
      List<TrackingPlanCreationRequest> apiResponse = new ArrayList<>();
      List<EventTrackingPlanMapping> eventTrackingPlanMappings =
          eventTrackingPlanMappingRepository.findAll();
      eventTrackingPlanMappings.stream()
          .collect(Collectors.groupingBy(mapping -> mapping.getPlanId()))
          .forEach((key, value) -> {
            TrackingPlanCreationRequest request = TrackingPlanCreationRequest
                .from(key, Optional.of(value));
            apiResponse.add(request);
          });
      return apiResponse;
    }

  }
