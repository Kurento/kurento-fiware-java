/*
 * (C) Copyright 2017 Kurento (http://kurento.org/)
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
package org.kurento.tutorial.platedetector.orion.models;

import java.util.Date;

public class MediaObject {

	protected String name;
	protected Date creationTime;
	protected Boolean sendTagsInEvents;
	protected MediaObject parent;

	public MediaObject() {

	}

	public MediaObject(org.kurento.client.MediaObject mediaObject) {
		if (mediaObject == null) {
			throw new IllegalArgumentException("mediaObject can not be null");
		}
		this.name = mediaObject.getName();
		this.creationTime = new Date(Long.valueOf(mediaObject.getCreationTime()) * 1000);
		this.sendTagsInEvents = mediaObject.getSendTagsInEvents();

		if (mediaObject.getParent() != null) {
			this.parent = new MediaObject(mediaObject.getParent());
		}

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public Boolean getSendTagsInEvents() {
		return sendTagsInEvents;
	}

	public void setSendTagsInEvents(Boolean sendTagsInEvents) {
		this.sendTagsInEvents = sendTagsInEvents;
	}

	public MediaObject getParent() {
		return parent;
	}

	public void setParent(MediaObject parent) {
		this.parent = parent;
	}

}
