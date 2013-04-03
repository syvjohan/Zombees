package graphics;

import utilities.math.Vec2;

public class VertexPositionTexture {
  
  private Vec2 position;
  private Vec2 texCoords;

  public VertexPositionTexture(float posX, float posY, float texX, float texY) {
    this(new Vec2(posX, posY), new Vec2(texX, texY));
  }

  public VertexPositionTexture(Vec2 position, Vec2 texCoords) {
    this.position = position;
    this.texCoords = texCoords;
  }

  public Vec2 getPosition() {
    return position;
  }

  public Vec2 getTexCoords() {
    return texCoords;
  }

  public void setPosition(float x, float y) {
    position.x = x;
    position.y = y;
  }

  public void setPosition(Vec2 newPos) {
    setPosition(newPos.x, newPos.y);
  }

  public void setTexCoords(float x, float y) {
    texCoords.x = x;
    texCoords.y = y;
  }

  public void setTexCoords(Vec2 newCoords) {
    setTexCoords(newCoords.x, newCoords.y);
  }

}