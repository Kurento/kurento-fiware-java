package org.kurento.tutorial.platedetector;

import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kurento.orion.connector.OrionConnectorConfiguration;
import org.kurento.orion.connector.OrionConnectorException;
import org.kurento.orion.connector.entities.OrionEntity;
import org.kurento.tutorial.platedetector.orion.models.UserAgent;
import org.kurento.tutorial.platedetector.orion.models.WebRTCDevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

@RestController 
public class DeviceController {
	
	private final Logger log = LoggerFactory.getLogger(DeviceController.class);

	
	 @RequestMapping(value={"/device/new"}, method={RequestMethod.POST})
	 @ResponseBody
	 public String NewDevice(@RequestBody UserAgent ua, HttpServletRequest req) {
		 String response = "{\"result\": __RESULT__}";
		 
		 try {
				WebRTCDevice wrtcd = new WebRTCDevice(ua,req.getRemoteAddr());
				
				final OrionConnectorConfiguration orionConnectorConfiguration = new OrionConnectorConfiguration();
				WebRTCEndPointOrionPublisher endpointPublisher = new WebRTCEndPointOrionPublisher(
						orionConnectorConfiguration);
				
				try {
					wrtcd.setId(endpointPublisher.publish(wrtcd).getId());
					Gson gson = new Gson();
					String jsonInString = gson.toJson(wrtcd);
					response= response.replace("__RESULT__","\"OK\","+"\"device\":"+jsonInString);
				} catch (OrionConnectorException e) {
					log.warn("Could not publish Device in ORION");
				}
		 } catch (Throwable t) {
			 	response=response.replace("__RESULT__", "error");
				//sendError(session, t.getMessage());
		}
		
	    return response;
	 }  
	 
	 @RequestMapping(value={"/device/activate/{DeviceID}"}, method={RequestMethod.POST})
	 @ResponseBody
	 public String activateDevice(@PathVariable(value="someID") String id, HttpServletRequest req) {
		 String response = "{\"result\": __RESULT__}";
		 
		 try {
				WebRTCDevice wrtcd = new WebRTCDevice();
				
				wrtcd.setId(id);
				wrtcd.setActive(true);
			
				final OrionConnectorConfiguration orionConnectorConfiguration = new OrionConnectorConfiguration();
				WebRTCEndPointOrionPublisher endpointPublisher = new WebRTCEndPointOrionPublisher(
						orionConnectorConfiguration);
				
				try {
					endpointPublisher.update(wrtcd);
					Gson gson = new Gson();
					String jsonInString = gson.toJson(wrtcd);
					response= response.replace("__RESULT__","\"OK\","+"\"device\":"+jsonInString);
				} catch (OrionConnectorException e) {
					log.warn("Could not publish Device in ORION");
				}
		 } catch (Throwable t) {
			 	response=response.replace("__RESULT__", "error");
				//sendError(session, t.getMessage());
		}
		
	    return response;

	 }  
	 
	 @RequestMapping(value={"/device/{DeviceID}"}, method={RequestMethod.POST})
	 @ResponseBody
	 public String getDevice(@PathVariable(value="someID") String id, HttpServletRequest req) {
		 String response = "{\"result\": __RESULT__}";
		 
		 try {
				WebRTCDevice wrtcd = new WebRTCDevice();
				
				wrtcd.setId(id);
			
				final OrionConnectorConfiguration orionConnectorConfiguration = new OrionConnectorConfiguration();
				WebRTCEndPointOrionReader endpointReader = new WebRTCEndPointOrionReader(
						orionConnectorConfiguration);
				
				try {
					endpointReader.readOrionEntity(id);
					/*Gson gson = new Gson();
					String jsonInString = gson.toJson(wrtcd);
					response= response.replace("__RESULT__","\"OK\","+"\"device\":"+jsonInString);*/
				} catch (OrionConnectorException e) {
					log.warn("Could not publish Device in ORION");
				}
		 } catch (Throwable t) {
			 	response=response.replace("__RESULT__", "error");
				//sendError(session, t.getMessage());
		}
		
	    return response;

	 }  
	 
	 @RequestMapping(value={"/devices"}, method={RequestMethod.POST})
	 @ResponseBody
	 public String getDevice( HttpServletRequest req) {
		 String response = "{\"result\": __RESULT__}";
		 
		 try {
				
				final OrionConnectorConfiguration orionConnectorConfiguration = new OrionConnectorConfiguration();
				WebRTCEndPointOrionReader endpointReader = new WebRTCEndPointOrionReader(
						orionConnectorConfiguration);
				
				try {
					List<WebRTCDevice> wrtcdList =  endpointReader.readObjectList();
					Gson gson = new Gson();
					String jsonInString = gson.toJson(wrtcdList);
					response= response.replace("__RESULT__","\"OK\","+"\"deviceList\":"+jsonInString);
				} catch (OrionConnectorException e) {
					log.warn("Could not get  Device List");
				}
		 } catch (Throwable t) {
			 	response=response.replace("__RESULT__", "error");
				//sendError(session, t.getMessage());
		}
		
	    return response;

	 }  
	 
	 
	 private void debugRequest(HttpServletRequest req) {
		Enumeration<String> parameterNames = req.getParameterNames();
		log.debug("DEBUG: request parameters");
		while (parameterNames.hasMoreElements()) {

			String paramName = parameterNames.nextElement();
			log.debug(paramName+":");

			String[] paramValues = req.getParameterValues(paramName);
			for (int i = 0; i < paramValues.length; i++) {
				String paramValue = paramValues[i];
				log.debug("\t" + paramValue);
			}

		}

		 
	 }
}
