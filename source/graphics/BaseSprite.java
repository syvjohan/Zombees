package graphics;

import utilities.math.Vec2;
import utilities.math.ClipRect;

public abstract class BaseSprite {

  // Used by the engine to reference this sprite instance.
  private int spriteID;


  // Sprite Render States variables
  private Vec2 position;
  private float rotation;
  private float scale;
  private Vec2 scaleOrigin;
  private ClipRect clip;
  private int layer;

  // Used to make sure we set all parameters for sprites.
  private boolean isPositionSet = false;
  private boolean isRotationSet = false;
  private boolean isScaleSet    = false;
  private boolean isOriginSet   = false;
  private boolean isClipSet     = false;
  private boolean isLayerSet    = false;

  public BaseSprite() {

    // Make sure all sprites are registered with the spritemanager.
    SpriteManager.registerSprite(this);

    position = new Vec2(0,0);
    scaleOrigin = new Vec2(0,0);
    rotation = 0f;
    scale = 1f;
    clip = new ClipRect(0,0,0,0);
  }

  // These methods should not, and can not be overridden by derived classes.
  public final int getSpriteID() {
    return spriteID;
  }
  public final void setSpriteID(int id) {
    spriteID = id;
  }

  public final int getLayer() {
    return layer;
  }

  public final void setLayer(int layer) {
    this.layer = layer;
  }

  // Used to determine the type of a sprite instance.
  public abstract String getName();

  // Retrieve the texture resource name this sprite uses.
  // This is used for optimization in the spritemanagers texture generation.
  public abstract String getTextureResourceName();

  public abstract boolean isVisible();

  // Called from spritemanager to give the sprite update ticks.
  public abstract void update(double deltaTime);

  public abstract void draw();



}