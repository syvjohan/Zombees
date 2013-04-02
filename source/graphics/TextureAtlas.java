package graphics;

import utilities.Texture2D;

/* Basic idea for texture atlas: 
 * split atlas into small cells.
 * Occupy cells with textures, find regions of cells
 * large enough to hold a particular texture, thus saving space.
 * A potential idea would be to keep track of sprite clips,
 * and save the clips separatly, which would allow for even more
 * efficient packaging, but would require more processing for lookups. */

/* TODO : Find a way to smoothly let sprites use their local texture
 * coordinates and let the rendering system transform them into atlas coordinates. */

public class TextureAtlas {
  
  private Texture2D atlasTexture;
  private int currentDimension;
  private boolean[][] cells;
  private final int kCellSize = 16;

  public TextureAtlas(int initialDimension) {
    currentDimension = 0;
    resize(initialDimension);
  }

  public Texture2D getAtlasTexture() {
    return atlasTexture;
  }

  // Resizes the texture atlas to a new dimension.
  public void resize(int newDimension) {
    // Ensure compatible dimensions.
    if(!isPowerOfTwo(newDimension)) {
      newDimension = roundToPowerOfTwo(newDimension);
    }

    // Define our cells array.
    cells = new boolean[newDimension / kCellSize][newDimension / kCellSize];

    if(atlasTexture != null) {
      Texture2D tmp = atlasTexture;
      atlasTexture = new Texture2D(newDimension, newDimension);


      // Copy the previous atlas to the new one starting in the topleft corner.
      // This way previous texture coordinates will remain accurate.
      // TODO : Handle downsizing the atlas.
      atlasTexture.copyFrom(tmp, 0, 0, 0, 0, tmp.getWidth(), tmp.getHeight());
    } else {
      // If this is the first atlas we create, we simply set a new one.
      atlasTexture = new Texture2D(newDimension, newDimension);
    }

    currentDimension = newDimension;
  }

  // Adds a texture to the atlas.
  public void addTexture(Texture2D texture) {

  }

  // Verifies that a number is a power of two.
  private boolean isPowerOfTwo(int number) { 
    if(number < 0) return false;

    while(number % 2 == 0 && number != 0) {
      number = number >> 1;
    }

    // The smallest valid number of two is 1. (2^0)
    return number == 1;
  }

  // Finds the closest power of two rounded UP from num.
  private int roundToPowerOfTwo(int num) {
    int lp = 1;
    while(lp < num) {
      lp *= 2;
    }

    // lp will be the smallest number such that lp > num and lp is a power of two.
    return lp;
  }

}