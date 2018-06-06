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
 * Context update request object
 * 
 * @author Ivan Gracia (izanmail@gmail.com)
 * @author Guiomar Tuñón (gtunon@naevatec.com)
 */

public class OrionBatchQueryAction {
	
	public static enum Action {
		UPDATE, DELETE, APPEND, APPEND_STRICT;
	}

	@SerializedName("entities")
	private List<OrionEntity> elements;

	@SerializedName("actionType")
	private Action action;

	public  OrionBatchQueryAction(Action action, OrionEntity... elements) {
		this.action = action;
		this.elements = ImmutableList.copyOf(elements);
	}

	/**
	 * @return the contextElements
	 */
	public  List<OrionEntity> getElements() {
		return elements;
	}

	/**
	 * @param contextElements
	 *            the contextElements to set
	 */
	public  void setElements(List<OrionEntity> elements) {
		this.elements = elements;
	}

	/**
	 * @return the updateAction
	 */
	public  Action getAction() {
		return action;
	}

	/**
	 * @param updateAction
	 *            the updateAction to set
	 */
	public  void setUpdateAction(Action action) {
		this.action = action;
	}

	@Override
	public  String toString() {
		StringBuilder sb = new StringBuilder();

		for (OrionEntity element : elements) {
			sb.append(element).append("\n");
		}
		sb.append(action);
		return sb.toString();
	}
}
