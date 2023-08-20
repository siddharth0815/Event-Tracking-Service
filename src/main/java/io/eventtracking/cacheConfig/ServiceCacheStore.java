package io.eventtracking.cacheConfig;


import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import io.eventtracking.models.entity.Event;
import io.eventtracking.models.entity.EventTrackingPlanMapping;
import io.eventtracking.models.entity.TrackingPlan;
import io.eventtracking.repositories.EventRepository;
import io.eventtracking.repositories.EventTrackingPlanMappingRepository;
import io.eventtracking.repositories.TrackingPlanRepository;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
public class ServiceCacheStore {

  private LoadingCache<String, Optional<Event>> eventCache;
  private LoadingCache<String, Optional<TrackingPlan>> trackingPlanCache;
  private LoadingCache<String, Optional<EventTrackingPlanMapping>> eventTrackingPlanMappingCache;
  private EventRepository eventRepository;
  private TrackingPlanRepository trackingPlanRepository;
  private EventTrackingPlanMappingRepository eventTrackingPlanMappingRepository;

  @Autowired
  public ServiceCacheStore(
      LoadingCache<String, Optional<Event>> eventCache,
      LoadingCache<String, Optional<TrackingPlan>> trackingPlanCache,
      LoadingCache<String, Optional<EventTrackingPlanMapping>> eventTrackingPlanMappingCache) {
    this.eventCache = eventCache;
    this.trackingPlanCache = trackingPlanCache;
    this.eventTrackingPlanMappingCache = eventTrackingPlanMappingCache;
    initializeEventCache();
    initializeTrackingPlanCache();
    initializeEventTrackingPlanMappingCache();
  }

  public Optional<Event>  getEvent(String eventId) throws ExecutionException {
   return eventCache.get(eventId);
  }

  public Optional<TrackingPlan> getTrackingPlan(String trackingPlanId) throws ExecutionException {
    return trackingPlanCache.get(trackingPlanId);
  }

  public Optional<EventTrackingPlanMapping> getEventTrackingPlanMapping(String key)
      throws ExecutionException {
    return eventTrackingPlanMappingCache.get(key);
  }


  private void initializeEventCache() {
    eventCache = CacheBuilder.newBuilder()
        .concurrencyLevel(4)
        .initialCapacity(10000)
        .maximumSize(400000)
        .expireAfterWrite(30, TimeUnit.DAYS)
        .recordStats()
        .build(new CacheLoader<String, Optional<Event>>() {
          @Override
          public Optional<Event> load(String key) throws Exception {
            return eventRepository.findById(key);
          }
        });
  }

  private void initializeTrackingPlanCache() {
    trackingPlanCache = CacheBuilder.newBuilder()
        .concurrencyLevel(4)
        .initialCapacity(10000)
        .maximumSize(400000)
        .expireAfterWrite(30, TimeUnit.DAYS)
        .recordStats()
        .build(new CacheLoader<String, Optional<TrackingPlan>>() {
          @Override
          public Optional<TrackingPlan> load(String key) throws Exception {
            return trackingPlanRepository.findById(key);
          }
        });
  }


  private void initializeEventTrackingPlanMappingCache() {
    eventTrackingPlanMappingCache = CacheBuilder.newBuilder()
        .concurrencyLevel(4)
        .initialCapacity(10000)
        .maximumSize(400000)
        .expireAfterWrite(30, TimeUnit.DAYS)
        .recordStats()
        .build(new CacheLoader<String, Optional<EventTrackingPlanMapping>>() {
          @Override
          public Optional<EventTrackingPlanMapping> load(String key) throws Exception {
            String eventId = key.split("_",2)[0];
            String trackingPlanId = key.split("_", 2)[1];
            return eventTrackingPlanMappingRepository.findByEventIdAndPlanId(eventId, trackingPlanId);
          }
        });
  }


}

