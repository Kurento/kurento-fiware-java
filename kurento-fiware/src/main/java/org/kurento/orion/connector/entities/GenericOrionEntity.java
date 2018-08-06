package org.kurento.orion.connector.entities;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class GenericOrionEntity<V, K> implements OrionEntity,JsonDeserializer<GenericOrionEntity> {

	 private String type;

	  private boolean isPattern;
	  private String id;
	  private final List<OrionAttribute<?>> attributes = new ArrayList<OrionAttribute<?>>();

	  public String getType() {
	    return type;
	  }

	  public void setType(String type) {
	    this.type = type;
	  }

	  public boolean isPattern() {
	    return isPattern;
	  }

	  public void setPattern(boolean isPattern) {
	    this.isPattern = isPattern;
	  }

	  public String getId() {
	    return id;
	  }

	  public void setId(String id) {
	    this.id = id;
	  }

	  public List<OrionAttribute<?>> getAttributes() {
	    return attributes;
	  }

	  @Override
	  public String toString() {
	    StringBuilder sb = new StringBuilder();

	    sb.append(" Type: ").append(type).append("\n");
	    sb.append(" Id: ").append(id).append("\n");
	    sb.append(" IsPattern: ").append(isPattern).append("\n");

	    return sb.toString();
	  }
	  

	@Override
	public GenericOrionEntity deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
		
		JsonObject object = jsonElement.getAsJsonObject();
		GenericOrionEntity entity = new GenericOrionEntity();
		
		for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
        if (entry.getKey().equals("id")) entity.setId(context.deserialize(entry.getValue(), String.class));
        else if (entry.getKey().equals("type")) entity.setId(context.deserialize(entry.getValue(), String.class));
        else {
        	entity.getAttributes().add(context.deserialize(entry.getValue(), Object.class));
        }
      }
		return entity;
	}
}
