/*
 * Copyright 2017 Kurento (https://www.kurento.org)
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

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.kurento.orion.connector.OrionConnectorConfiguration;
import org.kurento.orion.reader.DefaultOrionReader;

public abstract class DeviceOrionReader<T extends Object> extends
		DefaultOrionReader<T, Device> {

	public DeviceOrionReader(OrionConnectorConfiguration config) {
		super(config, new DeviceJsonManager(), Device.class);
		Type sooper = getClass().getGenericSuperclass();
		for (Type t : ((ParameterizedType)sooper).getActualTypeArguments()) {
			System.out.println("DeviceOrionReader::Type name:" + t.getTypeName());
		}
	    
	}

}
