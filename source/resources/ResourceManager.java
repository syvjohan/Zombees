package resources;

import java.util.HashMap;
import eventsystem.*;
import eventsystem.events.SpriteCreatedEvent;
import graphics.BaseSprite;
//import resources.ResourceType;

public class ResourceManager implements IEventListener {

  private static ResourceManager singleton;

  private HashMap<String, Resource> resourceMap;

  public static void initialize() {
    singleton = new ResourceManager();

    EventManager.registerEventListener(SpriteCreatedEvent.kEventType, singleton);

  }

  // Implementation for IEventListener
  @Override
  public String getName() {
    return "ResourceManager";
  }

  // For interface IEventListener,
  // this is where most of the resource management logic should take place.
  @Override
  public boolean handleEvent(BaseEvent event) {

    // Handle sprite created event.
    if(event.getEventType().getID() == SpriteCreatedEvent.kEventType.getID()) {
      BaseSprite sprite = ((SpriteCreatedEvent)event).getSprite();

      String rName = sprite.getTextureResourceName();

      Resource resource = resourceMap.get(rName);

      // Make sure the sprites resource exists!
      assert resource != null;

      if(!resource.isLoaded()) {
        resource.loadResource();  
      }
    }
    return false;
  }

  public static Resource getResource(String name) {
    return singleton.safeGetResource(name);
  }

  public static void createResource(ResourceType type, String name, String filepath) {
    singleton.safeCreateResource(type, name, filepath);
  }

  // Removes a resource completely from the manager.
  public static void destroyResource(String name) {
    singleton.safeDestroyResource(name);
  }

  private ResourceManager() {
    resourceMap = new HashMap<String, Resource>();
  }

  // Check if a resource with the given name already exists.
  private boolean checkResourceExists(String name) {
    Resource resource = resourceMap.get(name);
    return (resource != null);
  }

  private Resource safeGetResource(String name) {
    Resource resource = resourceMap.get(name);

    // Make sure we're not requesting resources that don't exist.
    assert resource != null;

    // Make sure the resource is loaded.
    if(!resource.isLoaded()) {
      resource.loadResource();
    }

    return resource;
  }
  

  // Creates a resource wrapper with the given name and type.
  private void safeCreateResource(ResourceType type, String name, String filepath) {

    // Make sure we're not creating the same resource several times.
    assert !checkResourceExists(name);

    // Create a new shell resource.
    Resource resource = new Resource(type, name, filepath);

    resourceMap.put(name, resource);
  }

  private void safeDestroyResource(String name) {

    // Make sure we're not trying to delete resources that don't exist.
    assert checkResourceExists(name);

    resourceMap.remove(name);
  }
}
      

    



