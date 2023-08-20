package io.eventtracking.controllers;

import io.eventtracking.models.request.TrackingPlanCreationRequest;
import io.eventtracking.service.TrackingPlanService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/trackingPlan")
public class TrackingPlanController {

  private final TrackingPlanService trackingPlanService;

  public TrackingPlanController(TrackingPlanService trackingPlanService) {
    this.trackingPlanService = trackingPlanService;
  }

  @PostMapping
  public TrackingPlanCreationRequest createTrackingPlan(
      @RequestBody TrackingPlanCreationRequest request) {
    return trackingPlanService.createTrackingPlan(request);
  }

  @GetMapping("/{trackingPlanId}")
  public TrackingPlanCreationRequest getTrackingPlan(
      @PathVariable(value = "trackingPlanId") String trackingPlanId,
      @RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
      @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
    return trackingPlanService.getTrackingPlan(trackingPlanId, pageNumber, pageSize);
  }


}
