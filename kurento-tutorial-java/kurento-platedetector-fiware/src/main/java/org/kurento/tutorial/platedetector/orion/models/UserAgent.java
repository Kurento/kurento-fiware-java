package org.kurento.tutorial.platedetector.orion.models;

public class UserAgent {
	
	
	public class UA_Browser{
		private String family;
		private int major;
		private int minor;
		private int patch;
		private String name;
		private String version;
		public String getFamily() {
			return family;
		}
		public void setFamily(String family) {
			this.family = family;
		}
		public int getMajor() {
			return major;
		}
		public void setMajor(int major) {
			this.major = major;
		}
		public int getMinor() {
			return minor;
		}
		public void setMinor(int minor) {
			this.minor = minor;
		}
		public int getPatch() {
			return patch;
		}
		public void setPatch(int patch) {
			this.patch = patch;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getVersion() {
			return version;
		}
		public void setVersion(String version) {
			this.version = version;
		}
	
	}
	public class UA_Os{
		private String family;
		private int major;
		private int minor;
		private int patch;
		private String name;
		private String version;
		
		public String getFamily() {
			return family;
		}
		public void setFamily(String family) {
			this.family = family;
		}
		public int getMajor() {
			return major;
		}
		public void setMajor(int major) {
			this.major = major;
		}
		public int getMinor() {
			return minor;
		}
		public void setMinor(int minor) {
			this.minor = minor;
		}
		public int getPatch() {
			return patch;
		}
		public void setPatch(int patch) {
			this.patch = patch;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getVersion() {
			return version;
		}
		public void setVersion(String version) {
			this.version = version;
		}
	}
	
	public class UA_Device{
		private String family;
		private String type;
		private String manufacturer;
		
		public String getFamily() {
			return family;
		}
		public void setFamily(String family) {
			this.family = family;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getManufacturer() {
			return manufacturer;
		}
		public void setManufacturer(String manufacturer) {
			this.manufacturer = manufacturer;
		}
	}
	private String source;
	private UA_Browser browser;
	private UA_Os os;
	private UA_Device device;
	
	public UserAgent() {
		browser = new UA_Browser();
		os = new UA_Os();
		device = new UA_Device();
	}
	
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public UA_Browser getBrowser() {
		return browser;
	}
	public void setBrowser(UA_Browser browser) {
		this.browser = browser;
	}
	public UA_Os getOs() {
		return os;
	}
	public void setOs(UA_Os os) {
		this.os = os;
	}
	public UA_Device getDevice() {
		return device;
	}
	public void setDevice(UA_Device device) {
		this.device = device;
	}
	
	
}
