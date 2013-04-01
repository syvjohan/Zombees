package eventsystem;

// Interface for classes to implement in order to act as eventlisteners.
public interface IEventListener {

  // Retrieve the name, if any of the eventlistener instance, mainly useful for debugging.
  public abstract String getName();

  // implemented by listeners to handle events sent by the eventManager.
  // Returning true means the listener wants to swallow the event.
  public abstract boolean handleEvent(BaseEvent event);
 


}