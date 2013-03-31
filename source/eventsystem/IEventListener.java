package eventsystem;

public interface IEventListener {

  // Retrieve the name, if any of the eventlistener instance, mainly useful for debugging.
  public abstract String getName();

  public abstract boolean handleEvent(BaseEvent event);
  


}