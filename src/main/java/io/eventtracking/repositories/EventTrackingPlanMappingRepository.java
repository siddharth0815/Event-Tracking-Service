package io.eventtracking.repositories;

import io.eventtracking.models.entity.EventTrackingPlanMapping;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventTrackingPlanMappingRepository extends JpaRepository<EventTrackingPlanMapping, String> {

  Optional<List<EventTrackingPlanMapping>> findByEventId(String eventId);
  Optional<List<EventTrackingPlanMapping>> findByPlanId(String planId, Pageable page);
  Optional<EventTrackingPlanMapping> findByEventIdAndPlanId(String eventId, String planId);

}
