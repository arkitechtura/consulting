package org.cabi.ofra.dataload.event;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * (c) 2014, Eduardo Quirós-Campos
 */
public class Event {
  public enum EVENT_TYPE {INFORMATION, ERROR, WARNING}
  private Map<String, Object> data;
  private String message;
  private Exception exception;
  private EVENT_TYPE type;
  private UUID id;
  private Date timestamp;

  Event(String message) {
    this(message, EVENT_TYPE.INFORMATION, null);
  }

  Event(String message, EVENT_TYPE type) {
    this(message, type, null);
  }

  Event(String message, EVENT_TYPE type, Exception exception) {
    this.message = message;
    this.type = type;
    this.exception = exception;
    this.id = UUID.randomUUID();
    this.timestamp = new Date(System.currentTimeMillis());
  }

  public String getMessage() {
    return message;
  }

  public Exception getException() {
    return exception;
  }

  public EVENT_TYPE getType() {
    return type;
  }

  public void addData(String key, Object value) {
    if (data == null) {
      data = new HashMap<String, Object>();
    }
    data.put(key, value);
  }

  public Object getData(String key) {
    if (data == null) return null;
    return data.get(key);
  }

  public String getId() {
    return id.toString();
  }

  public Date getTimestamp() {
    return timestamp;
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }
}
