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

import static org.apache.http.entity.ContentType.APPLICATION_JSON;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.client.utils.URIBuilder;
import org.kurento.orion.connector.entities.OrionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Connector to the Orion context broker. This connector uses only the NGSIv2
 * service from Orion
 */
public class OrionConnector {

	private static final String ENTITIES_PATH = "/v2/entities";

	private static final Gson gson = new GsonBuilder().setDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'").create();
	private static final Logger log = LoggerFactory.getLogger(OrionConnector.class);

	private OrionConnectorConfiguration config;

	private URI orionAddr;

	/**
	 * Orion connector constructor.
	 *
	 * @param config
	 *            Configuration object
	 */
	public OrionConnector(OrionConnectorConfiguration config) {
		this.config = config;
		this.init();
	}

	/**
	 * Initiates the {@link #orionAddr}. This step is performed to validate the
	 * fields from the configuration object.
	 */
	private void init() {
		try {
			this.orionAddr = new URIBuilder().setScheme(this.config.getOrionScheme())
					.setHost(this.config.getOrionHost()).setPort(this.config.getOrionPort())
					.build();
		} catch (URISyntaxException e) {
			throw new OrionConnectorException(
					"Could not build URI to make a request to Orion", e);
		}
	}

	/**
	 * Creates an entity in Orion
	 *
	 * @param orionEntity
	 *            The entity that should be created in Orion
	 * 
	 * @throws OrionConnectorException
	 *             if a communication exception happens, either when contacting
	 *             the context broker at the given address, or obtaining the
	 *             answer from it.
	 */
	public void createNewEntity(OrionEntity orionEntity) {
		createEntity(orionEntity, false);
	}

	/**
	 * Creates an entity in Orion specifying whether use or not the keyValues
	 * mode
	 *
	 * @param orionEntity
	 *            The entity that should be created in Orion
	 * 
	 * @param keyValuesMode
	 *            use or not the keyValues mode
	 * 
	 * @throws OrionConnectorException
	 *             if a communication exception happens, either when contacting
	 *             the context broker at the given address, or obtaining the
	 *             answer from it.
	 */
	public void createNewEntity(OrionEntity orionEntity, boolean keyValuesMode) {
		createEntity(orionEntity, keyValuesMode);
	}

	private void createEntity(OrionEntity orionEntity, boolean KeyValuesMode) {

		String jsonEntity = gson.toJson(orionEntity);
		log.debug("Send request to Orion: {}", jsonEntity);

		String uri = this.orionAddr.toString() + ENTITIES_PATH;
		if (KeyValuesMode) {
			uri += "?options=keyValues";
		}
		Request req = Request.Post(uri)
				.addHeader("Content-Type", APPLICATION_JSON.getMimeType())
				.bodyString(jsonEntity, APPLICATION_JSON).connectTimeout(5000)
				.socketTimeout(5000);

		if (this.config.getFiwareService() != null
				&& !"".equalsIgnoreCase(this.config.getFiwareService())) {
			req.addHeader("Fiware-Service", this.config.getFiwareService());
		}
		Response response;
		try {
			response = req.execute();
		} catch (IOException e) {
			throw new OrionConnectorException("Could not execute HTTP request", e);
		}
		HttpResponse httpResponse = checkResponse(response);

		log.debug("Sent to Orion. Obtained response: {}", httpResponse);

	}

	private <T> HttpResponse checkResponse(Response response) {
		HttpResponse httpResponse;
		try {
			httpResponse = response.returnResponse();
		} catch (IOException e) {
			throw new OrionConnectorException("Could not obtain HTTP response", e);
		}
		if (httpResponse.getStatusLine().getStatusCode() / 100 != 2) {
			throw new OrionConnectorException("Failed with HTTP error code : "
					+ httpResponse.getStatusLine().getStatusCode());
		}
		return httpResponse;
	}
}
