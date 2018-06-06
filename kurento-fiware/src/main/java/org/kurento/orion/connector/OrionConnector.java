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
import static org.kurento.orion.connector.entities.OrionBatchQueryAction.Action.APPEND;
import static org.kurento.orion.connector.entities.OrionBatchQueryAction.Action.APPEND_STRICT;
import static org.kurento.orion.connector.entities.OrionBatchQueryAction.Action.UPDATE;
import static org.kurento.orion.connector.entities.OrionBatchQueryAction.Action.DELETE;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.client.utils.URIBuilder;
import org.kurento.orion.connector.entities.OrionEntity;
import org.kurento.orion.connector.entities.OrionBatchQueryAction;
import org.kurento.orion.connector.entities.QueryEntityResponse;
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
	private static final String BATCH_PATH = "/v2/op/update";
	private static final int HTTP_CREATED = 201;
	
	private OrionConnectorConfiguration config;

	private URI orionAddr;
	
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

	
	public void createEntity(OrionEntity orionEntity, boolean keyValuesMode) {

		String uri = this.orionAddr.toString() + ENTITIES_PATH;
		
		if (keyValuesMode) {
			uri += "?options=keyValues";
		}
		
		sendPostRequestToOrion(orionEntity, uri, HttpStatus.SC_CREATED ,QueryEntityResponse.class);
	
	}
	
	public void updateEntity(OrionEntity orionEntity, boolean KeyValuesMode) {
//
		/* OrionEntity updateEntity = orionEntity;
		updateEntity.setId(null);
		updateEntity.setType(null);
		
		String jsonEntity = gson.toJson(updateEntity);
		log.debug("Send request to Orion: {}", jsonEntity);
		
		String uri = this.orionAddr.toString() + ENTITIES_PATH+"/"+orionEntity.getId()+"?type="+orionEntity.getType();
		if (KeyValuesMode) {
			uri += "&options=keyValues";
		}
		Request req = Request.Patch(uri)
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

		log.debug("Sent to Orion. Obtained response: {}", httpResponse);*/

	}
	
	
	
	
	public <T extends OrionEntity> OrionEntity readEntity(String id) {
		/*
		log.debug("Find entity: {}", id);
		
		String uri = this.orionAddr.toString() + ENTITIES_PATH + "/"+ id;
		OrionEntity oe = null;
		
		Request req = Request.Get(uri)
				.connectTimeout(5000)
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

		HttpEntity entity = httpResponse.getEntity();

        if (entity != null) {

            // A Simple JSON Response Read
            try {
            	 InputStream instream = entity.getContent();
            	 String result = convertStreamToString(instream);
            	 oe = gson.fromJson(result, responseClazz);      
                 // now you have the string representation of the HTML request
            	 instream.close();
      
            	 log.debug("readEntityList::Response-Json: "+result);
			} catch (UnsupportedOperationException | IOException e) {
				throw new OrionConnectorException("Could not parse response", e);
			}
        }
        
		log.debug("Sent to Orion. Obtained response: {}", httpResponse);
		*/
		return null;
		
	}
	
	public <T extends OrionEntity> List<OrionEntity> readEntityList(String type) {
		/*
		log.debug("Find entity: {}", type);
		List<OrionEntity> entityList = null;
		
		String uri = this.orionAddr.toString() + ENTITIES_PATH + "?type="+ type;
		
		Request req = Request.Get(uri)
				.connectTimeout(5000)
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

		HttpEntity entity = httpResponse.getEntity();

        if (entity != null) {

            // A Simple JSON Response Read
            try {
            	 InputStream instream = entity.getContent();
            	 String result = convertStreamToString(instream);
            	 OrionEntity[] mcArray = gson.fromJson(result, responseClazz);
            	 entityList = Arrays.asList(mcArray);
                 // now you have the string representation of the HTML request
            	 instream.close();
      
            	 log.debug("readEntityList::Response-Json: "+result);
			} catch (UnsupportedOperationException | IOException e) {
				throw new OrionConnectorException("Could not parse response", e);
			}
        }
        
		log.debug("Sent to Orion. Obtained response: {}", httpResponse);
		*/
		return null;
		
	}	
	
	private static String convertStreamToString(InputStream is) {

	    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	    StringBuilder sb = new StringBuilder();

	    String line = null;
	    try {
	        while ((line = reader.readLine()) != null) {
	            sb.append(line + "\n");
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            is.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    return sb.toString();
	}
	
	
	/* BATCH Methods */
	
	public QueryEntityResponse createBatchEntity(OrionEntity... orionEntity) {

		String uri = this.orionAddr.toString() + BATCH_PATH;
		
		OrionBatchQueryAction oqa = new OrionBatchQueryAction(APPEND, orionEntity);
		
		return sendPostRequestToOrion(oqa, uri, HttpStatus.SC_OK,QueryEntityResponse.class);
	
	}
	
	/**
	 * Sends a request to Orion
	 * 
	 * @param ctxElement
	 *            The context element
	 * @param path
	 *            the path from the context broker that determines which
	 *            "operation"will be executed
	 * @param responseClazz
	 *            The class expected for the response
	 * @return The object representing the JSON answer from Orion
	 * @throws OrionConnectorException
	 *             if a communication exception happens, either when contacting
	 *             the context broker at the given address, or obtaining the
	 *             answer from it.
	 */
	private <E, T>  T sendPostRequestToOrion(E ctxElement, String uri, int expected_code,  Class<T> responseClazz) {
		
		String jsonEntity = gson.toJson(ctxElement);
		log.debug("Send request to Orion {}: {}",uri, jsonEntity);

		Request req = Request.Post(uri)
				.addHeader("Accept", APPLICATION_JSON.getMimeType())
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
			throw new OrionConnectorException("Could not execute HTTP request",	e);
		}

		HttpResponse httpResponse = checkResponse(response, expected_code);

		T ctxResp = getOrionObjFromResponse(httpResponse, responseClazz);

		log.debug("Send to Orion response: {}", httpResponse);

		return ctxResp;
	}

	private <T>  HttpResponse checkResponse(Response response, int expected) {
		HttpResponse httpResponse;
		try {
			httpResponse = response.returnResponse();
		} catch (IOException e) {
			throw new OrionConnectorException("Could not obtain HTTP response",
					e);
		}
		if (httpResponse.getStatusLine().getStatusCode() != expected) {
			throw new OrionConnectorException("Failed with HTTP error code : "+ httpResponse.getStatusLine().getStatusCode());
		}

		return httpResponse;
	}

	private <T>  T getOrionObjFromResponse(HttpResponse httpResponse, Class<T> responseClazz) {
		InputStream source;
		try {
			source = httpResponse.getEntity().getContent();
		} catch (IllegalStateException | IOException e) {
			throw new OrionConnectorException(
					"Could not obtain entity content from HTTP response", e);
		}
		T ctxResp = null;
		try (Reader reader = new InputStreamReader(source)) {
			ctxResp = gson.fromJson(reader, responseClazz);
		} catch (IOException e) {
			log.warn("Could not close input stream from HttpResponse.", e);
		}

		return ctxResp;
	}

}
