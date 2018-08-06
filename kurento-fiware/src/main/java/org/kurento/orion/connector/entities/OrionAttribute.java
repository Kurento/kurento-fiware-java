package org.kurento.orion.connector.entities;

/**
 * Attribute to be used in context elements and queries
 *
 * @author Ivan Gracia (izanmail@gmail.com). Guiomar Tuñón (gtunon@naevatec.com)
 * @param <T>
 *          The type of attribute
 *
 */
public class OrionAttribute<T> {

  private String name;
  private String type;
  private T value;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public T getValue() {
    return value;
  }

  public void setValue(T value) {
    this.value = value;
  }

  public OrionAttribute(String name, String type, T value) {
    super();
    this.name = name;
    this.type = type;
    this.value = value;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    sb.append(" Name: ").append(name).append("\n");
    sb.append(" Type: ").append(type).append("\n");
    sb.append(" Value: ").append(value).append("\n");

    return sb.toString();
  }
}