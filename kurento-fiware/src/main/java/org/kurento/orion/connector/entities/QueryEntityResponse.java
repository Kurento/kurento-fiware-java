/*
 * (C) Copyright 2014 Kurento (http://kurento.org/)
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-2.1.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 */
package org.kurento.orion.connector.entities;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * Query entity list response object
 * 
 * @author Guiomar Tuñón (gtunon@naevatec.com)
 */
public class QueryEntityResponse extends OrionResponse{
	
	@SerializedName("contextElement")
	private List<OrionEntity> elements;


	public  QueryEntityResponse() {
	}

	/**
	 * @return the element
	 */
	public  List<OrionEntity> getElement() {
		return elements;
	}

	/**
	 * @param element
	 *            the element to set
	 */
	public  void setElements(List<OrionEntity> elements) {
		this.elements = elements;
	}

	@Override
	public  String toString() {
		StringBuilder sb = new StringBuilder();
		for (OrionEntity e : elements) {
			sb.append(e).append("\n");
			sb.append(super.toString());
		}
		return sb.toString();
	}
}
