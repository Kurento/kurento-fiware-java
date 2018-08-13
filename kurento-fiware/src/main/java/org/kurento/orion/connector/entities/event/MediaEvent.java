package org.kurento.orion.connector.entities.event;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.kurento.orion.connector.entities.OrionEntity;
import org.kurento.orion.connector.entities.commons.GSMACommons;
import org.kurento.orion.connector.entities.commons.MediaSource;
import org.kurento.orion.connector.entities.device.Device;

public class MediaEvent implements OrionEntity,Serializable{

	private static final long serialVersionUID = -1602741890197466709L;
	
	GSMACommons _gsmaCommons;

	String type = "MediaEvent";
	String eventType;
	
	MediaSource mediasource;
	Object data;
	String deviceSource;
	String[] refersTo;
	 

	public MediaEvent() {
		super();
		this._gsmaCommons = new GSMACommons();
	}
	
	public MediaEvent(String id, String eventType, MediaSource mediasource,
			Object data, String deviceSource, String[] refersTo) {
		super();
		this._gsmaCommons = new GSMACommons();
		this._gsmaCommons.id = id;
		this.eventType = eventType;
		this.mediasource = mediasource;
		this.data = data;
		this.deviceSource = deviceSource;
		this.refersTo = refersTo;
	}
	
	public MediaEvent(GSMACommons _gsmaCommons, String eventType, MediaSource mediasource,
			Object data, String deviceSource, String[] refersTo) {
		super();
		this._gsmaCommons = _gsmaCommons;
		this.eventType = eventType;
		this.mediasource = mediasource;
		this.data = data;
		this.deviceSource = deviceSource;
		this.refersTo = refersTo;
	}

	@Override
	public String getId() {
		return  _gsmaCommons.id;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public void setId(String id) {
		_gsmaCommons.id = id;
	}

	@Override
	public void setType(String type) {
		this.type=type;	
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public MediaSource getMediasource() {
		return mediasource;
	}

	public void setMediasource(MediaSource mediasource) {
		this.mediasource = mediasource;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getDeviceSource() {
		return deviceSource;
	}

	public void setDeviceSource(String deviceSource) {
		this.deviceSource = deviceSource;
	}
	
	public void setDeviceSource(Device deviceSource) {
		this.deviceSource = deviceSource.getId();
	}

	public String[] getRefersTo() {
		return refersTo;
	}

	public void setRefersTo(String[] refersTo) {
		this.refersTo = refersTo;
	}
	
	public void setRefersTo(OrionEntity[] oes) {
		List<String> refersTo = new ArrayList<String>();
		for (OrionEntity oe :oes) {
			refersTo.add(oe.getId());
		}
		this.refersTo = (String[]) refersTo.toArray();
	}
	
	public void setRefersTo(List<OrionEntity>oes) {
		List<String> refersTo = new ArrayList<String>();
		for (OrionEntity oe :oes) {
			refersTo.add(oe.getId());
		}
		this.refersTo = (String[]) refersTo.toArray();
	}

	public GSMACommons _getGsmaCommons() {
		return _gsmaCommons;
	}

}
