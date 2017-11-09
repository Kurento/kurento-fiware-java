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

package org.kurento.tutorial.platedetector;

import java.util.Date;

import org.kurento.client.MediaEvent;
import org.kurento.orion.connector.OrionConnectorConfiguration;
import org.kurento.orion.publisher.EventOrionPublisher;
import org.kurento.tutorial.platedetector.orion.models.datamodels.MediaEventDataModel;

public class MediaEventOrionPublisher extends
		EventOrionPublisher<MediaEvent, MediaEventDataModel> {

	public MediaEventOrionPublisher(OrionConnectorConfiguration config) {
		super(config);
	}

	/**
	 * Given a MediaEvent object, maps to a MediaEventDataModel FIWARE object.
	 *
	 * @param event
	 *            a T event
	 */
	@Override
	public MediaEventDataModel mapEntityToOrionEntity(final MediaEvent event) {

		MediaEventDataModel mediaEventDataModel = new MediaEventDataModel(event);

		mediaEventDataModel.setId("mediaEvent:" + new Date().getTime());
		return mediaEventDataModel;
	}
}
