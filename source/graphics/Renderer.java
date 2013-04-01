package graphics;

import org.lwjgl.opengl.*;
import org.lwjgl.*;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import utilities.Settings;

public class Renderer {

  private static Renderer singleton;

  private Renderer()
  {
    try {
      Display.setDisplayMode(new DisplayMode(Settings.kAppWidth, Settings.kAppHeight));
      Display.create();
    } catch (LWJGLException e) {
      final JPanel panel = new JPanel();
      JOptionPane.showMessageDialog(panel, "Unable to initialize display mode", "Error", JOptionPane.ERROR_MESSAGE);
      System.exit(0);
    }

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


    // Now with gourad shading!

    // Draw quad
    GL11.glBegin(GL11.GL_QUADS);
      GL11.glColor3f(1f, 0f, 0f);
      GL11.glVertex2f(100,100);
      GL11.glColor3f(0f, 1.0f, 0.0f);
      GL11.glVertex2f(100+200, 100);
      GL11.glColor3f(0f, 0.0f, 1.0f);
      GL11.glVertex2f(100+200, 100+200);
      GL11.glColor3f(1f, 0.0f, 1.0f);
      GL11.glVertex2f(100, 100+200);
    GL11.glEnd();

    Display.update();
  }

  public static void terminate() {
    Display.destroy();
  }
}