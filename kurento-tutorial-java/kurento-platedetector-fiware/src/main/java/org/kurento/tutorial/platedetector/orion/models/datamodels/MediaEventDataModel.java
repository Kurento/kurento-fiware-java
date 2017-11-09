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
package org.kurento.tutorial.platedetector.orion.models.datamodels;

import java.util.Date;

import org.kurento.client.MediaEvent;
import org.kurento.orion.connector.entities.OrionEntity;
import org.kurento.tutorial.platedetector.orion.models.MediaObject;

public class MediaEventDataModel implements OrionEntity {

	private static final String MEDIA_EVENT_TYPE = "MediaEvent";

	protected String id;
	protected String type = MEDIA_EVENT_TYPE;

	/**
	 *
	 * Type of event that was raised
	 *
	 **/
	protected String eventType;

	/**
	 *
	 *
	 **/
	protected Date timestamp;

	/**
	 *
	 * Object that raised the event
	 *
	 **/
	protected MediaObject source;

	protected String refSeeAlso;

	public MediaEventDataModel() {

	}

	public MediaEventDataModel(MediaEvent mediaEvent) {

		if (mediaEvent == null) {
			throw new IllegalArgumentException("mediaEvent can not be null");
		}

		this.eventType = mediaEvent.getType();
		this.timestamp = new Date(Long.valueOf(mediaEvent.getTimestamp()) * 1000);

		this.source = new MediaObject(mediaEvent.getSource());

	}

	@Override
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public MediaObject getSource() {
		return source;
	}

	public void setSource(MediaObject source) {
		this.source = source;
	}

	public String getRefSeeAlso() {
		return refSeeAlso;
	}

	public void setRefSeeAlso(String refSeeAlso) {
		this.refSeeAlso = refSeeAlso;
	}

}
