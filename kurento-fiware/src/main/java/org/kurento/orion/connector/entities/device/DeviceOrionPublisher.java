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

package org.kurento.orion.connector.entities.device;

import org.kurento.orion.connector.OrionConnectorConfiguration;
import org.kurento.orion.publisher.DefaultOrionPublisher;

/**
 * Extension from {@link DefaultOrionPublisher} specialized for {@link Device}s
 * 
 * @author Guiomar Tuñón (guiomar.tunon@gmail.com)
 *
 * @param <T>
 */
public abstract class DeviceOrionPublisher<T extends Object> extends DefaultOrionPublisher<T, Device> {

  public DeviceOrionPublisher(OrionConnectorConfiguration config) {
	super(config, new DeviceJsonManager(), Device.class);

  }

}
