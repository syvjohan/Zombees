package utilities.math;


public class ClipRect {

  public float x, y, w, h;

  public ClipRect(float x, float y, float width, float height) {
    this.x = x;
    this.y = y;
    this.w = width;
    this.h = height;
  }

  public ClipRect(Vec2 min, Vec2 max) {
    this(min.x, min.y, max.x - min.x, max.y - min.y);
  }

}
