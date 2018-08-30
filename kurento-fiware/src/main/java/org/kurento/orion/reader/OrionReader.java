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

import java.util.List;

import org.kurento.orion.connector.entities.OrionEntity;

public interface OrionReader<T, O extends OrionEntity> {

	public T readObject(String str);

	public O readOrionEntity(String str);

	public T mapOrionEntityToEntity(O entity);

	public List<T> readObjectList(String id);

	public List<O> readOrionEntityList(String id);
	
}
