package io.eventtracking.repositories;

import io.eventtracking.models.entity.EventTrackingPlanMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventTrackingPlanMappingRepository extends JpaRepository<EventTrackingPlanMapping, String> {


}
