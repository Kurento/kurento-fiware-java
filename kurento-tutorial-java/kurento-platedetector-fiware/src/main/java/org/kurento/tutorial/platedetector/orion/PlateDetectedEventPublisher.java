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

package org.kurento.tutorial.platedetector.orion;

import java.text.SimpleDateFormat;

import org.kurento.client.MediaObject;
import org.kurento.orion.connector.OrionConnectorConfiguration;
import org.kurento.orion.connector.entities.commons.MediaSource;
import org.kurento.orion.connector.entities.device.DeviceOrionPublisher;
import org.kurento.orion.connector.entities.event.MediaEvent;
import org.kurento.orion.connector.entities.event.MediaEventOrionPublisher;
import org.kurento.tutorial.platedetector.models.DevicePlateDetectedEvent;

/**
 * Extension of the {@link DeviceOrionPublisher} for accepting a custom
 * {@link DevicePlateDetectedEvent}
 * 
 * @author Guiomar Tuñón (guiomar.tunon@gmail.com)
 *
 */
public class PlateDetectedEventPublisher extends MediaEventOrionPublisher<DevicePlateDetectedEvent> {

  private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:SS");

  public PlateDetectedEventPublisher(OrionConnectorConfiguration config) {
	super(config);
  }

  /**
   * Uses the {@link DevicePlateDetectedEvent} data to initialize a
   * {@link MediaEvent}
   * 
   * @param Camera
   *          the {@link DevicePlateDetectedEvent} that should be mapped to a
   *          {@link MediaEvent}
   * @return Device the initialized {@link MediaEvent}
   */
  @Override
  public MediaEvent mapEntityToOrionEntity(DevicePlateDetectedEvent kurentoEvent) {

	MediaEvent orion_entity = new MediaEvent();

	orion_entity.setId("PlateDetected_" + System.currentTimeMillis() + "_" + kurentoEvent.getPlate());
	orion_entity._getGsmaCommons().setDateCreated(kurentoEvent.getTimestamp());
	orion_entity.setData(kurentoEvent.getPlate());
	if (kurentoEvent.getCamera() != null) {
	  orion_entity.setDeviceSource(kurentoEvent.getCamera().getId());
	}
	orion_entity.setMediasource(mapKurentoMediaSource(kurentoEvent.getSource()));

	return orion_entity;
  }

  private MediaSource mapKurentoMediaSource(MediaObject mediaObject) {
	if (mediaObject == null)
	  return null;

	MediaSource ms = new MediaSource();
	ms.setName(mediaObject.getName());
	ms.setSendTagsInEvents(mediaObject.getSendTagsInEvents());
	ms.setCreationTime(format.format(mediaObject.getCreationTime()));
	if (mediaObject.getParent() != null) {
	  ms.setParent(mapKurentoMediaSource(mediaObject.getParent()));
	}
	return ms;
  }

}
