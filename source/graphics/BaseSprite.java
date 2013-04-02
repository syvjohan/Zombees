package graphics;


public abstract class BaseSprite {

  // Used to determine the type of a sprite instance.
  public abstract String getName();

  // Retrieve the texture resource name this sprite uses.
  // This is used for optimization in the spritemanagers texture generation.
  public abstract String getTextureResourceName();

  // Called from spritemanager to give the sprite update ticks.
  public abstract void update(double deltaTime);

}