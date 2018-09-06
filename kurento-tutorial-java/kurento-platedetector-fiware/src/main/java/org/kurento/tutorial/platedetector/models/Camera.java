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

package org.kurento.tutorial.platedetector.models;

/**
 * Custom Camera data.
 * 
 * @author Guiomar Tuñón (guiomar.tunon@gmail.com)
 *
 */
public class Camera {

  // here data obtained that can be translated to a "Device Data Model"
  private String[] controlledAsset;
  private String creationDate;
  private String id;
  private String[] ip;
  private String Name;
  private String state; // NEW => CONFIGURED => PROCESSING <=> STOP => DISABLED
  private String type;
  private String updateDate;

  public String[] getControlledAsset() {
	return controlledAsset;
  }

  public String getCreationDate() {
	return creationDate;
  }

  public String getId() {
	return id;
  }

  public String[] getIp() {
	return ip;
  }

  public String getName() {
	return Name;
  }

  public String getState() {
	return state;
  }

  public String getType() {
	return type;
  }

  public String getUpdateDate() {
	return updateDate;
  }

  public void setControlledAsset(String[] strings) {
	this.controlledAsset = strings;
  }

  public void setCreationDate(String creationDate) {
	this.creationDate = creationDate;
  }

  public void setId(String id) {
	this.id = id;
  }

  public void setIp(String[] strings) {
	this.ip = strings;
  }

  public void setName(String name) {
	Name = name;
  }

  public void setState(String state) {
	this.state = state;
  }

  public void setType(String type) {
	this.type = type;
  }

  public void setUpdateDate(String updateDate) {
	this.updateDate = updateDate;
  }

}
