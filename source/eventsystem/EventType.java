package eventsystem;

public class EventType {

  static String kWildCard = "WildCard";

  private int hashCode = -1;
  private String eventName = "";

  public EventType(String name) {
    hashCode = name.toLowerCase().hashCode();
    eventName = name.toLowerCase();
  }

  public int getID() {
    return hashCode;
  }

  public String getName() {
    return eventName;
  }

}