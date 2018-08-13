package org.kurento.orion.connector.entities.commons;

import java.io.Serializable;

public class MediaSource implements Serializable{

	private static final long serialVersionUID = -767808878000289128L;
	
	String name;
	String creationTime;
	boolean sendTagsInEvents;
	MediaSource parent;
	
	
	public MediaSource() {}
			
	public MediaSource(String name, String creationTime, boolean sendTagsInEvents, MediaSource parent) {
		super();
		this.name = name;
		this.creationTime = creationTime;
		this.sendTagsInEvents = sendTagsInEvents;
		this.parent = parent;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}
	public boolean isSendTagsInEvents() {
		return sendTagsInEvents;
	}
	public void setSendTagsInEvents(boolean sendTagsInEvents) {
		this.sendTagsInEvents = sendTagsInEvents;
	}
	public MediaSource getParent() {
		return parent;
	}
	public void setParent(MediaSource parent) {
		this.parent = parent;
	}
	
	

}
