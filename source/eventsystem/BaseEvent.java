package eventsystem;


// Base Event type for all event classes to inherit from.
public abstract class BaseEvent {

  public abstract EventType getEventType();
}