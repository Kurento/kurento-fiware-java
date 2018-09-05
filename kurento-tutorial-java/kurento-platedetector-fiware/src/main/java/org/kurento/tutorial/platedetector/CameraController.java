/*
 * Copyright 2018 Kurento (https://www.kurento.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kurento.tutorial.platedetector;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kurento.orion.connector.OrionConnectorConfiguration;
import org.kurento.tutorial.platedetector.models.Camera;
import org.kurento.tutorial.platedetector.orion.CamPublisher;
import org.kurento.tutorial.platedetector.orion.CamReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

/**
 * Controller for managing a {@link Camera}
 * 
 * @author Guiomar Tuñón (guiomar.tunon@gmail.com)
 *
 */
@RestController
public class CameraController {

  private final Logger log = LoggerFactory.getLogger(CameraController.class);
  private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:SS");

  /**
   * Process the request for a new "source" device to be inserted in orion
   * 
   * @param req
   * @return the new camera
   */
  @RequestMapping(value = { "/camera/new" }, method = { RequestMethod.POST })
  @ResponseBody
  public String NewCamera(HttpServletRequest req) {
	String response = "{\"result\": __RESULT__}";

	try {
	  final OrionConnectorConfiguration orionConnectorConfiguration = new OrionConnectorConfiguration();

	  CamPublisher cp = new CamPublisher(orionConnectorConfiguration);

	  Camera cam = new Camera();

	  cam.setId("CameraDefault_" + System.currentTimeMillis());

	  cam.setName("Kurento-plate-detector Camera");

	  cam.setType("plate-detector");

	  // check if Dates are set.
	  cam.setCreationDate(format.format(System.currentTimeMillis()));
	  cam.setUpdateDate(cam.getCreationDate());
	  cam.setState("NEW");

	  // TODO ips will be updated on ICE negotiation

	  Gson gson = new Gson();
	  String jsonInString = gson.toJson(cam);
	  log.debug("CAM:{}", jsonInString);

	  cp.publish(cam);

	  response = response.replace("__RESULT__", "\"OK\"," + "\"device\":" + jsonInString);

	} catch (Throwable t) {
	  response = response.replace("__RESULT__", "error");
	}

	return response;
  }

  /**
   * Process the request to "switch on" the device (updates on orion)
   * 
   * @param id
   *          the id of the camera that should be updated
   * @param req
   * @return
   */
  @RequestMapping(value = { "/device/activate/{DeviceID}" }, method = { RequestMethod.POST })
  @ResponseBody
  public String activateDevice(@PathVariable(value = "DeviceID") String id, HttpServletRequest req) {
	String response = "{\"result\": __RESULT__}";
	log.debug("[activateDevice] INI {}", id);
	try {

	  final OrionConnectorConfiguration orionConnectorConfiguration = new OrionConnectorConfiguration();
	  CamPublisher cp = new CamPublisher(orionConnectorConfiguration);
	  CamReader cr = new CamReader(orionConnectorConfiguration);

	  Camera cam = cr.readObject(id);
	  cam.setState("PROCESSING");
	  cam.setUpdateDate(format.format(System.currentTimeMillis()));
	  cp.update(cam);
	  Gson gson = new Gson();
	  String jsonInString = gson.toJson(cam);
	  response = response.replace("__RESULT__", "\"OK\"," + "\"device\":" + jsonInString);
	} catch (Throwable t) {
	  response = response.replace("__RESULT__", "error");
	}
	log.debug("[activateDevice] END");

	return response;
  }

  /**
   * Process the request to "switch off" the device (updates on orion)
   * 
   * @param id
   *          the id of the camera that should be updated
   * @param req
   * @return
   */
  @RequestMapping(value = { "/device/pause/{DeviceID}" }, method = { RequestMethod.POST })
  @ResponseBody
  public String pauseDevice(@PathVariable(value = "DeviceID") String id, HttpServletRequest req) {
	String response = "{\"result\": __RESULT__}";
	log.debug("[activateDevice] INI {}", id);
	try {

	  final OrionConnectorConfiguration orionConnectorConfiguration = new OrionConnectorConfiguration();
	  CamPublisher cp = new CamPublisher(orionConnectorConfiguration);
	  CamReader cr = new CamReader(orionConnectorConfiguration);

	  Camera cam = cr.readObject(id);
	  cam.setState("STOP");
	  cam.setUpdateDate(format.format(System.currentTimeMillis()));
	  cp.update(cam);
	  Gson gson = new Gson();
	  String jsonInString = gson.toJson(cam);
	  response = response.replace("__RESULT__", "\"OK\"," + "\"device\":" + jsonInString);
	} catch (Throwable t) {
	  response = response.replace("__RESULT__", "error");
	  // sendError(session, t.getMessage());
	}
	log.debug("[activateDevice] END");

	return response;
  }

  @ExceptionHandler({ IllegalArgumentException.class, NullPointerException.class })
  void handleBadRequests(HttpServletResponse response) throws IOException {
	log.debug("[handleBadRequests] RISED");
	response.sendError(HttpStatus.BAD_REQUEST.value());
  }

  @SuppressWarnings("unused")
  private void debugRequest(HttpServletRequest req) {
	Enumeration<String> parameterNames = req.getParameterNames();
	log.debug("DEBUG: request parameters");
	while (parameterNames.hasMoreElements()) {

	  String paramName = parameterNames.nextElement();
	  log.debug(paramName + ":");

	  String[] paramValues = req.getParameterValues(paramName);
	  for (int i = 0; i < paramValues.length; i++) {
		String paramValue = paramValues[i];
		log.debug("\t" + paramValue);
	  }
	}
  }
}
