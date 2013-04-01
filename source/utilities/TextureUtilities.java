package utilities;

import java.io.File;
import java.nio.FloatBuffer;
import java.nio.ByteBuffer;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.nio.ByteOrder;


public class TextureUtilities {


  public static Texture2D loadGLCompatibleTexture(String filename) throws IOException {

    // Can throw IOException
    BufferedImage src = ImageIO.read(new File(filename));
    
    
  }


  public static FloatBuffer getImageDataBuffer(BufferedImage bi) {

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
    System.out.println(fb.toString());
    return fb;
  }


}