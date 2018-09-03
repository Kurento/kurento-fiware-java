package org.kurento.tutorial.platedetector.models;

import java.util.List;

import org.kurento.client.MediaObject;
import org.kurento.client.Tag;
import org.kurento.module.platedetector.PlateDetectedEvent;

public class DevicePlateDetectedEvent extends PlateDetectedEvent {

	private Camera camera;
	
	public DevicePlateDetectedEvent(MediaObject source, String timestamp, List<Tag> tags, String type, String plate) {
		super(source, timestamp, tags, type, plate);
	}
	
	public DevicePlateDetectedEvent(PlateDetectedEvent event, Camera cam) {
		super(event.getSource(),event.getTimestamp(), event.getTags(), event.getType(), event.getPlate());
		this.camera = cam;
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

}
