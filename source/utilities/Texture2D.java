package utilities;
import java.io.File;
import java.nio.FloatBuffer;
import java.nio.ByteBuffer;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.nio.ByteOrder;
import org.lwjgl.opengl.GL11;

public class Texture2D {

  private FloatBuffer pixelData = null;
  private int glTextureID = -1;
  private int width;
  private int height;

  // Registers this texture width the gl context.
  public void makeGLResource() {
    glTextureID = GL11.glGenTextures();

    GL11.glTexImage2D(
      GL11.GL_TEXTURE_2D,
      0,
      GL11.GL_RGBA,
      width,
      height,
      GL11.GL_RGBA,
      GL11.GL_FLOAT,
      pixelData
    );

    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
    GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0); 
  }

  // Binds this texture as the active texture in the gl context.
  public void bindToGLContext() {
    GL11.glBindTexture(GL11.GL_TEXTURE_2D, glTexutreID);
  }

  public FloatBuffer getPixelData() {
    return pixelData;
  }

  public int getGLTextureID() {
    return glTextureID;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  /* Static method that allows us to load a texture2D from
   * a file. Can throw IOException if the file does not exist,
   * or if its corrupt. */
  public static Texture2D fromFile(String filename) throws IOException {
    // Can throw IOException
    BufferedImage bi = ImageIO.read(new File(filename));

    // Used to determine the same pixel on the next row from the current.
    int scanline = bi.getWidth();

    // We expect width * height elements, with 4 bytes per element(RGBA) and 4 elements for each color.
    ByteBuffer byteBuffer = ByteBuffer.allocateDirect(bi.getWidth() * bi.getHeight() * 16);

    // Make sure the expected byteorder is correct.
    byteBuffer.order(ByteOrder.nativeOrder());

    // Convert to FloatBuffer
    FloatBuffer fb = byteBuffer.asFloatBuffer();

    // Retrieve image color data.
    int[] imgRGB = bi.getRGB(0, 0, bi.getWidth(), bi.getHeight(), null, 0, scanline);

    // Loop through each pixel element and convert to floats.
    for(int i = 0; i < imgRGB.length; ++i) {

      // Our pixel at imgRGB[i] looks like: A255-R255-G255-B255
      // We will perform bitshifts to extract color values into 0-255 range,
      // then divide by 255f in order to get into the normal float color range of 0.0-1.0

      fb.put(((imgRGB[i] >> 16) & 0xff) / 255f); // Red
      fb.put(((imgRGB[i] >> 8) & 0xff) / 255f); // Green
      fb.put(((imgRGB[i] >> 0) & 0xff) / 255f); // Blue
      fb.put(((imgRGB[i] >> 24) & 0xff) / 255f); // Alpha
    }

    fb.flip();

    return new Texture2D(bi.getWidth(), bi.getHeight(), fb);
  }

  // Private constructor used to create a Texture2D from a floatbuffer.
  private Texture2D(int width, int height, FloatBuffer pixelData) {
    this.pixelData = pixelData;
    this.width = width;
    this.height = height;
  }



}