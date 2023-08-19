package io.eventtracking.exceptions;

public class TrackingPlanNotFoundException extends RuntimeException{
  public TrackingPlanNotFoundException(String message){
    super(message);
  }

}
