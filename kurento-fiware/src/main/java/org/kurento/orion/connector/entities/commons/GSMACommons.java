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

import java.io.Serializable;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
/**
 * GSMA Commons from https://fiware.github.io/dataModels/common-schema.json#/definitions/GSMA-Commons
 * 
 * @author Guiomar Tuñón (guiomar.tunon@gmail.com)
 *
 */
public class GSMACommons implements Serializable {

  public String getId() {
	return id;
  }

  public void setId(String id) {
	this.id = id;
  }

  public String getDateCreated() {
	return dateCreated;
  }

  public void setDateCreated(String dateCreated) {
	this.dateCreated = dateCreated;
  }

  public String getDateModified() {
	return dateModified;
  }

  public void setDateModified(String dateModified) {
	this.dateModified = dateModified;
  }

  public String getSource() {
	return source;
  }

  public void setSource(String source) {
	this.source = source;
  }

  public String getName() {
	return name;
  }

  public void setName(String name) {
	this.name = name;
  }

  public String getAlternateName() {
	return alternateName;
  }

  public void setAlternateName(String alternateName) {
	this.alternateName = alternateName;
  }

  public String getDescription() {
	return description;
  }

  public void setDescription(String description) {
	this.description = description;
  }

  public String getDataProvider() {
	return dataProvider;
  }

  public void setDataProvider(String dataProvider) {
	this.dataProvider = dataProvider;
  }

  public String[] getOwner() {
	return owner;
  }

  public void setOwner(String[] owner) {
	this.owner = owner;
  }

  private static final long serialVersionUID = -4750458972222552462L;

  @Size(min = 1, max = 256)
  @Pattern(regexp = "^[\\w\\-\\.\\{\\}\\$\\+\\*\\[\\]`|~^@!,:\\\\]+$")
  public String id;
  public String dateCreated;
  public String dateModified;
  public String source;
  public String name;
  public String alternateName;
  public String description;
  public String dataProvider;
  public String[] owner;
}
