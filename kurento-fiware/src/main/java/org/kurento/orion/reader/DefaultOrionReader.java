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

package org.kurento.orion.reader;

import java.util.ArrayList;
import java.util.List;

import org.kurento.orion.connector.OrionConnector;
import org.kurento.orion.connector.OrionConnectorConfiguration;
import org.kurento.orion.connector.entities.OrionEntity;
import org.kurento.orion.connector.entities.commons.JsonManager;

/**
 * Generic Class for Reading Orion Entities.
 * 
 * @author Guiomar Tuñón (guiomar.tunon@gmail.com)
 *
 * @param <T>
 * @param <O
 *          extends {@link OrionEntity}>
 */
public abstract class DefaultOrionReader<T, O extends OrionEntity> implements OrionReader<T, O> {

  protected OrionConnector<O> orionConnector;

  public DefaultOrionReader(OrionConnectorConfiguration config, Class<O> clazz) {
	super();
	this.orionConnector = new OrionConnector<O>(config, clazz) {
	};
  }

  public DefaultOrionReader(OrionConnectorConfiguration config, JsonManager<O> manager, Class<O> clazz) {
	super();
	this.orionConnector = new OrionConnector<O>(config, manager, clazz) {
	};
  }

  /**
   * Reads a entity in FIWARE as a given Object.
   *
   * @param id
   *          a id as String
   */
  @Override
  public T readObject(String id) {
	O orionEntity = orionConnector.readEntity(id);
	return mapOrionEntityToEntity(orionEntity);
  }

  /**
   * Reads a entity in FIWARE.
   *
   * @param id
   *          a id as String
   */
  @Override
  public O readOrionEntity(String id) {
	return (O) orionConnector.readEntity(id);
  }

  /**
   * Reads a list of entities in FIWARE as a given Object.
   *
   * @param type
   *          a type as String
   */
  @Override
  public List<T> readObjectList(String type) {
	List<O> orionEntityList = orionConnector.readEntityList(type);
	List<T> result = new ArrayList<T>();

	for (O orionEntity : orionEntityList) {
	  result.add(mapOrionEntityToEntity(orionEntity));
	}
	return result;
  }

  /**
   * Reads a entity in FIWARE.
   *
   * @param id
   *          a id as String
   */
  @Override
  public List<O> readOrionEntityList(String type) {
	return orionConnector.readEntityList(type);
  }

  /**
   * Given an object, maps to an appropriate output FIWARE object.
   *
   * @param entity
   *          a T entity
   */
  @Override
  abstract public T mapOrionEntityToEntity(final O entity);

}
