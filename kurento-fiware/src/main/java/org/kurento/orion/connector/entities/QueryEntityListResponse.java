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

import com.google.common.collect.ImmutableList;
import com.google.gson.annotations.SerializedName;

/**
 * Query context response object
 * 
 * @author Ivan Gracia (izanmail@gmail.com)
 * @author Guiomar Tuñón (gtunon@naevatec.com)
 */

public class QueryEntityListResponse{
	
	@SerializedName("contextResponses")
	private List<QueryEntityResponse> contextElements;

	public  QueryEntityListResponse(QueryEntityResponse... contextElements) {
		this.contextElements = ImmutableList.copyOf(contextElements);
	}

	/**
	 * @return the contextElements
	 */
	public  List<QueryEntityResponse> getContextElements() {
		return contextElements;
	}

	/**
	 * @param contextElements
	 *            the contextElements to set
	 */
	public  void setContextElements(
			List<QueryEntityResponse> contextElements) {
		this.contextElements = contextElements;
	}

	@Override
	public  String toString() {
		StringBuilder sb = new StringBuilder();

		for (QueryEntityResponse response : contextElements) {
			sb.append(response).append("\n");
		}

		return sb.toString();
	}
}
