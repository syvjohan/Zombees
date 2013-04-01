package eventsystem;


// Basically a wrapper around a string,
// Generates a hashcode for every string it gets passed.
public class EventType {

  // A special event string that lets listeners listen to any event.
  public static final EventType kWildCard = new EventType("wildcard");

  private int hashCode = -1;
  private String eventName = "";

  public EventType(String name) {
    if(name != "wildcard") {
      hashCode = name.toLowerCase().hashCode();  
    } else {
      hashCode = 0;
    }
    
    eventName = name.toLowerCase();
  }

  public int getID() {
    return hashCode;
  }

  public String getName() {
    return eventName;
  }

}