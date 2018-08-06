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

package org.kurento.orion.reader;

import java.util.ArrayList;
import java.util.List;

import org.kurento.orion.connector.OrionConnector;
import org.kurento.orion.connector.OrionConnectorConfiguration;
import org.kurento.orion.connector.entities.OrionEntity;

public abstract class DefaultOrionReader<T, O extends OrionEntity> implements
		OrionReader<T, O> {

	protected OrionConnector orionConnector;

	public DefaultOrionReader(OrionConnectorConfiguration config) {
		super();
		this.orionConnector = new OrionConnector<O>(config){};
	}

	/**
	 * Reads a entity in FIWARE as a given Object.
	 *
	 * @param id
	 *            a id as String
	 */
	@Override
	public T readObject(String id) {
		OrionEntity orionEntity = orionConnector.readEntity(id);
		return mapOrionEntityToEntity((O) orionEntity);
	}

	/**
	 * Reads a entity in FIWARE.
	 *
	 * @param id
	 *            a id as String
	 */
	@Override
	public O readOrionEntity(String id) {
		return (O) orionConnector.readEntity(id); 
	} 
	
	/**
	 * Reads a list of entities in FIWARE as a given Object.
	 *
	 * @param type
	 *            a type as String
	 */
	@Override
	public List<T> readObjectList(String type) {
		List<O> orionEntityList = (List<O>) orionConnector.readEntityList(type);
		List <T> result = new ArrayList<T>();
		
		for (O orionEntity : orionEntityList) {
			result.add(mapOrionEntityToEntity(orionEntity));
		}
			return result;
	}

	/**
	 * Reads a entity in FIWARE.
	 *
	 * @param id
	 *            a id as String
	 */
	@Override
	public List<O> readOrionEntityList(String type) {
		return (List<O>) orionConnector.readEntityList(type); 
	} 

	/**
	 * Given an object, maps to an appropriate output FIWARE object.
	 *
	 * @param entity
	 *            a T entity
	 */
	@Override
	abstract public T mapOrionEntityToEntity(final O entity);

}
