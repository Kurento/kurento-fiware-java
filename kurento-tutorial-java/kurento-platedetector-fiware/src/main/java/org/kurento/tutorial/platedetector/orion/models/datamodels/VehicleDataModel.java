/*
 * (C) Copyright 2017 Kurento (http://kurento.org/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.kurento.tutorial.platedetector.orion.models.datamodels;

import java.util.Date;
import java.util.List;

import org.kurento.orion.connector.entities.OrionEntity;
import org.kurento.tutorial.platedetector.orion.models.GeoJson;
import org.kurento.tutorial.platedetector.orion.models.Person;

//Data model taken from http://fiware-datamodels.readthedocs.io/en/latest/Transportation/Vehicle/Vehicle/doc/spec/index.html
public class VehicleDataModel implements OrionEntity {

	private static final String VEHICLE_TYPE = "Vehicle";

	private String id;
	private String type = VEHICLE_TYPE;

	private String name;

	private String vehicleType;
	private List<String> category;
	private GeoJson location;
	private GeoJson previousLocation;
	private Integer speed;
	private Integer heading;
	private Float cargoWeight;
	private String vehicleIdentificationNumber;
	private String vehiclePlateIdentifier;
	private Date dateVehicleFirstRegistered;
	private Date dateFirstUsed;
	private Date purchaseDate;
	private Float mileageFromOdometer;
	private String vehicleConfiguration;
	private String color;
	private Person owner;
	private List<String> feature;
	private List<String> serviceProvided;
	private String vehicleSpecialUsage;
	private String refVehicleModel;
	private String areaServed;
	private String serviceStatus;
	private Date dateModified;
	private Date dateCreated;

	@Override
	public String getId() {
		return id;
	}
	
	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getType() {
		return type;
	}
	
	@Override
	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public List<String> getCategory() {
		return category;
	}

	public void setCategory(List<String> category) {
		this.category = category;
	}

	public GeoJson getLocation() {
		return location;
	}

	public void setLocation(GeoJson location) {
		this.location = location;
	}

	public GeoJson getPreviousLocation() {
		return previousLocation;
	}

	public void setPreviousLocation(GeoJson previousLocation) {
		this.previousLocation = previousLocation;
	}

	public Integer getSpeed() {
		return speed;
	}

	public void setSpeed(Integer speed) {
		this.speed = speed;
	}

	public Integer getHeading() {
		return heading;
	}

	public void setHeading(Integer heading) {
		this.heading = heading;
	}

	public Float getCargoWeight() {
		return cargoWeight;
	}

	public void setCargoWeight(Float cargoWeight) {
		this.cargoWeight = cargoWeight;
	}

	public String getVehicleIdentificationNumber() {
		return vehicleIdentificationNumber;
	}

	public void setVehicleIdentificationNumber(String vehicleIdentificationNumber) {
		this.vehicleIdentificationNumber = vehicleIdentificationNumber;
	}

	public String getVehiclePlateIdentifier() {
		return vehiclePlateIdentifier;
	}

	public void setVehiclePlateIdentifier(String vehiclePlateIdentifier) {
		this.vehiclePlateIdentifier = vehiclePlateIdentifier;
	}

	public Date getDateVehicleFirstRegistered() {
		return dateVehicleFirstRegistered;
	}

	public void setDateVehicleFirstRegistered(Date dateVehicleFirstRegistered) {
		this.dateVehicleFirstRegistered = dateVehicleFirstRegistered;
	}

	public Date getDateFirstUsed() {
		return dateFirstUsed;
	}

	public void setDateFirstUsed(Date dateFirstUsed) {
		this.dateFirstUsed = dateFirstUsed;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public Float getMileageFromOdometer() {
		return mileageFromOdometer;
	}

	public void setMileageFromOdometer(Float mileageFromOdometer) {
		this.mileageFromOdometer = mileageFromOdometer;
	}

	public String getVehicleConfiguration() {
		return vehicleConfiguration;
	}

	public void setVehicleConfiguration(String vehicleConfiguration) {
		this.vehicleConfiguration = vehicleConfiguration;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Person getOwner() {
		return owner;
	}

	public void setOwner(Person owner) {
		this.owner = owner;
	}

	public List<String> getFeature() {
		return feature;
	}

	public void setFeature(List<String> feature) {
		this.feature = feature;
	}

	public List<String> getServiceProvided() {
		return serviceProvided;
	}

	public void setServiceProvided(List<String> serviceProvided) {
		this.serviceProvided = serviceProvided;
	}

	public String getVehicleSpecialUsage() {
		return vehicleSpecialUsage;
	}

	public void setVehicleSpecialUsage(String vehicleSpecialUsage) {
		this.vehicleSpecialUsage = vehicleSpecialUsage;
	}

	public String getRefVehicleModel() {
		return refVehicleModel;
	}

	public void setRefVehicleModel(String refVehicleModel) {
		this.refVehicleModel = refVehicleModel;
	}

	public String getAreaServed() {
		return areaServed;
	}

	public void setAreaServed(String areaServed) {
		this.areaServed = areaServed;
	}

	public String getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(String serviceStatus) {
		this.serviceStatus = serviceStatus;
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

}
