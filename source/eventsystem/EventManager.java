
package eventsystem;

import java.util.HashMap;
import java.util.ArrayList;
import utilities.Logger;

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

  // Puts a listener in the list of subscribers for the eventType.
  public static void registerEventListener(EventType eventType, IEventListener listener) {
    singleton.safeRegisterEventListener(eventType, listener);
  }

  // Removes a listener from the list of subscribers for eventType.
  public static void unRegisterEventListener(EventType eventType, IEventListener listener) {
    singleton.safeUnRegisterEventListener(eventType, listener);
  }

  // Registers a new event type, asserts that the eventtype is not registered.
  public static void registerEventType(EventType eventType) {
    singleton.safeRegisterEventType(eventType);
  }

  // Un-Registers an eventtype, causing it to be invalid for listening and pushing.
  public static void unRegisterEventType(EventType eventType) {
    singleton.safeUnRegisterEventType(eventType);
  }

  // pushes a new event onto the eventqueue.
  public static void pushEvent(BaseEvent event) {
    singleton.safePushEvent(event);
  }

  // Pushes an event for immediate processing by the eventmanager. Skip past the que!
  public static void pushEventDirect(BaseEvent event) {
    singleton.safePushEventDirect(event);
  }

  // cancels the last even of the input type, or all of its type if allOfType is true.
  public static void cancelEvent(EventType eventType, boolean allOfType) {
    singleton.safeCancelEvent(eventType, allOfType);
  }

  // Processes all events currently on the eventqueue.
  // Needs to be monitored for an eventual event limit.
  public static void processEvents() {
    singleton.safeProcessEvents();
  }


  private void safeRegisterEventListener(EventType eventType, IEventListener listener) {
    // Make sure event is registered!
    assert checkEventRegistration(eventType);

    ArrayList<IEventListener> listeners = listenerMap.get(eventType.getID());
    if(listeners == null) {
      // This is the first listener to be added for this eventtype.
      listeners = new ArrayList<IEventListener>();
      listenerMap.put(eventType.getID(), listeners);  
    }

    listeners.add(listener);

    Logger.log("Added " + listener.getName() +
     " to the subscriber list of: " + eventType.getName()
     );
  }

  private void safeUnRegisterEventListener(EventType eventType, IEventListener listener) {
    assert checkEventRegistration(eventType);

    // TODO Un-register listeners.
    assert false;

    Logger.log("Removed " + listener.getName() +
     " from the subscriber list of: " + eventType.getName()
    );
  }

  private void safeRegisterEventType(EventType eventType) {

    // Make sure event has not already been registered.
    assert !checkEventRegistration(eventType);

    registeredEvents.add(eventType);
    Logger.log("EventType: " + eventType.getName() +
     " with id: " + eventType.getID() + " was registered!"
    );
  }

  private void safeUnRegisterEventType(EventType eventType) {
     // Make sure event is registered!
    assert checkEventRegistration(eventType); 

    registeredEvents.remove(eventType);

    Logger.log("EventType: " + eventType.getName() +
     " with id: " + eventType.getID() + " was unregistered!"
    );

    // Remove all queued events of the unregistered type, not doing so will cause assertion faults.
    safeCancelEvent(eventType, true);
  }

  private void safePushEvent(BaseEvent event) {
     // Make sure event is registered!
    assert checkEventRegistration(event.getEventType()); 

    eventQueue.add(event);
  }

  private void safePushEventDirect(BaseEvent event) {
     // Make sure event is registered!
    assert checkEventRegistration(event.getEventType()); 

    processEvent(event);
  }

  private void safeCancelEvent(EventType eventType, boolean allOfType){
     // Make sure event is registered!
    assert checkEventRegistration(eventType); 

    // TODO Cancel events.
    assert false;
  }

  private void safeProcessEvents() {

    // Swap eventqueue into active events,
    // Processing events is very likely to trigger new events.
    ArrayList<BaseEvent> tmp = activeEvents;
    activeEvents = eventQueue;
    eventQueue = tmp;


    for(BaseEvent be : activeEvents) {
      processEvent(be);
    }
    
    // Remove all events.
    activeEvents.clear();
  }

  // Processes a single event from the queue
  private void processEvent(BaseEvent event) {

    // Retrieve all eventlisteners that are subscribed to this eventtype.
    ArrayList<IEventListener> listeners = listenerMap.get(event.getEventType().getID());
    
    if(listeners == null) {
      return;
    }

    for(IEventListener ie : listeners) {
      // handleEvent returns true if the eventlistener wants to "swallow" the event.
      if(ie.handleEvent(event)) {
        Logger.log("EventType: " + event.getEventType().getName() + 
          " was swallowed by: " + ie.getName()
        );
        break;
      }
    }

    // Wildcard listeners get notified of everything that happens.
    ArrayList<IEventListener> wildcards = listenerMap.get(0);
    
    for(IEventListener ie : wildcards) {
      ie.handleEvent(event);
    }
  }

  // Looks up a particular event in the registered events list.
  private boolean checkEventRegistration(EventType eType) {
    for(EventType et : registeredEvents) {
      if(et.getID() == eType.getID()) {
        return true;
      }
    }

    return false;
  }

}