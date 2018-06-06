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
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.kurento.orion.connector.OrionConnectorConfiguration;
import org.kurento.orion.reader.DeviceOrionReader;
import org.kurento.tutorial.platedetector.orion.models.WebRTCDevice;
import org.kurento.tutorial.platedetector.orion.models.datamodels.DeviceDataModel;

public class WebRTCEndPointOrionReader extends
		DeviceOrionReader<WebRTCDevice, DeviceDataModel> {

	public WebRTCEndPointOrionReader(OrionConnectorConfiguration config) {
		super(config);
	}
	

	/**
	 * Reads a list of Devices in FIWARE as a given Object.
	 *
	 */
	public List<WebRTCDevice> readObjectList() {
		return super.readObjectList("Device");
	}


	

	/**
	 * Given a WebRTCDevice object, maps to a MediaEventDataModel FIWARE object.
	 *
	 * @param event
	 *            a T event
	 */
	@Override
	public WebRTCDevice mapOrionEntityToEntity(final DeviceDataModel ddm) {
		
		WebRTCDevice wrtcd = new WebRTCDevice();

		wrtcd.setId(ddm.getId());
		wrtcd.setIp(ddm.getIpAddress().get(0));
		wrtcd.setActive(ddm.getDeviceState().equals("on")? true: false);
		wrtcd.setName(ddm.getName());
		wrtcd.getUa().getBrowser().setVersion(ddm.getSoftwareVersion());
		
		try {
			wrtcd.getUa().setSource(URLDecoder.decode(ddm.getDescription(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*Probably more info should be gathered*/
		
		return wrtcd;
	}
}
