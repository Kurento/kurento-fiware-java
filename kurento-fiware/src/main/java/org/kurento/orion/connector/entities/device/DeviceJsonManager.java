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

import static org.slf4j.LoggerFactory.getLogger;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Map;

import org.kurento.orion.connector.entities.commons.DeviceCommons;
import org.kurento.orion.connector.entities.commons.GSMACommons;
import org.kurento.orion.connector.entities.commons.JsonManager;
import org.kurento.orion.connector.entities.commons.LocationCommons;
import org.kurento.orion.connector.entities.commons.PhysicalObjectCommons;
import org.slf4j.Logger;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;

/**
 * JsonSerializer and JsonUnserializer for Orion Entity {@link Device}
 * 
 * @author Guiomar Tuñón (guiomar.tunon@gmail.com)
 *
 */
public class DeviceJsonManager extends JsonManager<Device> {

  private static final Logger log = getLogger(DeviceJsonManager.class);

  @Override
  public JsonElement serialize(Device device, Type arg1, JsonSerializationContext context) {

	final JsonObject jsonObject = new JsonObject();
	// Device elements

	for (Field f : Device.class.getDeclaredFields()) {
	  if (!f.getName().equals("serialVersionUID")) {
		try {
		  f.setAccessible(true);
		  if (f.getType() == String.class) {
			Object obj = null;
			obj = f.get(device);
			if (obj != null)
			  jsonObject.add(f.getName(), context.serialize(obj));

		  } else if (f.getType() == String[].class) {
			Object obj = null;
			obj = f.get(device);
			if (obj != null)
			  jsonObject.add(f.getName(), context.serialize(obj));
		  } else if (f.getType() == Float.class) {
			Object obj = null;
			obj = f.get(device);
			if (obj != null)
			  jsonObject.add(f.getName(), context.serialize(obj));
		  } else {
			Object so = f.get(device);

			for (Field sf : f.getType().getDeclaredFields()) {
			  if (!sf.getName().equals("serialVersionUID")) {
				sf.setAccessible(true);
				Object obj = null;
				obj = sf.get(so);
				if (obj != null)
				  jsonObject.add(sf.getName(), context.serialize(obj));
			  }
			}
		  }

		} catch (IllegalAccessException iae) {
		  log.debug("do something");
		}
	  }
	}

	return jsonObject;
  }

  @Override
  public Device deserialize(JsonElement json, Type arg1, JsonDeserializationContext context) throws JsonParseException {
	// Device to return
	Device device = new Device();

	// JsonObject
	final JsonObject jsonObject = json.getAsJsonObject();

	// for each element of the Json
	for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
	  String key = entry.getKey();
	  Object selected = null;
	  Class<?> clazz;
	  try {
		if (isFieldInClass(key, Device.class)) {
		  selected = device;
		  clazz = Device.class.getDeclaredField(key).getType();
		} else if (isFieldInClass(key, GSMACommons.class)) {
		  selected = device._gsmaCommons;
		  clazz = GSMACommons.class.getDeclaredField(key).getType();
		} else if (isFieldInClass(key, LocationCommons.class)) {
		  clazz = LocationCommons.class.getDeclaredField(key).getType();
		  selected = device._locationCommons;
		} else if (isFieldInClass(key, PhysicalObjectCommons.class)) {
		  clazz = PhysicalObjectCommons.class.getDeclaredField(key).getType();
		  selected = device._pysicalCommons;
		} else if (isFieldInClass(key, DeviceCommons.class)) {
		  clazz = DeviceCommons.class.getDeclaredField(key).getType();
		  selected = device._deviceCommons;
		} else {
		  throw new JsonParseException("Field not found: " + key);
		}
	  } catch (NoSuchFieldException e) {
		throw new JsonParseException("NoSuchFieldException in any class: " + key);
	  }
	  try {
		set(selected, key, context.deserialize(entry.getValue(), clazz));
	  } catch (IllegalStateException ise) {
		throw new JsonParseException("IllegalStateException in set: " + key + " value: " + entry.getValue());
	  }
	}

	return device;
  }

  private static boolean set(Object object, String fieldName, Object fieldValue) {
	Class<?> clazz = object.getClass();
	while (clazz != null) {
	  try {
		Field field = clazz.getDeclaredField(fieldName);
		field.setAccessible(true);
		field.set(object, fieldValue);
		return true;
	  } catch (NoSuchFieldException e) {
		clazz = clazz.getSuperclass();
	  } catch (Exception e) {
		throw new IllegalStateException(e);
	  }
	}
	return false;
  }

  private boolean isFieldInClass(String field, Class<?> clazz) {
	try {
	  clazz.getDeclaredField(field);
	  return true;
	} catch (NoSuchFieldException e) {
	  return false;
	}
  }

}
