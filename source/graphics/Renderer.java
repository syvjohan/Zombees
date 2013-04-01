package graphics;

import org.lwjgl.opengl.*;
import org.lwjgl.*;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import utilities.Settings;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import java.awt.Color;
import utilities.TextureUtilities;

public class Renderer {

  private static Renderer singleton;

  int testImg;

  // Retrieve inner data of an image 
  private FloatBuffer getImageBuffer(BufferedImage bi) {
    int size = bi.getWidth() * bi.getHeight();
    int w = bi.getWidth();
    int h = bi.getHeight(); 

    int[] imgData = bi.getRGB(0, 0, w, h, null, 0, w);
    float[] bufferData = new float[size * 4];
    int bdIndex = 0;
    for(int i = 0; i < imgData.length; ++i) {
      Color c = new Color(imgData[i]);

      bufferData[bdIndex++] = c.getRed() / 255f;
      bufferData[bdIndex++] = c.getGreen() / 255f;
      bufferData[bdIndex++] = c.getBlue() / 255f;
      bufferData[bdIndex++] = c.getAlpha() / 255f;
    }

    FloatBuffer fb = BufferUtils.createFloatBuffer(size * 4);
    fb.put(bufferData);
    fb.flip();

    System.out.println(fb.order());
    return fb;
  }


  // Displays the flow of loading an image for OpenGL through javas API.
  private void testCreateImage() {
    BufferedImage bi = null;

    try {
      bi = ImageIO.read(new File("test.png"));  
    } catch(Exception e) {
      JOptionPane.showMessageDialog(null, e.getStackTrace(), "Error", JOptionPane.ERROR_MESSAGE);
    }
  
    testImg = GL11.glGenTextures();
    GL11.glBindTexture(GL11.GL_TEXTURE_2D, testImg);

    FloatBuffer imgData = TextureUtilities.getImageDataBuffer(bi);
    //FloatBuffer imgData = getImageBuffer(bi);

    //assert false;

    GL11.glTexImage2D(
      GL11.GL_TEXTURE_2D,
      0,
      GL11.GL_RGBA,
      bi.getWidth(),
      bi.getHeight(),
      0,
      GL11.GL_RGBA,
      GL11.GL_FLOAT,
      imgData
    ); 

    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
    GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
  }

  private Renderer()
  {
    try {
      Display.setDisplayMode(new DisplayMode(Settings.kAppWidth, Settings.kAppHeight));
      Display.create();
    } catch (LWJGLException e) {
      JOptionPane.showMessageDialog(null, "Unable to initialize display mode", "Error", JOptionPane.ERROR_MESSAGE);
      System.exit(0);
    }
  
    GL11.glEnable(GL11.GL_TEXTURE_2D);
    // Experimenting..
    testCreateImage();

    // Initialize orthographic matrix of size 800*600 with a clipping distance between 1 and -1
    GL11.glMatrixMode(GL11.GL_PROJECTION);
    GL11.glLoadIdentity();
    GL11.glOrtho(0, 800, 600, 0, 0, -1);
    GL11.glMatrixMode(GL11.GL_MODELVIEW);
  }

  public static void initialize()
  {
    if (singleton == null)
      singleton = new Renderer();
  }

  public static void renderScene()
  {
    singleton.memberRender();
  }

  private void memberRender() {

    // Clear screen and depth buffer
    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);


    // Bind texture for sampling..
    GL11.glBindTexture(GL11.GL_TEXTURE_2D, testImg);

    // Draw quad
    GL11.glBegin(GL11.GL_QUADS);
      GL11.glTexCoord2f(0, 0);
      GL11.glVertex2f(20,20);
      
      GL11.glTexCoord2f(1, 0);
      GL11.glVertex2f(20+401, 20);

      GL11.glTexCoord2f(1, 1);
      GL11.glVertex2f(20+401, 20+400);

      GL11.glTexCoord2f(0, 1);
      GL11.glVertex2f(20, 20+400);
    GL11.glEnd();

    // Unbind when done
    GL11.glBindTexture(GL11.GL_TEXTURE_2D, testImg);

    Display.update();
  }

  public static void terminate() {
    Display.destroy();
  }
}