package org.kurento.tutorial.platedetector.orion;

import java.text.SimpleDateFormat;

import org.kurento.client.MediaObject;
import org.kurento.orion.connector.OrionConnectorConfiguration;
import org.kurento.orion.connector.entities.commons.MediaSource;
import org.kurento.orion.connector.entities.event.MediaEvent;
import org.kurento.orion.connector.entities.event.MediaEventOrionPublisher;
import org.kurento.tutorial.platedetector.models.DevicePlateDetectedEvent;

public class PlateDetectedEventPublisher extends MediaEventOrionPublisher<DevicePlateDetectedEvent> {
	
	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:SS");


	public PlateDetectedEventPublisher(OrionConnectorConfiguration config) {
		super(config);
	}

	@Override
	public MediaEvent mapEntityToOrionEntity(DevicePlateDetectedEvent kurentoEvent) {
		
		MediaEvent orion_entity = new MediaEvent();
		
		orion_entity.setId("PlateDetected_"+System.currentTimeMillis()+"_"+kurentoEvent.getPlate());
		orion_entity._getGsmaCommons().setDateCreated(kurentoEvent.getTimestamp());
		orion_entity.setData(kurentoEvent.getPlate());
		orion_entity.setDeviceSource(kurentoEvent.getCamera().getId());
		orion_entity.setMediasource(mapKurentoMediaSource(kurentoEvent.getSource()));
		
		return orion_entity;
	}
	
	private MediaSource mapKurentoMediaSource(MediaObject mediaObject) {
		if (mediaObject == null) return null;
		
		MediaSource ms = new MediaSource();
		ms.setName(mediaObject.getName());
		ms.setSendTagsInEvents(mediaObject.getSendTagsInEvents());
		ms.setCreationTime(format.format(mediaObject.getCreationTime()));
		if (mediaObject.getParent()!=null) {
			ms.setParent(mapKurentoMediaSource(mediaObject.getParent()));
		}	
		return ms;
	}

}
