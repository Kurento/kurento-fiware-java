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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.client.utils.URIBuilder;
import org.kurento.orion.connector.entities.GenericOrionEntity;
import org.kurento.orion.connector.entities.OrionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

/**
 * Connector to the Orion context broker. This connector uses only the NGSIv2
 * service from Orion
 */
public abstract class OrionConnector <T extends OrionEntity> {
	
	private static final String ENTITIES_PATH = "/v2/entities";
	private static final String BATCH_PATH = "/v2/op/update";
	private static final String QUERY_PATH = "/v2/op/query";
	private static final String QUERY_ENTITY_TYPES = "/v2/types?options=values";
	private static final String KEY_VALUES = "options=keyValues";
	private static final String COUNT_HEADER="Fiware-Total-Count";
		
	
	private OrionConnectorConfiguration config;

	private URI orionAddr;
	
	public URI getOrionAddr() {
		return orionAddr;
	}

	private static final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create();
	
	private static final Logger log = LoggerFactory.getLogger(OrionConnector.class);

	
	/**
	 * Default constructor to be used when the orion connector is created from a
	 * spring context.
	 */
	public  OrionConnector() {

	}

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
	@PostConstruct
	private void init() {
		try {
			this.orionAddr = new URIBuilder()
									.setScheme(this.config.getOrionScheme())
									.setHost(this.config.getOrionHost()).setPort(this.config.getOrionPort())
									.build();
		} catch (URISyntaxException e) {
			throw new OrionConnectorException("Could not build URI to make a request to Orion", e);
		}
	}

	
	/**
	 * Receives a list of the current Types that exists in orion
	 *
	 * @return List<String> types
	 */
	public List<String> getEntityTypes() {
		String uri = this.orionAddr.toString() + QUERY_ENTITY_TYPES;
		
		List<String> entities = sendGetRequestStringToOrion(null, uri, HttpStatus.SC_OK);
		
		

		return entities;
		
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
	public void createNewEntity(T orionEntity) {
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
	public void createNewEntity(T orionEntity, boolean keyValuesMode) {
		createEntity(orionEntity, keyValuesMode);
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
	public void createEntity(T orionEntity, boolean keyValuesMode) {

		String uri = this.orionAddr.toString() + ENTITIES_PATH;
		
		if (keyValuesMode) {
			uri += "?options=keyValues";
		}
		
		sendPostRequestVoidToOrion(orionEntity, uri, HttpStatus.SC_CREATED);
	
	}
	
	/**
	 * Counts the number of entities of a certain type in Orion
	 *
	 * @param type
	 *            The entity type that would be used to count
	 * 
	 * @throws OrionConnectorException
	 *             if a communication exception happens, either when contacting
	 *             the context broker at the given address, or obtaining the
	 *             answer from it.
	 */
	public int getEntityCount(String type) {
		log.debug("Find entity: {}", type);
		
		String uri = this.orionAddr.toString() + ENTITIES_PATH + "?type="+ type+"&limit=1&options=count";
		
		return  sendGetRequestCountToOrion(null, uri, HttpStatus.SC_OK, true);

	}
	
	/**
	 * Gets ALL the entities of a certain type (it manages pagination with {@link OrionConnectorConfiguration} 
	 * 		properties. Not recommended for big data, as the result list could be to long. See 
	 * 		{@link readEntityList(String type, int limit, int offset)}
	 *
	 * @param type
	 *            The entity type that would be queried
	 * 
	 * @throws OrionConnectorException
	 *             if a communication exception happens, either when contacting
	 *             the context broker at the given address, or obtaining the
	 *             answer from it.
	 */
	public List<T> readEntityList(String type) {
		
		log.debug("Find entity: {}", type);
				
		List<T> entities = new ArrayList<T>();
		
		int count = getEntityCount(type);
		int offset = 0;
		do {
			
			entities.addAll(readEntityList(type, config.getQueryLimit(), offset));
			offset+=config.getQueryLimit();
			
		}while(offset < count);
		
		return entities;
				
	}	
	
	/**
	 * Gets the entities of a certain type leaving the pagination to the consumer of the data
	 *
	 * @param type
	 *            The entity type that would be queried
	 * 
	 * @throws OrionConnectorException
	 *             if a communication exception happens, either when contacting
	 *             the context broker at the given address, or obtaining the
	 *             answer from it.
	 */
	public List<T> readEntityList(String type, int limit, int offset) {
		
		log.debug("Find entity with offset: {}", type);
		
		String uri = this.orionAddr.toString() + ENTITIES_PATH + "?type="+ type+"&offset="+offset+"&limit="+limit+"&"+KEY_VALUES;
		
		return (List<T>) sendGetRequestGenericEntityToOrion(null, uri, HttpStatus.SC_OK);
				
	}
	
	/**
	 * Update an orion Entity setting KeyValuesMode to true
	 * @param orionEntity
	 * @throws OrionConnectorException
	 *             if a communication exception happens, either when contacting
	 *             the context broker at the given address, or obtaining the
	 *             answer from it.
	 */
	public void updateEntity(T orionEntity) {
		updateEntity(orionEntity, true);
	}
	
	/**
	 * Update an orion Entity 
	 * @param orionEntity
	 * @throws OrionConnectorException
	 *             if a communication exception happens, either when contacting
	 *             the context broker at the given address, or obtaining the
	 *             answer from it.
	 */
	public void updateEntity(T orionEntity, boolean KeyValuesMode) {

		//prepare uri
		String uri = this.orionAddr.toString() + ENTITIES_PATH + "/"+orionEntity.getId()+"/attrs";
		
		if (KeyValuesMode) {
			uri += "?options=keyValues";
		}
		
		//prepare entity - only parameters to change
		orionEntity.setId(null);
		orionEntity.setType(null);
		
		sendPatchRequestVoidToOrion(orionEntity, uri, HttpStatus.SC_NO_CONTENT);

	}
	
	
	
	/**
	 * Gets an entity from Orion by id
	 *
	 * @param id
	 *            The entity id that would be queried
	 * 
	 * @throws OrionConnectorException
	 *             if a communication exception happens, either when contacting
	 *             the context broker at the given address, or obtaining the
	 *             answer from it.
	 */
	public T readEntity(String id) {
		
		log.debug("Find entity: {}", id);
		
		String uri = this.orionAddr.toString() + ENTITIES_PATH + "/"+ id+"?"+KEY_VALUES;
		
		List<T> lstgoe = sendGetRequestGenericEntityToOrion(null, uri, HttpStatus.SC_OK);
		
		return (lstgoe != null && lstgoe.size()==1)? lstgoe.get(0) : null;
		
	}
	
	public void deleteOneEntity(String id) {
		log.debug("Find entity: {}", id);
		
		String uri = this.orionAddr.toString() + ENTITIES_PATH + "/"+ id;
		
		sendDeleteRequestGenericEntityToOrion(uri, HttpStatus.SC_NO_CONTENT);
		
		
	}
	private List<String> sendGetRequestStringToOrion(T ctxElement, String uri, int expected_code){
		
		HttpResponse httpResponse = sendGetRequestToOrion(ctxElement, uri);
		
		httpResponse = checkResponse(httpResponse, expected_code);
		
		return getStringFromResponse(httpResponse);
	}
	
	private List<T> sendGetRequestGenericEntityToOrion(T ctxElement, String uri, int expected_code){
		
		HttpResponse httpResponse = sendGetRequestToOrion(ctxElement, uri);
		
		httpResponse = checkResponse(httpResponse, expected_code);
		
		return getOrionObjFromResponse(httpResponse);
	}

		
	private void  sendPostRequestVoidToOrion (T ctxElement, String uri, int expected_code) {		
		HttpResponse httpResponse = sendPostRequestToOrion(ctxElement, uri);
		
		httpResponse = checkResponse(httpResponse, expected_code);
			
	}
	
	private void  sendPatchRequestVoidToOrion (T ctxElement, String uri, int expected_code) {		
		HttpResponse httpResponse = sendPatchRequestToOrion(ctxElement, uri);
		
		httpResponse = checkResponse(httpResponse, expected_code);
			
	}
	
	private void  sendGetRequestVoidToOrion (T ctxElement, String uri, int expected_code) {
		sendGetRequestCountToOrion (ctxElement, uri, expected_code, false);
	}
	
	
	private int sendGetRequestCountToOrion (T ctxElement, String uri, int expected_code, boolean count) {
		
		HttpResponse httpResponse = sendGetRequestToOrion(ctxElement, uri);
		
		httpResponse = checkResponse(httpResponse, expected_code);
		
		if (count) {
			return getCount(httpResponse);
		}
		else return -1;
	}
	
	private void  sendDeleteRequestGenericEntityToOrion (String uri, int expected_code) {		
		HttpResponse httpResponse = sendDeleteRequestToOrion(uri);
		
		httpResponse = checkResponse(httpResponse, expected_code);
			
	}
	
	/**
	 * Sends a post request to Orion
	 * 
	 * @param ctxElement
	 *            The context element
	 * @param uri
	 *            the path from the context broker that determines which
	 *            "operation"will be executed
	 * @return the httpResponse object from Orion
	 * @throws OrionConnectorException
	 *             if a communication exception happens, either when contacting
	 *             the context broker at the given address, or obtaining the
	 *             answer from it.
	 */
	private HttpResponse sendPostRequestToOrion(T ctxElement, String uri) {
		
		Request req = Request.Post(uri)
				.addHeader("Accept", APPLICATION_JSON.getMimeType())
				.socketTimeout(5000);
		
		if (ctxElement != null ) {
			String jsonEntity = gson.toJson(ctxElement);
			log.debug("Send request to Orion {}: {}",uri, jsonEntity);
			req.bodyString(jsonEntity, APPLICATION_JSON).connectTimeout(5000);

		}
		
		if (this.config.getFiwareService() != null
				&& !"".equalsIgnoreCase(this.config.getFiwareService())) {
			req.addHeader("Fiware-Service", this.config.getFiwareService());
		}
		
		Response response;
		try {
			response = req.execute();
		} catch (IOException e) {
			throw new OrionConnectorException("Could not execute HTTP request",	e);
		}
		return getHttpResponse(response);
	}
	
	/**
	 * Sends a patch request to Orion
	 * 
	 * @param ctxElement
	 *            The context element
	 * @param uri
	 *            the path from the context broker that determines which
	 *            "operation"will be executed
	 * @return the httpResponse object from Orion
	 * @throws OrionConnectorException
	 *             if a communication exception happens, either when contacting
	 *             the context broker at the given address, or obtaining the
	 *             answer from it.
	 */
	private HttpResponse sendPatchRequestToOrion(T ctxElement, String uri) {
		
		Request req = Request.Patch(uri)
				.addHeader("Accept", APPLICATION_JSON.getMimeType())
				.socketTimeout(5000);
		
		if (ctxElement != null ) {
			String jsonEntity = gson.toJson(ctxElement);
			log.debug("Send request to Orion {}: {}",uri, jsonEntity);
			req.bodyString(jsonEntity, APPLICATION_JSON).connectTimeout(5000);

		}
		
		if (this.config.getFiwareService() != null
				&& !"".equalsIgnoreCase(this.config.getFiwareService())) {
			req.addHeader("Fiware-Service", this.config.getFiwareService());
		}
		
		Response response;
		try {
			response = req.execute();
		} catch (IOException e) {
			throw new OrionConnectorException("Could not execute HTTP request",	e);
		}
		return getHttpResponse(response);
	}
	/**
	 * Sends a post request to Orion
	 * 
	 * @param ctxElement
	 *            The context element
	 * @param uri
	 *            the path from the context broker that determines which
	 *            "operation"will be executed
	 * @return the httpResponse object from Orion
	 * @throws OrionConnectorException
	 *             if a communication exception happens, either when contacting
	 *             the context broker at the given address, or obtaining the
	 *             answer from it.
	 */
	private HttpResponse sendGetRequestToOrion(T ctxElement, String uri) {
		

		Request req = Request.Get(uri)
				.addHeader("Accept", APPLICATION_JSON.getMimeType())
				.socketTimeout(5000);
		
		if (ctxElement != null ) {
			String jsonEntity = gson.toJson(ctxElement);
			log.debug("Send request to Orion {}: {}",uri, jsonEntity);
			req.bodyString(jsonEntity, APPLICATION_JSON).connectTimeout(5000);

		}
		
		if (this.config.getFiwareService() != null
				&& !"".equalsIgnoreCase(this.config.getFiwareService())) {
			req.addHeader("Fiware-Service", this.config.getFiwareService());
		}
		
		Response response;
		try {
			response = req.execute();
		} catch (IOException e) {
			throw new OrionConnectorException("Could not execute HTTP request",	e);
		}
		return getHttpResponse(response);
	}

	/**
	 * Sends a post request to Orion
	 * 
	 * @param ctxElement
	 *            The context element
	 * @param uri
	 *            the path from the context broker that determines which
	 *            "operation"will be executed
	 * @return the httpResponse object from Orion
	 * @throws OrionConnectorException
	 *             if a communication exception happens, either when contacting
	 *             the context broker at the given address, or obtaining the
	 *             answer from it.
	 */
	private HttpResponse sendDeleteRequestToOrion(String uri) {
		
		Request req = Request.Delete(uri)
				.socketTimeout(5000);
		
		
		if (this.config.getFiwareService() != null
				&& !"".equalsIgnoreCase(this.config.getFiwareService())) {
			req.addHeader("Fiware-Service", this.config.getFiwareService());
		}
		
		Response response;
		try {
			response = req.execute();
		} catch (IOException e) {
			throw new OrionConnectorException("Could not execute HTTP request",	e);
		}
		return getHttpResponse(response);
	}
	
	/**
	 * retrieves the HttpResponse form the {@Link Response} of orion
	 * @param response
	 * @return HttpResponse
	 * @throws OrionConnectorException
	 *             if a the HttpResponse couldn't be retrieved
	 */
	private HttpResponse getHttpResponse(Response response) {
		HttpResponse httpResponse;
		try {
			httpResponse = response.returnResponse();
		} catch (IOException e) {
			throw new OrionConnectorException("Could not obtain HTTP response",
					e);
		}
		return httpResponse;
	}
	
	/**
	 * Check whether the error code in the response has been the expected one. 
	 * @param httpResponse
	 * @param expected
	 * @return
	 */
	private HttpResponse checkResponse(HttpResponse httpResponse, int expected) {
		
		if (httpResponse.getStatusLine().getStatusCode() != expected) {
			throw new OrionConnectorException("Failed with HTTP error code : "+ httpResponse.getStatusLine().getStatusCode());
		}

		return httpResponse;
	}
	
	/**
	 * Retrieves the response of the Orion Query as a List of Strings
	 * @param httpResponse
	 * @return List<String>
	 */
	private List<String> getStringFromResponse(HttpResponse httpResponse) {
		
		InputStream source;
		List<String> list =null;
		
		try {
			source = httpResponse.getEntity().getContent();
		} catch (IllegalStateException | IOException e) {
			throw new OrionConnectorException(
					"Could not obtain entity content from HTTP response", e);
		}
		String ctxResp = null;
		try (Reader reader = new InputStreamReader(source)) {
			ctxResp = IOUtils.toString(reader);
			list = gson.fromJson(ctxResp, new TypeToken<List<String>>(){}.getType());
					
		}catch (IOException e) {
			log.warn("Could not close input stream from HttpResponse.", e);
		}
		return list;
		
	}

	/**
	 * Retrieves the response of the Orion Query as a list of T 
	 * @param httpResponse
	 * @return List<T>
	 */
	private List<T> getOrionObjFromResponse(HttpResponse httpResponse) {
		InputStream source;
		
		Type sooper = getClass().getGenericSuperclass();
	    Type t = ((ParameterizedType)sooper).getActualTypeArguments()[0 ];
		
	    //Type listType = new TypeToken<List<String>>() {}.getType();
 
		List <T> ctxResp = new ArrayList<T>();
		
		try {
			source = httpResponse.getEntity().getContent();
			
		} catch (IllegalStateException | IOException e) {
			throw new OrionConnectorException(
					"Could not obtain entity content from HTTP response", e);
		}
		
		try (Reader reader = new InputStreamReader(source)) {
			String targetString = IOUtils.toString(reader);
			if (targetString.startsWith("{")) {
				T objResp = null; 
				objResp = gson.fromJson(targetString, t);
				ctxResp.add(objResp); 
			}
			else if (targetString.startsWith("[")) {
				List<LinkedTreeMap> items = gson.fromJson(targetString, List.class);
				for (LinkedTreeMap it : items) {
					T objResp = gson.fromJson(gson.toJson(it), t);
					ctxResp.add(objResp); 
				}
			}
		} catch (IOException e) {
			log.warn("Could not close input stream from HttpResponse.", e);
		}

		return ctxResp;
	}
	
	
	/**
	 * Gets the count of entities form the response header
	 * @param httpResponse
	 * @return int
	 */
	private int getCount(HttpResponse httpResponse) {
		Header h= httpResponse.getFirstHeader(COUNT_HEADER);
		return Integer.parseInt(h.getValue());
	}
	
}
