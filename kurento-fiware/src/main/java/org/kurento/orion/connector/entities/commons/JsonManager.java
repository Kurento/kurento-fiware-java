package org.kurento.orion.connector.entities.commons;

import org.kurento.orion.connector.entities.OrionEntity;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

public abstract class JsonManager <T extends OrionEntity> implements JsonDeserializer<T>, JsonSerializer<T>{

}
