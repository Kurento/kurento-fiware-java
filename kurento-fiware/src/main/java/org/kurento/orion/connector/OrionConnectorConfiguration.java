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

package org.kurento.orion.connector;

public class OrionConnectorConfiguration {

	private String orionHost = "localhost";
	private int orionPort = 1026;
	private String orionScheme = "http";
	private String fiwareService = "";

	public String getOrionHost() {
		return this.orionHost;
	}

	public void setOrionHost(String orionHost) {
		this.orionHost = orionHost;
	}

	public int getOrionPort() {
		return this.orionPort;
	}

	public void setOrionPort(int orionPort) {
		this.orionPort = orionPort;
	}

	public String getOrionScheme() {
		return this.orionScheme;
	}

	public void setOrionScheme(String orionSchema) {
		this.orionScheme = orionSchema;
	}

	public String getFiwareService() {
		return fiwareService;
	}

	public void setFiwareService(String fiwareService) {
		this.fiwareService = fiwareService;
	}

}
