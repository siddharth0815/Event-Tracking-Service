package io.eventtracking.repositories;

import io.eventtracking.models.entity.Event;
import java.util.Map;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, String> {

  @Modifying(clearAutomatically = true)
  @Query(value = "update event set content_json = :content_json "
      + " where event_name = :event_name ",
      nativeQuery = true)
  @Transactional
  Integer addContentToEvent(@Param("event_name") String eventName,
      @Param("content_json") Map<String, Object> content_json);

}
