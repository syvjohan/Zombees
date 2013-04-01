package graphics;

import org.lwjgl.opengl.*;
import org.lwjgl.*;
import javax.swing.JOptionPane;
import utilities.Settings;
import utilities.Texture2D;
import java.io.IOException;

public class Renderer {

  private static Renderer singleton;

  Texture2D testImage;


  private Renderer()
  {
    try {
      Display.setDisplayMode(new DisplayMode(Settings.kAppWidth, Settings.kAppHeight));
      Display.create();
    } catch (LWJGLException e) {
      JOptionPane.showMessageDialog(null, "Unable to initialize display mode", "Error", JOptionPane.ERROR_MESSAGE);
      System.exit(0);
    }
  
    // Enable textures
    GL11.glEnable(GL11.GL_TEXTURE_2D);

    try {
      // Load our image.
      testImage = Texture2D.fromFile("derp.png");  
    } catch(IOException ioe) {

      // TODO : Do something useful about missing textures..
    }
    
    // Make the texture a GL resource.
    testImage.makeGLResource();

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
    testImage.bindToGLContext();

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
    testImage.unbindFromGLContext();

    Display.update();
  }

  public static void terminate() {
    Display.destroy();
  }
}