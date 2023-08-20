package io.eventtracking.models.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.eventtracking.models.entity.Event;
import io.eventtracking.models.entity.EventTrackingPlanMapping;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Builder
@Data
@AllArgsConstructor
public class TrackingPlanCreationRequest {
    @JsonInclude(Include.NON_NULL)
    private String sourceName;
    @JsonInclude(Include.NON_NULL)
    private String sourceId;
    private String displayName;
    private List<Event> events;

    public static TrackingPlanCreationRequest from(String trackingPlan,
        Optional<List<EventTrackingPlanMapping>> mappings){
        List<Event> events = new ArrayList<>();
        mappings.ifPresent(eventTrackingPlanMappings -> eventTrackingPlanMappings.forEach(
            mapping -> {
                Event event = Event.builder()
                    .eventName(mapping.getEventId())
                    .description(mapping.getEventDescription())
                    .contentJson(mapping.getContentJson())
                    .build();
                events.add(event);
            }));
        return TrackingPlanCreationRequest.builder()
            .displayName(trackingPlan)
            .events(events).build();
    }
}
