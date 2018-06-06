package org.kurento.tutorial.platedetector.orion.models;

public class WebRTCDevice {
	
	private UserAgent ua;
	
	private String ip;
	
	private String id; 
	
	private String url;
	
	private String name;
	
	private boolean active = false;

	public WebRTCDevice(UserAgent ua, String ip) {
		super();
		this.ua = ua;
		this.ip = ip;
	}
	
	public WebRTCDevice() {
		this.ua = new UserAgent();
		this.ip = "";
	}

	public UserAgent getUa() {
		return ua;
	}
	public void setUa(UserAgent ua) {
		this.ua = ua;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.url="/v2/entities/"+id+"?type=Device";
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
		this.id= url.substring(url.lastIndexOf('/'), url.indexOf('?'));
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
