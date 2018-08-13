package org.kurento.orion.connector.integration.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.slf4j.LoggerFactory.getLogger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.kurento.orion.connector.OrionConnectorConfiguration;
import org.kurento.orion.connector.entities.event.MediaEvent;
import org.kurento.orion.connector.entities.event.MediaEventJsonManager;
import org.kurento.orion.connector.entities.event.MediaEventOrionPublisher;
import org.kurento.orion.connector.entities.event.MediaEventOrionReader;
import org.kurento.orion.publisher.OrionPublisherForbidenOperationException;
import org.slf4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class EventTest {
	private static final Logger log = getLogger(EventTest.class);
	private static SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss.SSS");
	
	private static List<String> published_test_ids = new ArrayList<String>();
	
	private static OrionConnectorConfiguration occ = new OrionConnectorConfiguration();
	
	private static MediaEventOrionPublisher<TestBasicAgnosticMediaEvent> meop;
	private static MediaEventOrionReader<TestBasicAgnosticMediaEvent> meor;

	static String eventJson = "{" + 
			"  \"id\": \"mediaEvent_1509702324600\"," + 
			"  \"type\": \"MediaEvent\"," + 
			"  \"eventType\": \"plate-detected\"," + 
			"  \"mediasource\": {" + 
			"    \"name\": \"03ea110c-0ab2-4b19-8618-57f474721c86_kurento.MediaPipeline/28e4ae84-4e96-43bb-a812-538f7950b75f_platedetector.PlateDetectorFilter\"," + 
			"    \"creationTime\": \"2017-11-03T10:45:19Z\"," + 
			"    \"sendTagsInEvents\": false," + 
			"    \"parent\": {" + 
			"      \"name\": \"03ea110c-0ab2-4b19-8618-57f474721c86_kurento.MediaPipeline\"," + 
			"      \"creationTime\": \"2017-11-03T10:45:19Z\"," + 
			"      \"sendTagsInEvents\": false" + 
			"    }" + 
			"  }," + 
			"  \"dateCreated\": \"2017-11-03T10:45:23Z\"," + 
			"  \"dateModified\": \"2017-11-03T10:45:23Z\"" + 
			"}";
	
	public static class TestBasicAgnosticMediaEvent{
		
		
		public TestBasicAgnosticMediaEvent(){}
    
	}
	
	@BeforeAll
	public static void initialization() {
		meop = new MediaEventOrionPublisher<TestBasicAgnosticMediaEvent>(occ) {

			@Override
			public MediaEvent mapEntityToOrionEntity(TestBasicAgnosticMediaEvent entity) {
				MediaEvent event = new MediaEvent();
				/*	device.setId(entity.getId());
				if (entity.getPreferredProtocol()!=null) {
					String[] supportedProtocols = new String[1];
					supportedProtocols[0] = entity.getPreferredProtocol();
					device._getDeviceCommons().setSupportedProtocol(supportedProtocols);
				}				
				Date now = new Date(System.currentTimeMillis());	
				String nowstr = format.format(now);
				device._getGsmaCommons().setDateCreated(nowstr);
				
				device._getGsmaCommons().setName(entity.getName());
				device._getPysicalCommons().setColor(entity.color);
				device.setDeviceState(entity.getState());
				if (entity.getIpAddress()!=null) {
					String[] ipAddresses = new String[1];
					ipAddresses[0] = entity.getIpAddress();
					device.setIpAddress(ipAddresses);
				}
				if (entity.getMacAddress()!=null) {
					String[] macAddresses = new String[1];
					macAddresses[0] = entity.getMacAddress();
					device.setMacAddress(macAddresses);
				}
				device._getGsmaCommons().setDateModified(nowstr); */
				return event; 
			}
	    	
	    };
	    
	    meor = new MediaEventOrionReader<TestBasicAgnosticMediaEvent>(occ) {
	    	
		@Override
		public TestBasicAgnosticMediaEvent mapOrionEntityToEntity(MediaEvent device) {
			
			TestBasicAgnosticMediaEvent agnostic_device = new TestBasicAgnosticMediaEvent();
			
			/*agnostic_device.setId(device.getId());
			if (device._getDeviceCommons().getSupportedProtocol()!=null && device._getDeviceCommons().getSupportedProtocol().length>0)
				agnostic_device.setPreferredProtocol(device._getDeviceCommons().getSupportedProtocol()[0]);
			agnostic_device.setName(device._getGsmaCommons().getName());
			agnostic_device.setColor(device._getPysicalCommons().getColor());
			agnostic_device.setState(device.getDeviceState());
			if (device.getIpAddress()!=null && device.getIpAddress().length>0)
				agnostic_device.setIpAddress(device.getIpAddress()[0]);
			if (device.getMacAddress()!=null && device.getMacAddress().length>0)
				agnostic_device.setMacAddress(device.getMacAddress()[0]);*/
			return agnostic_device;
		}};
	}
	
	@AfterAll
	public static void cleanInsert() {
		for (String id: published_test_ids) {
			meop.delete(id);
			//published_test_ids.remove(id);
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
	    
	    //test source
	    assertTrue(e.getMediasource().getName()
	    		.equals("03ea110c-0ab2-4b19-8618-57f474721c86_kurento.MediaPipeline/28e4ae84-4e96-43bb-a812-538f7950b75f_platedetector.PlateDetectorFilter"));
	    //test source parent
	    assertTrue(e.getMediasource().getParent().getName()
	    		.equals("03ea110c-0ab2-4b19-8618-57f474721c86_kurento.MediaPipeline"));

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
	    assertTrue(e.getMediasource().getName().equals("03ea110c-0ab2-4b19-8618-57f474721c86_kurento.MediaPipeline/28e4ae84-4e96-43bb-a812-538f7950b75f_platedetector.PlateDetectorFilter"));
	    assertTrue(e.getMediasource().getParent().getName()
	    		.equals("03ea110c-0ab2-4b19-8618-57f474721c86_kurento.MediaPipeline"));
	    
	    String json = gson.toJson(e);
	    MediaEvent e2 = gson.fromJson(json, MediaEvent.class);
	    assertTrue(e.getId().equals(e2.getId()),"[ERROR] generated device doesn't contain correct id");
	    assertTrue(e.getMediasource().getName().equals(e2.getMediasource().getName()),"[ERROR] generated device doesn't contain correct source");
	    assertTrue(e.getMediasource().getParent().getName().equals(e2.getMediasource().getParent().getName()),"[ERROR] generated device doesn't contain correct parent source");
	    log.info("[unserializeDeviceTest] END OK");
	}
	
	@Test
	public void publishAndReadMediaEventTest() {
		log.info("[publishAndReadMediaEventTest] INI");
		
		/*Generate a Device*/
		final GsonBuilder gsonBuilder = new GsonBuilder();
	    gsonBuilder.registerTypeAdapter(MediaEvent.class, new MediaEventJsonManager());
	    final Gson gson = gsonBuilder.create();
	    MediaEvent me = gson.fromJson(eventJson, MediaEvent.class);
	    assertTrue(me.getId().equals("mediaEvent_1509702324600"), "[ERROR] generated MediaEvent doesn't contain correct id");
	
		meop.publish(me);
		published_test_ids.add(me.getId());
		
		MediaEvent read = meor.readOrionEntity(me.getId());
		
		assertTrue(me.getId().equals(read.getId()),"[ERROR] MediaEvent doesn't contain correct id");
		assertTrue(me.getMediasource().getName().equals(read.getMediasource().getName()),"[ERROR] generated device doesn't contain correct source");
		 assertTrue(me.getMediasource().getParent().getName().equals(read.getMediasource().getParent().getName()),"[ERROR] generated device doesn't contain correct parent source");
		log.info("[publishAndReadMediaEventTest] END OK");
	}
	
/*	@Test
	public void readDeviceListTest() {
		log.info("[readDeviceListTest] INI");
		
		/*Generate a Device
		final GsonBuilder gsonBuilder = new GsonBuilder();
	    gsonBuilder.registerTypeAdapter(MediaEvent.class, new MediaEventJsonManager());
	    final Gson gson = gsonBuilder.create();
	    MediaEvent d = gson.fromJson(eventJson, MediaEvent.class);
	    d.setId("Test_"+System.currentTimeMillis());
		meop.publish(d);
		published_test_ids.add(d.getId());
		
		List<Device> read = meor.readOrionEntityList(d.getType());
		
		boolean found = false;
		for (MediaEvent rd: read) {
			if(rd.getId().equals(d.getId())) {
				found = true;
				break;
			}
		}
		assertTrue(found,"[ERROR] published device is not in the list");
		log.info("[readDeviceListTest] END OK");
	}
	
	@Test
	public void deleteDeviceByIdTest() {
		log.info("[deleteDeviceByIdTest] INI");
		
		/*Generate a Device
		final GsonBuilder gsonBuilder = new GsonBuilder();
	    gsonBuilder.registerTypeAdapter(MediaEvent.class, new MediaEventJsonManager());
	    final Gson gson = gsonBuilder.create();
	    MediaEvent d = gson.fromJson(eventJson, MediaEvent.class);
	    d.setId("Test_"+System.currentTimeMillis());
		meop.publish(d);
		
		meop.delete(d.getId());
		
		List<Device> read = meor.readOrionEntityList(d.getType());
		
		boolean found = false;
		for (MediaEvent rd: read) {
			if(rd.getId().equals(d.getId())) {
				found = true;
				break;
			}
		}
		assertTrue(!found,"[ERROR] published device hasn't been deleted");
		log.info("[deleteDeviceByIdTest] END OK");
	}*/
	
	@Test 
	public void updateEventTest() {
		log.info("[updateEventTest] INI");
		
		/*Generate a Device*/
		final GsonBuilder gsonBuilder = new GsonBuilder();
	    gsonBuilder.registerTypeAdapter(MediaEvent.class, new MediaEventJsonManager());
	    final Gson gson = gsonBuilder.create();
	    MediaEvent me = gson.fromJson(eventJson, MediaEvent.class);
	    String newID = "Test_"+System.currentTimeMillis();
	    me.setId(newID);
		meop.publish(me);
		published_test_ids.add(me.getId());
		
		Date now = new Date(System.currentTimeMillis());	
		String nowstr = format.format(now);
		
		me._getGsmaCommons().setDateModified(nowstr);
		me.setEventType("plate-detector");
		try {
			meop.update(me);
			//if an exception is not risen:
			fail("[ERROR] EventSeems to be updated and Events Should't be updated");
		}catch(OrionPublisherForbidenOperationException opfoe) {
			log.info("OK: Update not accepted");
		}
		
		MediaEvent read = meor.readOrionEntity(newID);
		
		assertTrue(!read._getGsmaCommons().getDateModified().equals(me._getGsmaCommons().getDateModified()),"[ERROR] date updated");
		assertTrue(!read.getEventType().equals(me.getEventType()),"[ERROR] EventType updated");
		
		log.info("[updateEventTest] END OK");
	}
	
	/*Agnostic devices tests
	@Test
	public void publishAndReadAgnosticDeviceTest() {
		log.info("[publishAndReadAgnosticDeviceTest] INI");
		
		//new agnostic_device
		TestBasicAgnosticMediaEvent agnostic_device = new TestBasicAgnosticDevice();	
		agnostic_device.setId("AgnosticDevice_"+System.currentTimeMillis());
		agnostic_device.setPreferredProtocol("sigfox");
		agnostic_device.setName("publishAndReadAgnosticDeviceTest");
		agnostic_device.setColor("red");
		agnostic_device.setState("STANDBY");
		agnostic_device.setIpAddress("185.35.54.152");
		agnostic_device.setMacAddress("ac:23:23:11:23:cd:54");
		
		//publish
		meop.publish(agnostic_device);
		published_test_ids.add(agnostic_device.getId());
		
		
		TestBasicAgnosticMediaEvent read = meor.readObject(agnostic_device.getId());
		
		assertTrue(read.getId().equals(agnostic_device.getId()),"[ERROR] different IDs");
		assertTrue(read.getColor().equals(agnostic_device.getColor()),"[ERROR] different color");
		assertTrue(read.getPreferredProtocol().equals(agnostic_device.getPreferredProtocol()),"[ERROR] different protocol");
		assertTrue(read.getName().equals(agnostic_device.getName()),"[ERROR] different name");
		assertTrue(read.getState().equals(agnostic_device.getState()),"[ERROR] different state");
		assertTrue(read.getIpAddress().equals(agnostic_device.getIpAddress()),"[ERROR] different ipaddress");
		assertTrue(read.getMacAddress().equals(agnostic_device.getMacAddress()),"[ERROR] different macaddress");

		List <TestBasicAgnosticDevice> readlst = meor.readObjectList("Device");
		boolean found = false;
		for (TestBasicAgnosticMediaEvent tbad : readlst) {
			if (tbad.getId().equals(agnostic_device.getId())) {
				found = true;
				break;
			}
		}		
		assertTrue(found, "[ERROR] AgnosticMediaEvent not found in the list");
		
		//asserts 
		log.info("[publishAndReadAgnosticDeviceTest] END OK");
	}
	
	@Test
	public void updateAgnosticDeviceTest() {
		log.info("[updateAgnosticDeviceTest] INI");
		
		//new agnostic_device
		TestBasicAgnosticMediaEvent agnostic_device = new TestBasicAgnosticDevice();	
		String newID = "AgnosticDevice_"+System.currentTimeMillis();
		agnostic_device.setId(newID);
		agnostic_device.setPreferredProtocol("sigfox");
		agnostic_device.setName("updateAgnosticDeviceTest");
		agnostic_device.setColor("red");
		agnostic_device.setState("STANDBY");
		agnostic_device.setIpAddress("185.35.54.152");
		agnostic_device.setMacAddress("ac:23:23:11:23:cd:54");
		
		//publish
		meop.publish(agnostic_device);
		published_test_ids.add(agnostic_device.getId());
		
		agnostic_device.setState("STARTED");
		meop.update(agnostic_device);
				
		TestBasicAgnosticMediaEvent read = meor.readObject(newID);
		
		assertTrue(read.getId().equals(newID),"[ERROR] different IDs");
		assertTrue(read.getColor().equals(agnostic_device.getColor()),"[ERROR] different color");
		assertTrue(read.getPreferredProtocol().equals(agnostic_device.getPreferredProtocol()),"[ERROR] different protocol");
		assertTrue(read.getName().equals(agnostic_device.getName()),"[ERROR] different name");
		assertTrue(read.getState().equals(agnostic_device.getState()),"[ERROR] different state");
		assertTrue(read.getIpAddress().equals(agnostic_device.getIpAddress()),"[ERROR] different ipaddress");
		assertTrue(read.getMacAddress().equals(agnostic_device.getMacAddress()),"[ERROR] different macaddress");
		
		//asserts 
		log.info("[updateAgnosticDeviceTest] END OK");
	}*/
	
}
