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

import org.kurento.orion.connector.OrionConnectorConfiguration;
import org.kurento.orion.connector.entities.device.Device;
import org.kurento.orion.connector.entities.device.DeviceOrionReader;
import org.kurento.tutorial.platedetector.models.Camera;

/**
 * Extension of the {@link DeviceOrionReader} for reading a custom
 * {@link Camera} from Orion
 * 
 * @author Guiomar Tuñón (guiomar.tunon@gmail.com)
 *
 */
public class CamReader extends DeviceOrionReader<Camera> {

  public CamReader(OrionConnectorConfiguration config) {
	super(config);
	// TODO Auto-generated constructor stub
  }

  /**
   * Initalises a {@link Camera} data from a {@link Device}
   * 
   * @param Device
   *          the {@link Device} that should be mapped to a {@link Camera}
   * @return Camera the initialized {@link Camera}
   */
  @Override
  public Camera mapOrionEntityToEntity(Device entity) {
	Camera cam = new Camera();
	cam.setControlledAsset(entity.getControlledAsset());
	cam.setCreationDate(entity.getDateInstalled());
	cam.setId(entity.getId());
	cam.setName(entity._getGsmaCommons().getName());
	cam.setIp(entity.getIpAddress());
	cam.setState(entity.getDeviceState());
	cam.setUpdateDate(entity._getGsmaCommons().getDateModified());
	return cam;
  }

}
