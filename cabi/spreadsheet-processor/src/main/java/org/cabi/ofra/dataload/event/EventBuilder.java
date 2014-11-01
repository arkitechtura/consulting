package org.cabi.ofra.dataload.event;

/**
 * (c) 2014, Eduardo Quirós-Campos
 */
public class EventBuilder {
  private String message;
  private Event.EVENT_TYPE type = Event.EVENT_TYPE.INFORMATION;
  private Exception exception;

  public static EventBuilder createBuilder() {
    return new EventBuilder();
  }

  EventBuilder() {
  }

  public EventBuilder withMessage(String message) {
    this.message = message;
    return this;
  }

  public EventBuilder withType(Event.EVENT_TYPE type) {
    this.type = type;
    return this;
  }

  public EventBuilder withException(Exception exception ) {
    this.exception = exception;
    return this;
  }

  public Event build() {
    return new Event(message, type, exception);
  }
}
