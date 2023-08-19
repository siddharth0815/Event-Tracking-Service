package io.eventtracking.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.ZonedDateTime;
import javax.validation.constraints.NotNull;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Event {

  @Id
  private String eventName;

  @Type(type = "jsonb")
  @Column(columnDefinition = "jsonb default '{}'::jsonb", nullable = false)
  @NotNull(message = "Posting Category Template cannot be null")
  private Map<String, Object> contentJson;

  @Type(type = "jsonb")
  @Column(columnDefinition = "jsonb default '{}'::jsonb", nullable = false)
  @NotNull(message = "Posting Category Template cannot be null")
  private Map<String, Object> schemaJson;

  @Column(nullable = false, updatable = false)
  @CreationTimestamp
  private ZonedDateTime createdAt;

  @Column(nullable = false)
  @UpdateTimestamp
  private ZonedDateTime modifiedAt;

}
