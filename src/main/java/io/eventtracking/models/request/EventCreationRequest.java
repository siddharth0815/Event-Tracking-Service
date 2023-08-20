package io.eventtracking.models.request;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Builder
@Data
@AllArgsConstructor
public class EventCreationRequest {

  private String name;
  private String description;
  private Map<String, Object> properties;


}
