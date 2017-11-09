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

package org.kurento.orion.publisher;

import org.kurento.orion.connector.OrionConnector;
import org.kurento.orion.connector.OrionConnectorConfiguration;
import org.kurento.orion.connector.entities.OrionEntity;

public abstract class DefaultOrionPublisher<T, O extends OrionEntity> implements
		OrionPublisher<T, O> {

	protected OrionConnector orionConnector;

	public DefaultOrionPublisher(OrionConnectorConfiguration config) {
		super();
		this.orionConnector = new OrionConnector(config);
	}

	/**
	 * Creates a new entity in FIWARE. The
	 * {@link #mapEntityToOrionEntity(T entity)} should be implemented to map
	 * the entity to the output desired entity in FIWARE
	 *
	 * @param entity
	 *            a T entity
	 */
	@Override
	public void publish(T entity) {
		OrionEntity orionEntity = mapEntityToOrionEntity(entity);
		orionConnector.createNewEntity(orionEntity, true);
	}

	/**
	 * Creates a new entity in FIWARE.
	 *
	 * @param orionEntity
	 *            a O orionEntity
	 */
	@Override
	public void publish(O orionEntity) {
		orionConnector.createNewEntity(orionEntity, true);
	}

	/**
	 * Given an object, maps to an appropriate output FIWARE object.
	 *
	 * @param entity
	 *            a T entity
	 */
	@Override
	abstract public O mapEntityToOrionEntity(final T entity);

}
