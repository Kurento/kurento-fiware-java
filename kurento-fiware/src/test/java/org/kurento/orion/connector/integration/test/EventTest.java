package org.kurento.orion.connector.integration.test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.slf4j.LoggerFactory.getLogger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.kurento.orion.connector.OrionConnectorConfiguration;
import org.kurento.orion.connector.entities.commons.MediaSource;
import org.kurento.orion.connector.entities.event.MediaEvent;
import org.kurento.orion.connector.entities.event.MediaEventJsonManager;
import org.kurento.orion.connector.entities.event.MediaEventOrionPublisher;
import org.kurento.orion.connector.entities.event.MediaEventOrionReader;
import org.kurento.orion.publisher.OrionPublisherForbidenOperationException;
import org.slf4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class EventTest {
  private static final Logger										   log				  = getLogger(EventTest.class);
  private static SimpleDateFormat									   format			  = new SimpleDateFormat(
      "YYYY-MM-dd HH:mm:ss.SSS");

  private static List<String>										   published_test_ids = new ArrayList<String>();

  private static OrionConnectorConfiguration						   occ				  = new OrionConnectorConfiguration();

  private static MediaEventOrionPublisher<TestBasicAgnosticMediaEvent> meop;
  private static MediaEventOrionReader<TestBasicAgnosticMediaEvent>	   meor;

  static String														   eventJson		  = "{"
      + "  \"id\": \"mediaEvent_1509702324600\"," + "  \"type\": \"MediaEvent\","
      + "  \"eventType\": \"plate-detected\"," + "  \"mediasource\": {"
      + "    \"name\": \"03ea110c-0ab2-4b19-8618-57f474721c86_kurento.MediaPipeline/28e4ae84-4e96-43bb-a812-538f7950b75f_platedetector.PlateDetectorFilter\","
      + "    \"creationTime\": \"2017-11-03T10:45:19Z\"," + "    \"sendTagsInEvents\": false," + "    \"parent\": {"
      + "      \"name\": \"03ea110c-0ab2-4b19-8618-57f474721c86_kurento.MediaPipeline\","
      + "      \"creationTime\": \"2017-11-03T10:45:19Z\"," + "      \"sendTagsInEvents\": false" + "    }" + "  },"
      + "  \"dateCreated\": \"2017-11-03T10:45:23Z\"," + "  \"dateModified\": \"2017-11-03T10:45:23Z\"" + "}";

  public static class TestBasicAgnosticMediaEvent {

	public TestBasicAgnosticMediaEvent() {
	}

	String id;
	String type;
	String mediaSource;
	String dateCreated;

	public String getId() {
	  return id;
	}

	public void setId(String id) {
	  this.id = id;
	}

	public String getType() {
	  return type;
	}

	public void setType(String type) {
	  this.type = type;
	}

	public String getMediaSource() {
	  return mediaSource;
	}

	public void setMediaSource(String mediaSource) {
	  this.mediaSource = mediaSource;
	}

	public String getDateCreated() {
	  return dateCreated;
	}

	public void setDateCreated(String dateCreated) {
	  this.dateCreated = dateCreated;
	}
  }

  @BeforeAll
  public static void initialization() {
	meop = new MediaEventOrionPublisher<TestBasicAgnosticMediaEvent>(occ) {

	  @Override
	  public MediaEvent mapEntityToOrionEntity(TestBasicAgnosticMediaEvent entity) {
		MediaEvent event = new MediaEvent();
		event.setId(entity.getId());
		event.setEventType(entity.getType());
		event._getGsmaCommons().setDateCreated(entity.getDateCreated());
		MediaSource ms = new MediaSource();
		ms.setName(entity.getMediaSource());
		event.setMediasource(ms);
		event._getGsmaCommons().setDateModified(entity.getDateCreated());
		return event;
	  }

	};

	meor = new MediaEventOrionReader<TestBasicAgnosticMediaEvent>(occ) {

	  @Override
	  public TestBasicAgnosticMediaEvent mapOrionEntityToEntity(MediaEvent event) {

		TestBasicAgnosticMediaEvent entity = new TestBasicAgnosticMediaEvent();

		entity.setId(event.getId());
		entity.setType(event.getEventType());
		entity.setDateCreated(event._getGsmaCommons().getDateCreated());
		entity.setMediaSource(event.getMediasource().getName());

		return entity;
	  }
	};
  }

  @AfterAll
  public static void cleanInsert() {
	for (String id : published_test_ids) {
	  meop.delete(id);
	  // published_test_ids.remove(id);
	}
  }

  @Test
  public void unserializeEventTest() {

	log.info("[unserializeDeviceTest] INI");
	final GsonBuilder gsonBuilder = new GsonBuilder();
	gsonBuilder.registerTypeAdapter(MediaEvent.class, new MediaEventJsonManager());
	final Gson gson = gsonBuilder.create();
	MediaEvent e = gson.fromJson(eventJson, MediaEvent.class);

	assertTrue(e.getId().equals("mediaEvent_1509702324600"));

	// test source
	assertTrue(e.getMediasource().getName().equals(
	    "03ea110c-0ab2-4b19-8618-57f474721c86_kurento.MediaPipeline/28e4ae84-4e96-43bb-a812-538f7950b75f_platedetector.PlateDetectorFilter"));
	// test source parent
	assertTrue(
	    e.getMediasource().getParent().getName().equals("03ea110c-0ab2-4b19-8618-57f474721c86_kurento.MediaPipeline"));

	log.info("[unserializeDeviceTest] END OK");

  }

  @Test
  public void serializeEventTest() {
	log.info("[serializeDeviceTest] INI");
	final GsonBuilder gsonBuilder = new GsonBuilder();
	gsonBuilder.registerTypeAdapter(MediaEvent.class, new MediaEventJsonManager());
	final Gson gson = gsonBuilder.create();
	MediaEvent e = gson.fromJson(eventJson, MediaEvent.class);
	assertTrue(e.getId().equals("mediaEvent_1509702324600"));
	assertTrue(e.getMediasource().getName().equals(
	    "03ea110c-0ab2-4b19-8618-57f474721c86_kurento.MediaPipeline/28e4ae84-4e96-43bb-a812-538f7950b75f_platedetector.PlateDetectorFilter"));
	assertTrue(
	    e.getMediasource().getParent().getName().equals("03ea110c-0ab2-4b19-8618-57f474721c86_kurento.MediaPipeline"));

	String json = gson.toJson(e);
	MediaEvent e2 = gson.fromJson(json, MediaEvent.class);
	assertTrue(e.getId().equals(e2.getId()), "[ERROR] generated device doesn't contain correct id");
	assertTrue(e.getMediasource().getName().equals(e2.getMediasource().getName()),
	    "[ERROR] generated device doesn't contain correct source");
	assertTrue(e.getMediasource().getParent().getName().equals(e2.getMediasource().getParent().getName()),
	    "[ERROR] generated device doesn't contain correct parent source");
	log.info("[unserializeDeviceTest] END OK");
  }

  @Test
  public void publishAndReadMediaEventTest() {
	log.info("[publishAndReadMediaEventTest] INI");

	/* Generate a Device */
	final GsonBuilder gsonBuilder = new GsonBuilder();
	gsonBuilder.registerTypeAdapter(MediaEvent.class, new MediaEventJsonManager());
	final Gson gson = gsonBuilder.create();
	MediaEvent me = gson.fromJson(eventJson, MediaEvent.class);
	assertTrue(me.getId().equals("mediaEvent_1509702324600"),
	    "[ERROR] generated MediaEvent doesn't contain correct id");

	meop.publish(me);
	published_test_ids.add(me.getId());

	MediaEvent read = meor.readOrionEntity(me.getId());

	assertTrue(me.getId().equals(read.getId()), "[ERROR] MediaEvent doesn't contain correct id");
	assertTrue(me.getMediasource().getName().equals(read.getMediasource().getName()),
	    "[ERROR] generated device doesn't contain correct source");
	assertTrue(me.getMediasource().getParent().getName().equals(read.getMediasource().getParent().getName()),
	    "[ERROR] generated device doesn't contain correct parent source");
	log.info("[publishAndReadMediaEventTest] END OK");
  }

  @Test
  public void readAllEventListTest() {
	log.info("[readAllEventListTest] INI");

	/* Generate a Device */
	final GsonBuilder gsonBuilder = new GsonBuilder();
	gsonBuilder.registerTypeAdapter(MediaEvent.class, new MediaEventJsonManager());
	final Gson gson = gsonBuilder.create();

	// we will generate a list of 10 events with different ids
	for (int i = 0; i < 10; i++) {
	  MediaEvent d = gson.fromJson(eventJson, MediaEvent.class);
	  d.setId("Test_" + System.currentTimeMillis());
	  d.setEventType("list-test");
	  meop.publish(d);
	  published_test_ids.add(d.getId());
	}

	for (int i = 0; i < 10; i++) {
	  MediaEvent d = gson.fromJson(eventJson, MediaEvent.class);
	  d.setId("Test_" + System.currentTimeMillis());
	  d.setEventType("list2-test");
	  meop.publish(d);
	  published_test_ids.add(d.getId());
	}

	List<MediaEvent> read = meor.readOrionEntityList("MediaEvent");

	assertTrue(read.size() >= 20, "[ERROR] the list doesn't contain at least 20 elemens");

	// we need to recover by event Type
	List<MediaEvent> read_2 = meor.readMediaEventListByEventType("list-test");
	assertTrue(read_2.size() == 10, "[ERROR] the list doesn't contain exactly 10 elemens(" + read_2.size() + ")");

	// TODO : we need to recover by creation dates.

	log.info("[readAllEventListTest] END OK");
  }

  @Test
  public void deleteMediaEventByIdTest() {
	log.info("[deleteMediaEventByIdTest] INI");

	/* Generate a Media Event */
	final GsonBuilder gsonBuilder = new GsonBuilder();
	gsonBuilder.registerTypeAdapter(MediaEvent.class, new MediaEventJsonManager());
	final Gson gson = gsonBuilder.create();
	MediaEvent d = gson.fromJson(eventJson, MediaEvent.class);
	d.setId("Test_" + System.currentTimeMillis());
	meop.publish(d);

	meop.delete(d.getId());

	List<MediaEvent> read = meor.readOrionEntityList(d.getType());

	boolean found = false;
	for (MediaEvent rd : read) {
	  if (rd.getId().equals(d.getId())) {
		found = true;
		break;
	  }
	}
	assertTrue(!found, "[ERROR] published event hasn't been deleted");
	log.info("[deleteMediaEventByIdTest] END OK");
  }

  @Test
  public void updateEventTest() {
	log.info("[updateEventTest] INI");

	/* Generate a Device */
	final GsonBuilder gsonBuilder = new GsonBuilder();
	gsonBuilder.registerTypeAdapter(MediaEvent.class, new MediaEventJsonManager());
	final Gson gson = gsonBuilder.create();
	MediaEvent me = gson.fromJson(eventJson, MediaEvent.class);
	String newID = "Test_" + System.currentTimeMillis();
	me.setId(newID);
	meop.publish(me);
	published_test_ids.add(me.getId());

	Date now = new Date(System.currentTimeMillis());
	String nowstr = format.format(now);

	me._getGsmaCommons().setDateModified(nowstr);
	me.setEventType("plate-detector");
	try {
	  meop.update(me);
	  // if an exception is not risen:
	  fail("[ERROR] EventSeems to be updated and Events Should't be updated");
	} catch (OrionPublisherForbidenOperationException opfoe) {
	  log.info("OK: Update not accepted");
	}

	MediaEvent read = meor.readOrionEntity(newID);

	assertTrue(!read._getGsmaCommons().getDateModified().equals(me._getGsmaCommons().getDateModified()),
	    "[ERROR] date updated");
	assertTrue(!read.getEventType().equals(me.getEventType()), "[ERROR] EventType updated");

	log.info("[updateEventTest] END OK");
  }

  /* Agnostic devices tests */
  @Test
  public void publishAndReadAgnosticDeviceTest() {
	log.info("[publishAndReadAgnosticDeviceTest] INI");

	// new agnostic_device
	TestBasicAgnosticMediaEvent agnostic_event = new TestBasicAgnosticMediaEvent();
	agnostic_event.setId("Agnostic_" + System.currentTimeMillis());
	agnostic_event.setType("agnostic-event");
	agnostic_event.setMediaSource("Fake Media Source");
	agnostic_event.setDateCreated("2017-11-03T10:45:23Z");

	// publish
	meop.publish(agnostic_event);
	published_test_ids.add(agnostic_event.getId());

	TestBasicAgnosticMediaEvent read = meor.readObject(agnostic_event.getId());

	assertTrue(read.getId().equals(agnostic_event.getId()), "[ERROR] different IDs");
	assertTrue(read.getType().equals(agnostic_event.getType()), "[ERROR] different type");
	assertTrue(read.getMediaSource().equals(agnostic_event.getMediaSource()), "[ERROR] different media source");
	assertTrue(read.getDateCreated().equals(agnostic_event.getDateCreated()), "[ERROR] different date created");

	List<TestBasicAgnosticMediaEvent> readlst = meor.readObjectList(MediaEvent.TYPE);
	boolean found = false;
	for (TestBasicAgnosticMediaEvent tbad : readlst) {
	  if (tbad.getId().equals(agnostic_event.getId())) {
		found = true;
		break;
	  }
	}
	assertTrue(found, "[ERROR] AgnosticMediaEvent not found in the list");

	// asserts
	log.info("[publishAndReadAgnosticDeviceTest] END OK");
  }
}
