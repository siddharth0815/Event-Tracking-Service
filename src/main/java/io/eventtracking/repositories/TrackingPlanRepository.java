package io.eventtracking.repositories;

import io.eventtracking.models.entity.TrackingPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackingPlanRepository extends JpaRepository<TrackingPlan, String> {

}
