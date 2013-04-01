package eventsystem.events;
import eventsystem.BaseEvent;
import eventsystem.EventType;


public class WindowCloseRequestEvent extends BaseEvent {

  public static final EventType kEventType = new EventType("windowcloserequestevent");

  @Override
  public EventType getEventType() {
    return kEventType;
  }

}