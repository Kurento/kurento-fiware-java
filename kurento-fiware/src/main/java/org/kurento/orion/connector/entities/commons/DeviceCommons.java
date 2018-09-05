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

package org.kurento.orion.connector.entities.commons;

import java.io.Serializable;

/**
 * Device Commons based on
 * https://fiware.github.io/dataModels/specs/Device/device-schema.json#/definitions/Device-Commons
 * 
 * @author Guiomar Tuñón (guiomar.tunon@gmail.com)
 *
 */
public class DeviceCommons implements Serializable {

  private static final long serialVersionUID = 1772956111060812310L;

  @ValidateString(acceptedValues = { "sensor", "actuator", "meter", "HVAC", "network", "multimedia" })
  public String[] category;

  @ValidateString(acceptedValues = { "temperature", "humidity", "light", "motion", "fillingLevel", "occupancy", "power",
      "pressure", "smoke", "energy", "airPollution", "noiseLevel", "weatherConditions", "precipitation", "windSpeed",
      "windDirection", "atmosphericPressure", "solarRadiation", "depth", "pH", "conductivity", "conductance", "tss",
      "tds", "turbidity", "salinity", "orp", "cdom", "waterPollution", "location", "speed", "heading", "weight",
      "waterConsumption", "gasComsumption", "electricityConsumption" })
  public String[] controlledProperty;

  @ValidateString(acceptedValues = { "ul20", "mqtt", "lwm2m", "http", "websocket", "onem2m", "sigfox", "lora", "nb-iot",
      "ec-gsm-iot", "lte-m", "cat-m", "3g", "grps" })
  public String[] supportedProtocol;

  public String[] getCategory() {
	return category;
  }

  public void setCategory(String[] category) {
	this.category = category;
  }

  public String[] getControlledProperty() {
	return controlledProperty;
  }

  public void setControlledProperty(String[] controlledProperty) {
	this.controlledProperty = controlledProperty;
  }

  public String[] getSupportedProtocol() {
	return supportedProtocol;
  }

  public void setSupportedProtocol(String[] supportedProtocol) {
	this.supportedProtocol = supportedProtocol;
  }

}
