
package eventsystem;

import java.util.HashMap;
import java.util.ArrayList;

public class EventManager {

  private static EventManager singleton = null;

  HashMap<Integer, ArrayList<IEventListener>> listenerMap;
  ArrayList<EventType> registeredEvents;
  ArrayList<BaseEvent> eventQueue;
  ArrayList<BaseEvent> activeEvents;

  private EventManager() {
    listenerMap = new HashMap<Integer, ArrayList<IEventListener>>();
    registeredEvents = new ArrayList<EventType>();
    eventQueue = new ArrayList<BaseEvent>();
    activeEvents = new ArrayList<BaseEvent>();
  }

  public static void initialize() {

    singleton = new EventManager();
  }

  public static void registerListener(EventType eventType, IEventListener listener) throws EventException {

    singleton.safeRegisterListener(eventType, listener);
  }

  public static void unRegisterListener(EventType eventType, IEventListener listener) throws EventException {
    singleton.safeUnRegisterListener(eventType, listener);
  }

  public static void registerEventType(EventType eventType) throws EventException {
    singleton.safeRegisterEventType(eventType);
  }

  public static void unRegisterEventType(EventType eventType) throws EventException {
    singleton.safeUnRegisterEventType(eventType);
  }

  public static void pushEvent(BaseEvent event) throws EventException {
    singleton.safePushEvent(event);
  }

  public static void pushEventDirect(BaseEvent event) throws EventException {
    singleton.safePushEventDirect(event);
  }


  // Needs parameter for "all of type"
  public static void cancelEvent(EventType eventType, boolean allOfType) throws EventException {
    singleton.safeCancelEvent(eventType, allOfType);
  }


  public static void processEvents() {
    singleton.safeProcessEvents();
  }


  private void safeRegisterListener(EventType eventType, IEventListener listener) throws EventException {
    if(checkEventRegistration(eventType) == false) {
      throw new EventException("Event: " +  eventType.getName() + " is not registered!");
    }
  }

  private void safeUnRegisterListener(EventType eventType, IEventListener listener) throws EventException {
    if(checkEventRegistration(eventType) == false) {
      throw new EventException("Event: " +  eventType.getName() + " is not registered!");
    }
  }

  private void safeRegisterEventType(EventType eventType) throws EventException {

  }

  private void safeUnRegisterEventType(EventType eventType) throws EventException {
    if(checkEventRegistration(eventType) == false) {
      throw new EventException("Event: " +  eventType.getName() + " is not registered!");
    }
  }

  private void safePushEvent(BaseEvent event) throws EventException {

  }

  private void safePushEventDirect(BaseEvent event) throws EventException {

  }

  private void safeCancelEvent(EventType eventType, boolean allOfType) throws EventException {

  }

  private void safeProcessEvents() {

  }

  private void processEvent(BaseEvent event) {

  }

  private boolean checkEventRegistration(EventType eType) {
    for(EventType et : registeredEvents) {
      if(et.getID() == eType.getID()) {
        return true;
      }
    }

    return false;
  }

}