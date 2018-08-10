package org.kurento.orion.connector.entities.commons;

import java.io.Serializable;

public class LocationCommons implements Serializable{

	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public String getStreetAddress() {
		return address.streetAddress;
	}
	public void setStreetAddress(String streetAddress) {
		this.address.streetAddress = streetAddress;
	}
	public String getAddressLocality() {
		return address.addressLocality;
	}
	public void setAddressLocality(String addressLocality) {
		this.address.addressLocality = addressLocality;
	}
	public String getAddressRegion() {
		return address.addressRegion;
	}
	public void setAddressRegion(String addressRegion) {
		this.address.addressRegion = addressRegion;
	}
	public String getAddressCountry() {
		return address.addressCountry;
	}
	public void setAddressCountry(String addressCountry) {
		this.address.addressCountry = addressCountry;
	}
	public String getPostalCode() {
		return address.postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.address.postalCode = postalCode;
	}
	public String getPostOfficeBoxNumber() {
		return address.postOfficeBoxNumber;
	}
	public void setPostOfficeBoxNumber(String postOfficeBoxNumber) {
		this.address.postOfficeBoxNumber = postOfficeBoxNumber;
	}
	public String getAreaServed() {
		return areaServed;
	}
	public void setAreaServed(String areaServed) {
		this.areaServed = areaServed;
		this.address.areaServed = areaServed;
	}
	private static final long serialVersionUID = -942971062695474894L;
		
	public LocationCommons() {
		this.location = new Location();
		this.address = new Address();
	}
	class Location {
			// "http://json-schema.org/geojson/geometry.json# 404 - Not Found
		}
		
	class Address{
		String streetAddress;		
		String addressLocality;
		String addressRegion;
		String addressCountry;
		String postalCode;
		String postOfficeBoxNumber;
		String areaServed;
	}
		
	Location location;
	Address address;
	String areaServed;	    
}
