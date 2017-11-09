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

import org.kurento.module.platedetector.PlateDetectedEvent;

public class PlateDetectedEventDataModel extends MediaEventDataModel {

	private static final String PLATE_DETECTED_EVENT_TYPE = "PlateDetectedEvent";

	/**
	 *
	 * Plate identification that was detected by the filter
	 *
	 **/
	private String plate;

	public PlateDetectedEventDataModel() {
		// TODO Auto-generated constructor stub
	}

	public PlateDetectedEventDataModel(PlateDetectedEvent plateDetectedEvent) {
		super(plateDetectedEvent);
		this.type = PLATE_DETECTED_EVENT_TYPE;
		this.plate = plateDetectedEvent.getPlate();
	}

	public String getPlate() {
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

}
