package graphics;

import java.util.ArrayList;
import eventsystem.*;
import eventsystem.events.SpriteCreatedEvent;

public class SpriteManager implements IEventListener {

  private static SpriteManager singleton;
 

  ArrayList<BaseSprite> spritelist;

  // Must be called at engine startup.
  public static void initialize() {
    singleton = new SpriteManager();

    // TODO Register spritemanager with events etc.

    // Register the SpriteCreated event.
    EventManager.registerEventType(SpriteCreatedEvent.kEventType);
  }


  // Adds a sprite to the spritemanagers list.
  public static void registerSprite(BaseSprite sprite) {
    singleton.safeRegisterSprite(sprite);
  }

  // Removes a sprite from the spritemanagers list.
  public static void unRegisterSprite(BaseSprite sprite) {
    singleton.safeUnRegisterSprite(sprite);
  }

  // Updates all sprites registered with the spritemanager.
  public static void update(double deltaTime) {
    singleton.safeUpdate(deltaTime);
  }

  // For IEventListener interface
  @Override
  public boolean handleEvent(BaseEvent event) {

    return false;
  }

  // For IEventListener interface
  @Override
  public String getName() {
    return "SpriteManager";
  }

  // private Constructor
  private SpriteManager() {
    spritelist = new ArrayList<BaseSprite>();
  }

  private void safeRegisterSprite(BaseSprite sprite) {
    // Make sure sprites don't get added to the list multiple times.
    assert !checkSpriteExists(sprite);

    spritelist.add(sprite);

    // Push an event notifying the engine that a new sprite was created.
    EventManager.pushEvent(new SpriteCreatedEvent(sprite));
  }

  private void safeUnRegisterSprite(BaseSprite sprite) {
    // Make sure we don't try to unregister sprites that don't exist.
    assert checkSpriteExists(sprite);

    spritelist.remove(sprite);
  }

  private void safeUpdate(double deltaTime) {

    // Update all sprites.
    for(BaseSprite sprite : spritelist) {
      sprite.update(deltaTime);
    }

  }

  private boolean checkSpriteExists(BaseSprite sprite) {
    for(int i = 0; i < spritelist.size(); ++i) {
      if(sprite == spritelist.get(i))
        return true;
    }

    return false;
  }

}