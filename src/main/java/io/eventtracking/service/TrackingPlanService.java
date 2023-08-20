package io.eventtracking.service;

import io.eventtracking.models.request.TrackingPlanCreationRequest;

public interface TrackingPlanService {

  TrackingPlanCreationRequest createTrackingPlan(TrackingPlanCreationRequest request);
  TrackingPlanCreationRequest getTrackingPlan(String trackingPlan, Integer pageNumber, Integer pageSize);


}
