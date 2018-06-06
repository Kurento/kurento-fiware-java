package org.kurento.tutorial.platedetector.orion.models.datamodels;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.kurento.orion.connector.entities.OrionEntity;
import org.kurento.tutorial.platedetector.orion.models.GeoJson;
import org.kurento.tutorial.platedetector.orion.models.Organization;

public class DeviceDataModel implements OrionEntity {

	static private String DEVICE_TYPE = "Device";
	String id; // Unique identifier.

	private String type = DEVICE_TYPE; // Entity type. It must be equal to Device.

	private String category; // See attribute category from DeviceModel. Optional but recommended to optimize queries.

	private List<String> controlledProperty; // See attribute controlledProperty from DeviceModel. Optional but recommended to optimize queries.

	private List<String> controlledAsset; // The asset(s) (building, object, etc.) controlled by the device.

	private String mnc; // This property identifies the Mobile Network Code (MNC) of the network the device is attached to. The MNC is used in combination with a Mobile Country Code (MCC) (also known as a "MCC / MNC tuple") to uniquely identify a mobile phone operator/carrier using the GSM, CDMA, iDEN, TETRA and 3G / 4G public land mobile networks and some satellite mobile networks.

	private String mcc; // Mobile Country Code - This property identifies univoquely the country of the mobile network the device is attached to.

	private List<String> macAddress; // The MAC address of the device.

	private List<String> ipAddress; // The IP address of the device. It can be a comma separated list of values if the device has more than one IP address.

	private List<String> supportedProtocol; // See attribute supportedProtocol from DeviceModel. Needed if due to a software update new protocols are supported. Otherwise it is better to convey it at  DeviceModel level.

	private HashMap <String, Object>configuration; // Device's technical configuration. This attribute is intended to be a dictionary of properties which capture parameters which have to do with the configuration of a device (timeouts, reporting periods, etc.) and which are not currently covered by the standard attributes defined by this model.

	private GeoJson location; // Location of this device represented by a GeoJSON geometry of type point.

	private String name; // A mnemonic name given to the device.

	private String description; // Device's description.

	private Date dateInstalled; // A timestamp which denotes when the device was installed (if it requires installation).

	private Date dateFirstUsed; // A timestamp which denotes when the device was first used.

	private Date dateManufactured; // A timestamp which denotes when the device was manufactured.

	private String hardwareVersion; // The hardware version of this device.

	private String softwareVersion; // The software version of this device.

	private String firmwareVersion; // The firmware version of this device.

	private String osVersion; // The version of the host operating system device.

	private Date dateLastCalibration; // A timestamp which denotes when the last calibration of the device happened.

	private String serialNumber; // The serial number assigned by the manufacturer.

	private Organization provider; // The provider of the device.

	private String refDeviceModel; // The device's model.

	private float batteryLevel; // Device's battery level. It must be equal to 1.0 when battery is full. 0.0 when battery ìs empty. null when cannot be determined.

	private String deviceState; // State of this device from an operational point of view. Its value can be vendor dependent.

	private Date dateLastValueReported; // A timestamp which denotes the last time when the device successfully reported data to the cloud.

	private String value; // A observed or reported value. For actuator devices, it is an attribute that allows a controlling application to change the actuation setting. For instance, a switch device which is currently on can report a value "on"of type Text. Obviously, in order to toggle the referred switch, this attribute value will have to be changed to "off".

	private Date dateModified; // Last update timestamp of this entity.

	private Date dateCreated; // Entity's creation timestamp.

	private Organization owner; // The owners of a Device.

	
	@Override
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getType() {
		return type;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List<String> getControlledProperty() {
		return controlledProperty;
	}

	public void setControlledProperty(List<String> controlledProperty) {
		this.controlledProperty = controlledProperty;
	}

	public List<String> getControlledAsset() {
		return controlledAsset;
	}

	public void setControlledAsset(List<String> controlledAsset) {
		this.controlledAsset = controlledAsset;
	}

	public String getMnc() {
		return mnc;
	}

	public void setMnc(String mnc) {
		this.mnc = mnc;
	}

	public String getMcc() {
		return mcc;
	}

	public void setMcc(String mcc) {
		this.mcc = mcc;
	}

	public List<String> getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(List<String> macAddress) {
		this.macAddress = macAddress;
	}

	public List<String> getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(List<String> ipAddress) {
		this.ipAddress = ipAddress;
	}

	public List<String> getSupportedProtocol() {
		return supportedProtocol;
	}

	public void setSupportedProtocol(List<String> supportedProtocol) {
		this.supportedProtocol = supportedProtocol;
	}

	public HashMap<String, Object> getConfiguration() {
		return configuration;
	}

	public void setConfiguration(HashMap<String, Object> configuration) {
		this.configuration = configuration;
	}

	public GeoJson getLocation() {
		return location;
	}

	public void setLocation(GeoJson location) {
		this.location = location;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDateInstalled() {
		return dateInstalled;
	}

	public void setDateInstalled(Date dateInstalled) {
		this.dateInstalled = dateInstalled;
	}

	public Date getDateFirstUsed() {
		return dateFirstUsed;
	}

	public void setDateFirstUsed(Date dateFirstUsed) {
		this.dateFirstUsed = dateFirstUsed;
	}

	public Date getDateManufactured() {
		return dateManufactured;
	}

	public void setDateManufactured(Date dateManufactured) {
		this.dateManufactured = dateManufactured;
	}

	public String getHardwareVersion() {
		return hardwareVersion;
	}

	public void setHardwareVersion(String hardwareVersion) {
		this.hardwareVersion = hardwareVersion;
	}

	public String getSoftwareVersion() {
		return softwareVersion;
	}

	public void setSoftwareVersion(String softwareVersion) {
		this.softwareVersion = softwareVersion;
	}

	public String getFirmwareVersion() {
		return firmwareVersion;
	}

	public void setFirmwareVersion(String firmwareVersion) {
		this.firmwareVersion = firmwareVersion;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	public Date getDateLastCalibration() {
		return dateLastCalibration;
	}

	public void setDateLastCalibration(Date dateLastCalibration) {
		this.dateLastCalibration = dateLastCalibration;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public Organization getProvider() {
		return provider;
	}

	public void setProvider(Organization provider) {
		this.provider = provider;
	}

	public String getRefDeviceModel() {
		return refDeviceModel;
	}

	public void setRefDeviceModel(String refDeviceModel) {
		this.refDeviceModel = refDeviceModel;
	}

	public float getBatteryLevel() {
		return batteryLevel;
	}

	public void setBatteryLevel(float batteryLevel) {
		this.batteryLevel = batteryLevel;
	}

	public String getDeviceState() {
		return deviceState;
	}

	public void setDeviceState(String deviceState) {
		this.deviceState = deviceState;
	}

	public Date getDateLastValueReported() {
		return dateLastValueReported;
	}

	public void setDateLastValueReported(Date dateLastValueReported) {
		this.dateLastValueReported = dateLastValueReported;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Organization getOwner() {
		return owner;
	}

	public void setOwner(Organization owner) {
		this.owner = owner;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	

}
