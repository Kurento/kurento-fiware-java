package org.kurento.tutorial.platedetector.orion;

import org.kurento.tutorial.platedetector.models.Camera;
import org.kurento.orion.connector.OrionConnectorConfiguration;
import org.kurento.orion.connector.entities.device.Device;
import org.kurento.orion.connector.entities.device.DeviceOrionPublisher;

public class CamPublisher extends DeviceOrionPublisher<Camera>{

	public CamPublisher(OrionConnectorConfiguration config) {
		super(config);
		
	}

	@Override
	public Device mapEntityToOrionEntity(Camera cam) {
		
		String [] supportedProtocol = {"WebRTC"};
		
		Device entity = new Device();
		
		entity.setControlledAsset(cam.getControlledAsset());
		entity.setDateInstalled(cam.getCreationDate());
		entity.setDeviceState(cam.getState());
		entity._getDeviceCommons().setSupportedProtocol(supportedProtocol);
		entity._getGsmaCommons().setId(cam.getId());
		entity._getGsmaCommons().setDateCreated(cam.getCreationDate());
		entity._getGsmaCommons().setDescription("Plate detector camera example");
		entity._getGsmaCommons().setName(cam.getName());
		entity.setIpAddress(cam.getIp());
		return entity;
	}
}
