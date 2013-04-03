package utilities.math;

// 2D Vector class

public class Vec2 {

  public float x, y;

  public Vec2(float x, float y) {
    this.x = x;
    this.y = y;
  }

  public Vec2(Vec2 copy) {
    this.x = copy.x;
    this.y = copy.y;
  }

  public Vec2 mul(float c) {
    return new Vec2(x * c, y * c);
  }

  public Vec2 add(Vec2 vec) {
    return new Vec2(x + vec.x, y + vec.y);
  }

  public Vec2 sub(Vec2 vec) {
    return new Vec2(x - vec.x, y - vec.y);
  }

  public float dot(Vec2 rhs) {
    return x * rhs.x + y * rhs.y;
  }

  public static float dot(Vec2 lhs, Vec2 rhs) {
    return lhs.x * rhs.x + lhs.y * rhs.y;
  }

  public float getLength() {
    return (float)Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
  }

  public static float distance(Vec2 lhs, Vec2 rhs) {
    return rhs.sub(lhs).getLength();
  }

  public void normalize() {
    float len = getLength();
    x = x / len;
    y = y / len;
  }

  public Vec2 getUnit() {
    Vec2 v = new Vec2(this);
    v.normalize();
    return v;
  }

  // Potentially bogus implementation.
  public static Vec2 lerp(Vec2 start, Vec2 end, float weight) {
    return new Vec2(start.x + (end.x - start.x) * weight,
      start.y + (end.y - start.y) * weight);
  }

}