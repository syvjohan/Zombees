package eventsystem.events;

import eventsystem.BaseEvent;
import eventsystem.EventType;
import graphics.BaseSprite;

public class SpriteCreatedEvent extends BaseEvent {

  public static final EventType kEventType = new EventType("spritecreatedevent");


  private BaseSprite sprite;

  public SpriteCreatedEvent(BaseSprite sprite) {
    this.sprite = sprite;
  }

  @Override
  public EventType getEventType() {
    return kEventType;
  }

  public BaseSprite getSprite() {
    return sprite;
  }

}