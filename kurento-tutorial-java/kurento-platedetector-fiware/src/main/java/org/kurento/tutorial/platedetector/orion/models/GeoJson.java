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

public class GeoJson {

	private static final String GEO_JSON_TYPE = "geo:json";

	private String type = GEO_JSON_TYPE;

	// DISCLAIMER: We hardcode Point for demo purposes
	private Point value;

	public String getType() {
		return type;
	}

	private void setType(String type) {
		this.type = type;
	}

	public Point getValue() {
		return value;
	}

	public void setValue(Point value) {
		this.value = value;
	}

}
