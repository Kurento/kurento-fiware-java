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
 * Phisical Object Commons from
 * https://fiware.github.io/dataModels/common-schema.json#/definitions/PhysicalObject-Commons
 * 
 * @author Guiomar Tuñón (guiomar.tunon@gmail.com)
 *
 */
public class PhysicalObjectCommons implements Serializable {

  public String getColor() {
	return color;
  }

  public void setColor(String color) {
	this.color = color;
  }

  public String getImage() {
	return image;
  }

  public void setImage(String image) {
	this.image = image;
  }

  public String[] getAnnotations() {
	return annotations;
  }

  public void setAnnotations(String[] annotations) {
	this.annotations = annotations;
  }

  private static final long serialVersionUID = 1446195569593430516L;

  String color;
  String image;
  String[] annotations;
}
