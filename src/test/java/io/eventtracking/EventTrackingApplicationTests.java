package io.eventtracking;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.eventtracking.models.entity.Event;
import io.eventtracking.models.request.EventCreationRequest;
import io.eventtracking.models.request.EventUpdationRequest;
import io.eventtracking.models.request.TrackingPlanCreationRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.json.JSONException;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@FixMethodOrder(MethodSorters.DEFAULT)
@SpringBootTest(classes = EventTrackingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EventTrackingApplicationTests {

  @LocalServerPort
  private static int port;
  private static ObjectMapper mapper ;
  private static String uniqueId1;
  private static String uniqueId2;
  private static String uniqueId3;
  TestRestTemplate restTemplate = new TestRestTemplate();
  HttpHeaders headers = new HttpHeaders();

 @BeforeClass
  public static void init() {
    uniqueId1 = UUID.randomUUID().toString();
    uniqueId2 = UUID.randomUUID().toString();
    uniqueId3 = UUID.randomUUID().toString();
    port = 8080;
    mapper = new ObjectMapper();
  }

  /*
  These are integration tests , this test file should be run after every release
  Test_1 : Creates Tracking Plan (TP_1) with an Event (E_1)
  Test_2 : Gets Tracking Plan (TP_1) with ID
  Test_3 : Creates Event with a description (ID same as event already created with TP)
  Test_4 : Creates Event with random ID (Error Case)
  Test_5 : Gets Event By Id
  Test_6 : Updates Event Content for a given Event ID
  Test_7 : Gets Event By Id to check if content is updated
  Test_8 : Creates Tracking Plan (TP_2) with an Event (E_2)
  Test_9 : Links (TP_2) with (E_1)
  Test_10: Gets Tracking Plan (TP_1) with ID, expects 2 events now
   */


  @Test
  @Order(1)
  public void Test_1() {

    TrackingPlanCreationRequest trackingPlan = TrackingPlanCreationRequest.builder()
        .displayName("TrackingPlan_" + uniqueId1)
        .sourceId("Source_Id_" + uniqueId1)
        .sourceName("SourceName_" + uniqueId1)
        .events(Arrays.asList(
            Event.builder()
                .eventName("Event_" + uniqueId1)
                .description("Description1")
                .schemaJson(new HashMap<>())
                .build()))
        .build();

    HttpEntity<TrackingPlanCreationRequest> entity = new HttpEntity<TrackingPlanCreationRequest>
        (trackingPlan, headers);

    ResponseEntity<String> response = restTemplate.exchange(
        createURLWithPort("/trackingPlans"),
        HttpMethod.POST, entity, String.class);

    assertNotNull(response.getBody());
    assertEquals(response.getStatusCode().toString(), "200 OK");
  }

  @Test
  @Order(2)
  public void Test_2() throws JSONException, JsonProcessingException {

    HttpEntity<String> entity = new HttpEntity<>(null, headers);

    ResponseEntity<String> response = restTemplate.exchange(
        createURLWithPort("/trackingPlans/TrackingPlan_" + uniqueId1),
        HttpMethod.GET, entity, String.class);

    Map<String,Object> responseMap = mapper.readValue(response.getBody(), Map.class);
    assertEquals(responseMap.get("displayName"),"TrackingPlan_" + uniqueId1);
    assertEquals(((Map<String, String>)((List)responseMap.get("events")).get(0)).get("eventName"),
        "Event_" + uniqueId1);

  }


  @Test
  @Order(3)
  public void Test_3() throws JSONException, JsonProcessingException {

    EventCreationRequest event = EventCreationRequest.builder()
        .description("Description_"+uniqueId2)
        .name("Event_"+uniqueId1)
        .properties(new HashMap<>()).build();
    HttpEntity<EventCreationRequest> entity = new HttpEntity<EventCreationRequest>
        (event, headers);

    ResponseEntity<String> response = restTemplate.exchange(
        createURLWithPort("/events"),
        HttpMethod.POST, entity, String.class);

    assertNotNull(response.getBody());
    assertEquals(response.getStatusCode().toString(), "200 OK");

  }


  @Test
  @Order(4)
  public void Test_4() throws JSONException, JsonProcessingException {

    EventCreationRequest event = EventCreationRequest.builder()
        .description("Description_"+uniqueId2)
        .name("Event_"+uniqueId2)
        .properties(new HashMap<>()).build();
    HttpEntity<EventCreationRequest> entity = new HttpEntity<EventCreationRequest>
        (event, headers);

    ResponseEntity<String> response = restTemplate.exchange(
        createURLWithPort("/events"),
        HttpMethod.POST, entity, String.class);

    assertNotEquals(response.getStatusCode().toString(), "200 OK");
  }


  @Test
  @Order(5)
  public void Test_5() throws JSONException, JsonProcessingException {

    HttpEntity<String> entity = new HttpEntity<>(null, headers);

    ResponseEntity<String> response = restTemplate.exchange(
        createURLWithPort("/events/Event_" + uniqueId1),
        HttpMethod.GET, entity, String.class);

    Map<String,Object> responseMap = mapper.readValue(response.getBody(), Map.class);
    assertEquals(response.getStatusCode().toString(), "200 OK");
    assertEquals(responseMap.get("eventName"),"Event_" + uniqueId1);
  }

  @Test
  @Order(6)
  public void Test_6() throws JSONException, JsonProcessingException {
   Map<String, Object> propertyMap = new HashMap<>();
   propertyMap.put("key", "value");
    EventUpdationRequest eventUpdationRequest = EventUpdationRequest.builder()
        .properties(propertyMap)
        .build();
    HttpEntity<EventUpdationRequest> entity = new HttpEntity<EventUpdationRequest>
        (eventUpdationRequest, headers);

    ResponseEntity<String> response = restTemplate.exchange(
        createURLWithPort("/events/Event_" + uniqueId1),
        HttpMethod.PUT, entity, String.class);

    assertEquals(response.getStatusCode().toString(), "204 NO_CONTENT");
  }

  @Test
  @Order(7)
  public void Test_7() throws JSONException, JsonProcessingException {

    HttpEntity<String> entity = new HttpEntity<>(null, headers);

    ResponseEntity<String> response = restTemplate.exchange(
        createURLWithPort("/events/Event_" + uniqueId1),
        HttpMethod.GET, entity, String.class);

    Map<String,Object> responseMap = mapper.readValue(response.getBody(), Map.class);
    assertEquals(response.getStatusCode().toString(), "200 OK");
    assertEquals(responseMap.get("eventName"),"Event_" + uniqueId1);
    assertEquals(((Map<String,Object>)responseMap.get("contentJson")).get("key"), "value");
  }

  @Test
  @Order(8)
  public void Test_8() {

    TrackingPlanCreationRequest trackingPlan = TrackingPlanCreationRequest.builder()
        .displayName("TrackingPlan_" + uniqueId2)
        .sourceId("Source_Id_" + uniqueId2)
        .sourceName("SourceName_" + uniqueId2)
        .events(Arrays.asList(
            Event.builder()
                .eventName("Event_" + uniqueId2)
                .description("Description_"+uniqueId2)
                .schemaJson(new HashMap<>())
                .build()))
        .build();

    HttpEntity<TrackingPlanCreationRequest> entity = new HttpEntity<TrackingPlanCreationRequest>
        (trackingPlan, headers);

    ResponseEntity<String> response = restTemplate.exchange(
        createURLWithPort("/trackingPlans"),
        HttpMethod.POST, entity, String.class);

    assertNotNull(response.getBody());
    assertEquals(response.getStatusCode().toString(), "200 OK");
  }

  @Test
  @Order(9)
  public void Test_9() throws JSONException, JsonProcessingException {

    HttpEntity<String> entity = new HttpEntity<>(null, headers);

    ResponseEntity<String> response = restTemplate.exchange(
        createURLWithPort("/events/Event_" + uniqueId1 + "/trackingPlans/" + "TrackingPlan_" + uniqueId2),
        HttpMethod.POST, entity, String.class);

    assertEquals(response.getStatusCode().toString(), "204 NO_CONTENT");
  }

  @Test
  @Order(10)
  public void Test_10() throws JSONException, JsonProcessingException {

    HttpEntity<String> entity = new HttpEntity<>(null, headers);

    ResponseEntity<String> response = restTemplate.exchange(
        createURLWithPort("/trackingPlans/" + "TrackingPlan_" + uniqueId2),
        HttpMethod.GET, entity, String.class);

    Map<String,Object> responseMap = mapper.readValue(response.getBody(), Map.class);
    assertEquals(responseMap.get("displayName"),"TrackingPlan_" + uniqueId2);
    assertEquals(((Map<String, String>)((List)responseMap.get("events")).get(0)).get("eventName"),
        "Event_" + uniqueId2);
    assertEquals(((Map<String, String>)((List)responseMap.get("events")).get(1)).get("eventName"),
        "Event_" + uniqueId1);

  }

  private String createURLWithPort(String uri) {
    return "http://localhost:" + port + uri;
  }


}