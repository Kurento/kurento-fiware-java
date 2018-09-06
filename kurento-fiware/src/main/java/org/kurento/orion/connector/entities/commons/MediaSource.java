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
 * Media Source definition from
 * https://fiware.github.io/dataModels/specs/Media/mediasource-schema.json
 * 
 * @author Guiomar Tuñón (guiomar.tunon@gmail.com)
 *
 */
public class MediaSource implements Serializable {

  private static final long serialVersionUID = -767808878000289128L;

  String name;
  String creationTime;
  boolean sendTagsInEvents;
  MediaSource parent;

  public MediaSource() {
  }

  public MediaSource(String name, String creationTime, boolean sendTagsInEvents, MediaSource parent) {
	super();
	this.name = name;
	this.creationTime = creationTime;
	this.sendTagsInEvents = sendTagsInEvents;
	this.parent = parent;
  }

  public String getName() {
	return name;
  }

  public void setName(String name) {
	this.name = name;
  }

  public String getCreationTime() {
	return creationTime;
  }

  public void setCreationTime(String creationTime) {
	this.creationTime = creationTime;
  }

  public boolean isSendTagsInEvents() {
	return sendTagsInEvents;
  }

  public void setSendTagsInEvents(boolean sendTagsInEvents) {
	this.sendTagsInEvents = sendTagsInEvents;
  }

  public MediaSource getParent() {
	return parent;
  }

  public void setParent(MediaSource parent) {
	this.parent = parent;
  }

}
