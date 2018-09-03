package org.kurento.tutorial.platedetector.models;

public class Camera {
	
	//here data obtained that can be translated to a "Device Data Model"
	//to know::
	
	private String[] controlledAsset;
	private String creationDate;
	private String id;
	private String[] ip;
	private String Name;
	private String state; //NEW => CONFIGURED => PROCESSING <=> STOP => DISABLED
	private String type;
	private String updateDate;
	
	public String[] getControlledAsset() {
		return controlledAsset;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public String getId() {
		return id;
	}
	public String[] getIp() {
		return ip;
	}
	public String getName() {
		return Name;
	}
	public String getState() {
		return state;
	}
	public String getType() {
		return type;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setControlledAsset(String[] strings) {
		this.controlledAsset = strings;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setIp(String[] strings) {
		this.ip = strings;
	}
	public void setName(String name) {
		Name = name;
	}
	public void setState(String state) {
		this.state = state;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	
	
}
