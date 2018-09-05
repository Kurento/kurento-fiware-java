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

import org.kurento.orion.connector.entities.OrionEntity;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

/**
 * Wrapper for Custom JsonDesarializer and JsonSerializer
 * 
 * @author Guiomar Tuñón (guiomar.tunon@gmail.com)
 *
 * @param <T
 *          extends {@link OrionEntity}>
 */
public abstract class JsonManager<T extends OrionEntity> implements JsonDeserializer<T>, JsonSerializer<T> {

}
