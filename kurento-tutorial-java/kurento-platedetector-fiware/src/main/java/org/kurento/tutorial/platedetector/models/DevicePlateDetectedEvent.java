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

import java.util.List;

import org.kurento.client.MediaObject;
import org.kurento.client.Tag;
import org.kurento.module.platedetector.PlateDetectedEvent;

/**
 * Extension of the {@link PlateDetectedEvent} to associate the custom
 * {@link Camera} that generated it
 * 
 * @author Guiomar Tuñón (guiomar.tunon@gmail.com)
 *
 */
public class DevicePlateDetectedEvent extends PlateDetectedEvent {

  private Camera camera;

  public DevicePlateDetectedEvent(MediaObject source, String timestamp, List<Tag> tags, String type, String plate) {
	super(source, timestamp, tags, type, plate);
  }

  public DevicePlateDetectedEvent(PlateDetectedEvent event, Camera cam) {
	super(event.getSource(), event.getTimestamp(), event.getTags(), event.getType(), event.getPlate());
	this.camera = cam;
  }

  public Camera getCamera() {
	return camera;
  }

  public void setCamera(Camera camera) {
	this.camera = camera;
  }

}
