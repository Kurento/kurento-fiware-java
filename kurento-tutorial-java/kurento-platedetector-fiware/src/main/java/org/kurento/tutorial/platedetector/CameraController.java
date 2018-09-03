package org.kurento.tutorial.platedetector;

import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kurento.orion.connector.OrionConnectorConfiguration;
import org.kurento.orion.connector.OrionConnectorException;
import org.kurento.tutorial.platedetector.models.Camera;
import org.kurento.tutorial.platedetector.orion.CamPublisher;
import org.kurento.tutorial.platedetector.orion.CamReader;
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
public class CameraController {
	
	private final Logger log = LoggerFactory.getLogger(CameraController.class);
	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:SS");

	/**
	 * [WORKING]
	 * Process the request for a new "source" device to be inserted in orion
	 * @param ua
	 * @param req
	 * @return
	 */
	 @RequestMapping(value={"/camera/new"}, method={RequestMethod.POST})
	 @ResponseBody
	 public String NewCamera(HttpServletRequest req) {
		 String response = "{\"result\": __RESULT__}";
		 
		 try {
			 	//we create the new Camera with the info of the user
			 	
				final OrionConnectorConfiguration orionConnectorConfiguration = new OrionConnectorConfiguration();
				
				CamPublisher cp = new CamPublisher(orionConnectorConfiguration);
				
				Camera cam = new Camera();

				
				//check if Id is set
				if (req.getParameter("id")!=null || "".equals(req.getParameter("id"))) 
					cam.setId(req.getParameter("id"));
				
				else cam.setId("CameraDefault_"+System.currentTimeMillis());
				
				if (req.getParameter("name")!=null || "".equals(req.getParameter("name"))) cam.setName(req.getParameter("id")); 
				
				if (req.getParameter("type")!=null || "".equals(req.getParameter("type"))) cam.setType(req.getParameter("type")); 

				//check if Dates are set. 
				cam.setCreationDate(format.format(System.currentTimeMillis()));
				cam.setUpdateDate(cam.getCreationDate());
				cam.setState("NEW");
				
				// TODO ips will be updated on ICE negotiation
				
				Gson gson = new Gson();
				String jsonInString = gson.toJson(cam);
				log.debug("CAM:{}",jsonInString);
					
				cp.publish(cam);
					
				response= response.replace("__RESULT__","\"OK\","+"\"device\":"+jsonInString);
				
		 } catch (Throwable t) {
			 	response=response.replace("__RESULT__", "error");
				//sendError(session, t.getMessage());
		}
		
	    return response;
	 }  
	 
	 /**
	  * [TODO]
	  * Process the request to "switch on" the device 
	  * 	(updates on orion)
	  * @param id
	  * @param req
	  * @return
	  */
	 @RequestMapping(value={"/device/activate/{DeviceID}"}, method={RequestMethod.POST})
	 @ResponseBody
	 public String activateDevice(@PathVariable(value="someID") String id, HttpServletRequest req) {
		 String response = "{\"result\": __RESULT__}";
		 
		 try {
			
			 final OrionConnectorConfiguration orionConnectorConfiguration = new OrionConnectorConfiguration();
			 CamPublisher cp = new CamPublisher(orionConnectorConfiguration);
			 CamReader cr = new CamReader(orionConnectorConfiguration);
			 
			 Camera cam = cr.readObject(id);
			 cam.setState("PROCESSING");
			 cam.setUpdateDate(format.format(System.currentTimeMillis()));
			 cp.update(cam);
			 Gson gson = new Gson();
			 String jsonInString = gson.toJson(cam);
			 response= response.replace("__RESULT__","\"OK\","+"\"device\":"+jsonInString);
		 } catch (Throwable t) {
			 	response=response.replace("__RESULT__", "error");
				//sendError(session, t.getMessage());
		}
		
	    return response;
	 } 
	 
	 /**
	  * [TODO]
	  * Retrieves the device from orion
	  * @param id
	  * @param req
	  * @return
	  */
	 /*@RequestMapping(value={"/device/{DeviceID}"}, method={RequestMethod.POST})
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

	 }  */
	 
	 
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
