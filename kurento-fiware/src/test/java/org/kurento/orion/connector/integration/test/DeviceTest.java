package org.kurento.orion.connector.integration.test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.slf4j.LoggerFactory.getLogger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.kurento.orion.connector.OrionConnectorConfiguration;
import org.kurento.orion.connector.entities.device.Device;
import org.kurento.orion.connector.entities.device.DeviceJsonManager;
import org.kurento.orion.connector.entities.device.DeviceOrionPublisher;
import org.kurento.orion.connector.entities.device.DeviceOrionReader;
import org.slf4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class DeviceTest {

  private static final Logger log = getLogger(DeviceTest.class);
  private static SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss.SSS");

  private static List<String> published_test_ids = new ArrayList<String>();

  private static OrionConnectorConfiguration occ = new OrionConnectorConfiguration();
  private static DeviceOrionPublisher<TestBasicAgnosticDevice> dop;
  private static DeviceOrionReader<TestBasicAgnosticDevice> dor;

  static String deviceJson = "{\n" + "  \"id\": \"device-9845A\",\n" + "  \"type\": \"Device\",\n"
      + "  \"category\": [\"sensor\"],\n" + "  \"controlledProperty\": [\"fillingLevel\",\"temperature\"],\n"
      + "  \"controlledAsset\": [\"wastecontainer-Osuna-100\"],\n" + "  \"ipAddress\": [\"192.14.56.78\"],\n"
      + "  \"mcc\": \"214\",\n" + "  \"mnc\": \"07\",\n" + "  \"batteryLevel\": 0.75,\n"
      + "  \"serialNumber\": \"9845A\",\n" + "  \"refDeviceModel\": \"myDevice-wastecontainer-sensor-345\",\n"
      + "  \"value\": \"l%3D0.22%3Bt%3D21.2\",\n" + "  \"deviceState\": \"ok\",\n"
      + "  \"dateFirstUsed\": \"2014-09-11T11:00:00Z\",\n" + "  \"owner\": [\"http://person.org/leon\"]\n" + "}";

  public static class TestBasicAgnosticDevice {
	public String getId() {
	  return id;
	}

	public void setId(String id) {
	  this.id = id;
	}

	public String getName() {
	  return name;
	}

	public void setName(String name) {
	  this.name = name;
	}

	public String getState() {
	  return state;
	}

	public void setState(String state) {
	  this.state = state;
	}

	public String getIpAddress() {
	  return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
	  this.ipAddress = ipAddress;
	}

	public String getMacAddress() {
	  return macAddress;
	}

	public void setMacAddress(String macAddress) {
	  this.macAddress = macAddress;
	}

	public String getType() {
	  return type;
	}

	public void setType(String type) {
	  this.type = type;
	}

	public String getPreferredProtocol() {
	  return preferredProtocol;
	}

	public void setPreferredProtocol(String preferredProtocol) {
	  this.preferredProtocol = preferredProtocol;
	}

	public String getColor() {
	  return preferredProtocol;
	}

	public void setColor(String color) {
	  this.color = color;
	}

	String id;
	String name;
	String state;
	String ipAddress;
	String macAddress;
	String type;
	String preferredProtocol;
	String color;

	public TestBasicAgnosticDevice() {
	}

  }

  @BeforeAll
  public static void initialization() {
	dop = new DeviceOrionPublisher<TestBasicAgnosticDevice>(occ) {

	  @Override
	  public Device mapEntityToOrionEntity(TestBasicAgnosticDevice entity) {
		Device device = new Device();
		device.setId(entity.getId());
		if (entity.getPreferredProtocol() != null) {
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
		if (entity.getIpAddress() != null) {
		  String[] ipAddresses = new String[1];
		  ipAddresses[0] = entity.getIpAddress();
		  device.setIpAddress(ipAddresses);
		}
		if (entity.getMacAddress() != null) {
		  String[] macAddresses = new String[1];
		  macAddresses[0] = entity.getMacAddress();
		  device.setMacAddress(macAddresses);
		}
		device._getGsmaCommons().setDateModified(nowstr);
		return device;
	  }

	};

	dor = new DeviceOrionReader<TestBasicAgnosticDevice>(occ) {
	  @Override
	  public TestBasicAgnosticDevice mapOrionEntityToEntity(Device device) {

		TestBasicAgnosticDevice agnostic_device = new TestBasicAgnosticDevice();

		agnostic_device.setId(device.getId());
		if (device._getDeviceCommons().getSupportedProtocol() != null
		    && device._getDeviceCommons().getSupportedProtocol().length > 0)
		  agnostic_device.setPreferredProtocol(device._getDeviceCommons().getSupportedProtocol()[0]);
		agnostic_device.setName(device._getGsmaCommons().getName());
		agnostic_device.setColor(device._getPysicalCommons().getColor());
		agnostic_device.setState(device.getDeviceState());
		if (device.getIpAddress() != null && device.getIpAddress().length > 0)
		  agnostic_device.setIpAddress(device.getIpAddress()[0]);
		if (device.getMacAddress() != null && device.getMacAddress().length > 0)
		  agnostic_device.setMacAddress(device.getMacAddress()[0]);
		return agnostic_device;
	  }
	};
  }

  @AfterAll
  public static void cleanInsert() {
	for (String id : published_test_ids) {
	  dop.delete(id);
	  // published_test_ids.remove(id);
	}
  }

  @Test
  public void unserializeDeviceTest() {

	log.info("[unserializeDeviceTest] INI");
	final GsonBuilder gsonBuilder = new GsonBuilder();
	gsonBuilder.registerTypeAdapter(Device.class, new DeviceJsonManager());
	final Gson gson = gsonBuilder.create();
	Device d = gson.fromJson(deviceJson, Device.class);

	assertTrue(d.getId().equals("device-9845A"));
	log.info("[unserializeDeviceTest] END OK");

  }

  @Test
  public void serializeDeviceTest() {
	log.info("[serializeDeviceTest] INI");
	final GsonBuilder gsonBuilder = new GsonBuilder();
	gsonBuilder.registerTypeAdapter(Device.class, new DeviceJsonManager());
	final Gson gson = gsonBuilder.create();
	Device d = gson.fromJson(deviceJson, Device.class);
	assertTrue(d.getId().equals("device-9845A"));

	String json = gson.toJson(d);
	Device d2 = gson.fromJson(json, Device.class);
	assertTrue(d.getId().equals(d2.getId()), "[ERROR] generated device doesn't contain correct id");
	log.info("[unserializeDeviceTest] END OK");
  }

  @Test
  public void publishAndReadDeviceTest() {
	log.info("[publishAndReadDeviceTest] INI");

	/* Generate a Device */
	final GsonBuilder gsonBuilder = new GsonBuilder();
	gsonBuilder.registerTypeAdapter(Device.class, new DeviceJsonManager());
	final Gson gson = gsonBuilder.create();
	Device d = gson.fromJson(deviceJson, Device.class);
	assertTrue(d.getId().equals("device-9845A"), "[ERROR] generated device doesn't contain correct id");

	dop.publish(d);
	published_test_ids.add(d.getId());

	Device read = dor.readOrionEntity(d.getId());

	assertTrue(d.getId().equals(read.getId()), "[ERROR] readdevice doesn't contain correct id");

	log.info("[publishAndReadDeviceTest] END OK");
  }

  @Test
  public void readDeviceListTest() {
	log.info("[readDeviceListTest] INI");

	/* Generate a Device */
	final GsonBuilder gsonBuilder = new GsonBuilder();
	gsonBuilder.registerTypeAdapter(Device.class, new DeviceJsonManager());
	final Gson gson = gsonBuilder.create();
	Device d = gson.fromJson(deviceJson, Device.class);
	d.setId("Test_" + System.currentTimeMillis());
	dop.publish(d);
	published_test_ids.add(d.getId());

	List<Device> read = dor.readOrionEntityList(d.getType());

	boolean found = false;
	for (Device rd : read) {
	  if (rd.getId().equals(d.getId())) {
		found = true;
		break;
	  }
	}
	assertTrue(found, "[ERROR] published device is not in the list");
	log.info("[readDeviceListTest] END OK");
  }

  @Test
  public void deleteDeviceByIdTest() {
	log.info("[deleteDeviceByIdTest] INI");

	/* Generate a Device */
	final GsonBuilder gsonBuilder = new GsonBuilder();
	gsonBuilder.registerTypeAdapter(Device.class, new DeviceJsonManager());
	final Gson gson = gsonBuilder.create();
	Device d = gson.fromJson(deviceJson, Device.class);
	d.setId("Test_" + System.currentTimeMillis());
	dop.publish(d);

	dop.delete(d.getId());

	List<Device> read = dor.readOrionEntityList(d.getType());

	boolean found = false;
	for (Device rd : read) {
	  if (rd.getId().equals(d.getId())) {
		found = true;
		break;
	  }
	}
	assertTrue(!found, "[ERROR] published device hasn't been deleted");
	log.info("[deleteDeviceByIdTest] END OK");
  }

  @Test
  public void updateDeviceTest() {
	log.info("[deleteDeviceByIdTest] INI");

	/* Generate a Device */
	final GsonBuilder gsonBuilder = new GsonBuilder();
	gsonBuilder.registerTypeAdapter(Device.class, new DeviceJsonManager());
	final Gson gson = gsonBuilder.create();
	Device d = gson.fromJson(deviceJson, Device.class);
	String newID = "Test_" + System.currentTimeMillis();
	d.setId(newID);
	dop.publish(d);
	published_test_ids.add(d.getId());

	Date now = new Date(System.currentTimeMillis());
	String nowstr = format.format(now);

	d._getGsmaCommons().setDateModified(nowstr);
	d.setDeviceState("STARTED");

	dop.update(d);

	Device read = dor.readOrionEntity(newID);

	assertTrue(read._getGsmaCommons().getDateModified().equals(d._getGsmaCommons().getDateModified()),
	    "[ERROR] date not updated");
	assertTrue(read.getDeviceState().equals(d.getDeviceState()), "[ERROR] state not updated");

	log.info("[deleteDeviceByIdTest] END OK");
  }

  /* Agnostic devices tests */
  @Test
  public void publishAndReadAgnosticDeviceTest() {
	log.info("[publishAndReadAgnosticDeviceTest] INI");

	// new agnostic_device
	TestBasicAgnosticDevice agnostic_device = new TestBasicAgnosticDevice();
	agnostic_device.setId("AgnosticDevice_" + System.currentTimeMillis());
	agnostic_device.setPreferredProtocol("sigfox");
	agnostic_device.setName("publishAndReadAgnosticDeviceTest");
	agnostic_device.setColor("red");
	agnostic_device.setState("STANDBY");
	agnostic_device.setIpAddress("185.35.54.152");
	agnostic_device.setMacAddress("ac:23:23:11:23:cd:54");

	// publish
	dop.publish(agnostic_device);
	published_test_ids.add(agnostic_device.getId());

	TestBasicAgnosticDevice read = dor.readObject(agnostic_device.getId());

	assertTrue(read.getId().equals(agnostic_device.getId()), "[ERROR] different IDs");
	assertTrue(read.getColor().equals(agnostic_device.getColor()), "[ERROR] different color");
	assertTrue(read.getPreferredProtocol().equals(agnostic_device.getPreferredProtocol()),
	    "[ERROR] different protocol");
	assertTrue(read.getName().equals(agnostic_device.getName()), "[ERROR] different name");
	assertTrue(read.getState().equals(agnostic_device.getState()), "[ERROR] different state");
	assertTrue(read.getIpAddress().equals(agnostic_device.getIpAddress()), "[ERROR] different ipaddress");
	assertTrue(read.getMacAddress().equals(agnostic_device.getMacAddress()), "[ERROR] different macaddress");

	List<TestBasicAgnosticDevice> readlst = dor.readObjectList("Device");
	boolean found = false;
	for (TestBasicAgnosticDevice tbad : readlst) {
	  if (tbad.getId().equals(agnostic_device.getId())) {
		found = true;
		break;
	  }
	}
	assertTrue(found, "[ERROR] AgnosticDevice not found in the list");

	// asserts
	log.info("[publishAndReadAgnosticDeviceTest] END OK");
  }

  @Test
  public void updateAgnosticDeviceTest() {
	log.info("[updateAgnosticDeviceTest] INI");

	// new agnostic_device
	TestBasicAgnosticDevice agnostic_device = new TestBasicAgnosticDevice();
	String newID = "AgnosticDevice_" + System.currentTimeMillis();
	agnostic_device.setId(newID);
	agnostic_device.setPreferredProtocol("sigfox");
	agnostic_device.setName("updateAgnosticDeviceTest");
	agnostic_device.setColor("red");
	agnostic_device.setState("STANDBY");
	agnostic_device.setIpAddress("185.35.54.152");
	agnostic_device.setMacAddress("ac:23:23:11:23:cd:54");

	// publish
	dop.publish(agnostic_device);
	published_test_ids.add(agnostic_device.getId());

	agnostic_device.setState("STARTED");
	dop.update(agnostic_device);

	TestBasicAgnosticDevice read = dor.readObject(newID);

	assertTrue(read.getId().equals(newID), "[ERROR] different IDs");
	assertTrue(read.getColor().equals(agnostic_device.getColor()), "[ERROR] different color");
	assertTrue(read.getPreferredProtocol().equals(agnostic_device.getPreferredProtocol()),
	    "[ERROR] different protocol");
	assertTrue(read.getName().equals(agnostic_device.getName()), "[ERROR] different name");
	assertTrue(read.getState().equals(agnostic_device.getState()), "[ERROR] different state");
	assertTrue(read.getIpAddress().equals(agnostic_device.getIpAddress()), "[ERROR] different ipaddress");
	assertTrue(read.getMacAddress().equals(agnostic_device.getMacAddress()), "[ERROR] different macaddress");

	// asserts
	log.info("[updateAgnosticDeviceTest] END OK");
  }

}
