/*
 * Copyright 2018 Kurento (https://www.kurento.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kurento.orion.connector.entities.device;

import java.io.Serializable;
import java.util.ArrayList;

import javax.validation.constraints.Pattern;

import org.kurento.orion.connector.entities.OrionEntity;
import org.kurento.orion.connector.entities.commons.DeviceCommons;
import org.kurento.orion.connector.entities.commons.GSMACommons;
import org.kurento.orion.connector.entities.commons.LocationCommons;
import org.kurento.orion.connector.entities.commons.PhysicalObjectCommons;

/**
 * Device Data Model from
 * https://fiware.github.io/dataModels/specs/Device/Device/schema.json
 * 
 * @author Guiomar Tuñón (guiomar.tunon@gmail.com)
 *
 */
public class Device implements OrionEntity, Serializable {

  private static final long serialVersionUID = -1602741890197466709L;

  GSMACommons _gsmaCommons;
  LocationCommons _locationCommons;
  PhysicalObjectCommons _pysicalCommons;
  DeviceCommons _deviceCommons;

  /* NGSI Entity type */
  String type = "Device";
  String[] controlledAsset;
  String mnc;
  String mcc;

  @Pattern(regexp = "^([[:xdigit:]]{2}[:.-]?){5}[[:xdigit:]]{2}$")
  String[] macAddress;
  String[] ipAddress;
  Object configuration;
  String dateInstalled; // format": "date-time"
  String dateFirstUsed; // format": "date-time"
  String dateManufactured; // format": "date-time"
  String hardwareVersion;
  String sofwareVersion;
  String firmwareVersion;
  String osVersion;
  String dateLastCalibration;
  String serialNumber;
  String provider;
  String refDeviceModel;
  Float batteryLevel;
  String deviceState;
  String dateLastValueReported;
  String value;

  public Device(String id, String category, String... controlledProperties) {
	this._gsmaCommons = new GSMACommons();
	this._deviceCommons = new DeviceCommons();
	this._locationCommons = new LocationCommons();
	this._pysicalCommons = new PhysicalObjectCommons();

	this._gsmaCommons.id = id;
	ArrayList<String> ctlist = new ArrayList<String>();
	ctlist.add(category);
	this._deviceCommons.category = ctlist.toArray(new String[0]);

	ArrayList<String> cplist = new ArrayList<String>();
	for (String s : controlledProperties) {
	  cplist.add(s);
	}
	this._deviceCommons.controlledProperty = ctlist.toArray(new String[0]);
  }

  public Device() {
	this._gsmaCommons = new GSMACommons();
	this._deviceCommons = new DeviceCommons();
	this._locationCommons = new LocationCommons();
	this._pysicalCommons = new PhysicalObjectCommons();
  }

  @Override
  public String getId() {
	return _gsmaCommons.id;
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
	this.type = type;
  }

  public GSMACommons _getGsmaCommons() {
	return _gsmaCommons;
  }

  public LocationCommons _getLocationCommons() {
	return _locationCommons;
  }

  public PhysicalObjectCommons _getPysicalCommons() {
	return _pysicalCommons;
  }

  public DeviceCommons _getDeviceCommons() {
	return _deviceCommons;
  }

  public String[] getControlledAsset() {
	return controlledAsset;
  }

  public void setControlledAsset(String[] controlledAsset) {
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

  public String[] getMacAddress() {
	return macAddress;
  }

  public void setMacAddress(String[] macAddress) {
	this.macAddress = macAddress;
  }

  public String[] getIpAddress() {
	return ipAddress;
  }

  public void setIpAddress(String[] ipAddress) {
	this.ipAddress = ipAddress;
  }

  public Object getConfiguration() {
	return configuration;
  }

  public void setConfiguration(Object configuration) {
	this.configuration = configuration;
  }

  public String getDateInstalled() {
	return dateInstalled;
  }

  public void setDateInstalled(String dateInstalled) {
	this.dateInstalled = dateInstalled;
  }

  public String getDateFirstUsed() {
	return dateFirstUsed;
  }

  public void setDateFirstUsed(String dateFirstUsed) {
	this.dateFirstUsed = dateFirstUsed;
  }

  public String getDateManufactured() {
	return dateManufactured;
  }

  public void setDateManufactured(String dateManufactured) {
	this.dateManufactured = dateManufactured;
  }

  public String getHardwareVersion() {
	return hardwareVersion;
  }

  public void setHardwareVersion(String hardwareVersion) {
	this.hardwareVersion = hardwareVersion;
  }

  public String getSofwareVersion() {
	return sofwareVersion;
  }

  public void setSofwareVersion(String sofwareVersion) {
	this.sofwareVersion = sofwareVersion;
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

  public String getDateLastCalibration() {
	return dateLastCalibration;
  }

  public void setDateLastCalibration(String dateLastCalibration) {
	this.dateLastCalibration = dateLastCalibration;
  }

  public String getSerialNumber() {
	return serialNumber;
  }

  public void setSerialNumber(String serialNumber) {
	this.serialNumber = serialNumber;
  }

  public String getProvider() {
	return provider;
  }

  public void setProvider(String provider) {
	this.provider = provider;
  }

  public String getRefDeviceModel() {
	return refDeviceModel;
  }

  public void setRefDeviceModel(String refDeviceModel) {
	this.refDeviceModel = refDeviceModel;
  }

  public Float getBatteryLevel() {
	return batteryLevel;
  }

  public void setBatteryLevel(Float batteryLevel) {
	this.batteryLevel = batteryLevel;
  }

  public String getDeviceState() {
	return deviceState;
  }

  public void setDeviceState(String deviceState) {
	this.deviceState = deviceState;
  }

  public String getDateLastValueReported() {
	return dateLastValueReported;
  }

  public void setDateLastValueReported(String dateLastValueReported) {
	this.dateLastValueReported = dateLastValueReported;
  }

  public String getValue() {
	return value;
  }

  public void setValue(String value) {
	this.value = value;
  }

}
