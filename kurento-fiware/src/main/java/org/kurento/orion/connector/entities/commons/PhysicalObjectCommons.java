package org.kurento.orion.connector.entities.commons;

import java.io.Serializable;

public class PhysicalObjectCommons implements Serializable{

	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String[] getAnnotations() {
		return annotations;
	}
	public void setAnnotations(String[] annotations) {
		this.annotations = annotations;
	}
	private static final long serialVersionUID = 1446195569593430516L;
	
	String color; 
	String image; 
	String[] annotations; 
}
