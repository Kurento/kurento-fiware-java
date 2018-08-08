/*
 * (C) Copyright 2017 Kurento (http://kurento.org/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.kurento.tutorial.platedetector;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.kurento.orion.connector.OrionConnectorConfiguration;
import org.kurento.orion.connector.entities.device.DeviceOrionPublisher;
import org.kurento.tutorial.platedetector.orion.models.UserAgent;
import org.kurento.tutorial.platedetector.orion.models.WebRTCDevice;
import org.kurento.tutorial.platedetector.orion.models.datamodels.DeviceDataModel;

public class WebRTCEndPointOrionPublisher extends
		DeviceOrionPublisher<WebRTCDevice, DeviceDataModel> {

	public WebRTCEndPointOrionPublisher(OrionConnectorConfiguration config) {
		super(config);
	}

	/**
	 * Given a WebRTCDevice object, maps to a MediaEventDataModel FIWARE object.
	 *
	 * @param event
	 *            a T event
	 */
	@Override
	public DeviceDataModel mapEntityToOrionEntity(final WebRTCDevice wrtcd) {
		
		DeviceDataModel device = new DeviceDataModel();
		Date current = new Date(System.currentTimeMillis());
		
		if(wrtcd.getId()==null) {
			device.setId("Device_" + new Date().getTime());
			device.setDateCreated(current);
			device.setDateFirstUsed(current);
		}
		
		if(wrtcd.getUa()!=null) {
			UserAgent ua = wrtcd.getUa();
	
			//retrieve parameters of device:
			device.setName(ua.getBrowser().getName());
		
			try {
				device.setDescription(URLEncoder.encode(ua.getSource(), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}		
			List<String> ipAddresses = new ArrayList<String> ();
			ipAddresses.add(wrtcd.getIp());
			device.setIpAddress(ipAddresses);
			//Can we have location from web browser device.setLocation(location);
			List<String> controlledProperties = new ArrayList<String>();
			controlledProperties.add("trafficFlow");
			device.setControlledProperty(controlledProperties);
			List<String> supportedProtocols = new ArrayList<String>();
			supportedProtocols.add("websocket");
			supportedProtocols.add("webRTC");
			device.setSupportedProtocol(supportedProtocols);
			device.setSoftwareVersion(ua.getBrowser().getVersion());
		}
		
		device.setDateModified(current);
				
		device.setDeviceState(wrtcd.isActive() ? "on":"off");
		
		return device;
	}
}
