package io.eventtracking.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import io.eventtracking.models.request.TrackingPlanCreationRequest;
import java.util.Map;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

@Entity
@Table(name = "event_trackingPlan_mapping")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class EventTrackingPlanMapping {

  @Id
  @GeneratedValue
  private UUID id;
  private String planId;
  private String eventId;
  private String eventDescription;
  @Type(type = "jsonb")
  @Column(columnDefinition = "jsonb default '{}'::jsonb")
  private Map<String, Object> contentJson;

  public static EventTrackingPlanMapping from(String planId, Event event){
    return EventTrackingPlanMapping.builder()
        .planId(planId)
        .eventId(event.getEventName())
        .eventDescription(event.getDescription())
        .build();
  }

}
