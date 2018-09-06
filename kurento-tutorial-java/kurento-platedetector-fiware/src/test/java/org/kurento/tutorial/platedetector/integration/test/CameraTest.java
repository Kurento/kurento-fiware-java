/*
 * (C) Copyright 2018 Kurento (http://kurento.org/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.kurento.tutorial.platedetector.integration.test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.slf4j.LoggerFactory.getLogger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.kurento.orion.connector.OrionConnectorConfiguration;
import org.kurento.tutorial.platedetector.models.Camera;
import org.kurento.tutorial.platedetector.orion.CamPublisher;
import org.kurento.tutorial.platedetector.orion.CamReader;
import org.slf4j.Logger;

/**
 * 
 * @author Guiomar Tuñón (guiomar.tunon@gmail.com)
 *
 */
public class CameraTest {

  private static final Logger log = getLogger(CameraTest.class);
  private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:SS");

  private static List<String> published_test_ids = new ArrayList<String>();

  private static OrionConnectorConfiguration occ = new OrionConnectorConfiguration();

  private static CamPublisher cp;
  private static CamReader cr;

  @BeforeAll
  public static void initialization() {
	cp = new CamPublisher(occ);

	cr = new CamReader(occ);
  }

  @AfterAll
  public static void cleanInsert() {
	for (String id : published_test_ids) {
	  cp.delete(id);
	  // published_test_ids.remove(id);
	}
  }

  @Test
  public void publishAndReadCameraTest() {
	log.info("[publishAndReadCameraTest] INI");

	// new agnostic_device
	Camera agnostic_device = new Camera();
	agnostic_device.setId("Camera_" + System.currentTimeMillis());
	agnostic_device.setName("publishAndReadCameraTest");
	agnostic_device.setState("STANDBY");
	String[] ipAddress = { "185.35.54.152" };
	agnostic_device.setIp(ipAddress);
	agnostic_device.setCreationDate(format.format(System.currentTimeMillis()));
	String[] controlledAsset = { "vehicles" };
	agnostic_device.setControlledAsset(controlledAsset);
	agnostic_device.setUpdateDate(agnostic_device.getCreationDate());

	// publish
	cp.publish(agnostic_device);
	published_test_ids.add(agnostic_device.getId());

	Camera read = cr.readObject(agnostic_device.getId());

	assertTrue(read.getId().equals(agnostic_device.getId()), "[ERROR] different IDs");
	assertTrue(read.getName().equals(agnostic_device.getName()), "[ERROR] different name");
	assertTrue(read.getState().equals(agnostic_device.getState()), "[ERROR] different state");
	assertTrue(read.getIp()[0].equals(agnostic_device.getIp()[0]), "[ERROR] different name");
	assertTrue(read.getState().equals(agnostic_device.getState()), "[ERROR] different state");
	assertTrue(read.getControlledAsset()[0].equals(agnostic_device.getControlledAsset()[0]),
	    "[ERROR] different controlled asset");

	List<Camera> readlst = cr.readObjectList("Device");
	boolean found = false;
	for (Camera tbad : readlst) {
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
	Camera agnostic_device = new Camera();
	String newID = "Camera_" + System.currentTimeMillis();
	agnostic_device.setId(newID);
	agnostic_device.setName("publishAndReadCameraTest");
	agnostic_device.setState("STANDBY");
	String[] ipAddress = { "185.35.54.152" };
	agnostic_device.setIp(ipAddress);
	agnostic_device.setCreationDate(format.format(System.currentTimeMillis()));
	String[] controlledAsset = { "vehicles" };
	agnostic_device.setControlledAsset(controlledAsset);
	agnostic_device.setUpdateDate(agnostic_device.getCreationDate());

	// publish
	cp.publish(agnostic_device);
	published_test_ids.add(agnostic_device.getId());

	agnostic_device.setState("STARTED");
	agnostic_device.setUpdateDate(format.format(System.currentTimeMillis()));
	cp.update(agnostic_device);

	Camera read = cr.readObject(newID);

	assertTrue(read.getId().equals(newID), "[ERROR] different IDs");
	assertTrue(read.getName().equals(agnostic_device.getName()), "[ERROR] different name");
	assertTrue(read.getState().equals(agnostic_device.getState()), "[ERROR] different state");
	assertTrue(read.getIp()[0].equals(agnostic_device.getIp()[0]), "[ERROR] different name");
	assertTrue(read.getState().equals(agnostic_device.getState()), "[ERROR] different state");
	assertTrue(read.getControlledAsset()[0].equals(agnostic_device.getControlledAsset()[0]),
	    "[ERROR] different controlled asset");

	// asserts
	log.info("[updateAgnosticDeviceTest] END OK");
  }

}
