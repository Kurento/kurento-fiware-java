package org.kurento.tutorial.platedetector.orion;

import org.kurento.orion.connector.OrionConnectorConfiguration;
import org.kurento.orion.connector.entities.device.Device;
import org.kurento.orion.connector.entities.device.DeviceOrionReader;
import org.kurento.tutorial.platedetector.models.Camera;

public class CamReader extends DeviceOrionReader<Camera> {

	public CamReader(OrionConnectorConfiguration config) {
		super(config);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Camera mapOrionEntityToEntity(Device entity) {
		Camera cam = new Camera();
		cam.setControlledAsset(entity.getControlledAsset());
		cam.setCreationDate(entity.getDateInstalled());
		cam.setId(entity.getId());
		cam.setName(entity._getGsmaCommons().getName());
		cam.setIp(entity.getIpAddress());
		cam.setState(entity.getDeviceState());
		cam.setUpdateDate(entity._getGsmaCommons().getDateModified());
		return cam;
	}

}
