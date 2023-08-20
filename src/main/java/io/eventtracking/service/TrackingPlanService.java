package io.eventtracking.service;

import io.eventtracking.models.request.TrackingPlanCreationRequest;
import java.util.List;

public interface TrackingPlanService {

  TrackingPlanCreationRequest createTrackingPlan(TrackingPlanCreationRequest request);
  TrackingPlanCreationRequest getTrackingPlan(String trackingPlan, Integer pageNumber, Integer pageSize);

  List<TrackingPlanCreationRequest> getAllTrackingPlans();


}
