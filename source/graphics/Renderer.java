package graphics;

import org.lwjgl.opengl.*;
import org.lwjgl.*;
import javax.swing.JOptionPane;
import utilities.Settings;
import utilities.Texture2D;
import java.io.IOException;
import utilities.math.Vec2;

public class Renderer {

  private static Renderer singleton;

  Texture2D testImage;
  Texture2D catImage;
  SpriteBatch batch;

  float testRot = 0f;
  float testScale = 1.0f;
  float scaleMod = 0.0005f;
  float posX = 0;
  float posY = 0;
  float dirX = 1;
  float dirY = 1;

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
      testImage = Texture2D.fromFile("test.png");  
      catImage = Texture2D.fromFile("derp.png");
    } catch(IOException ioe) {

      // TODO : Do something useful about missing textures..
    }
    
    batch = new SpriteBatch(100);

    // Make the texture a GL resource.
    testImage.makeGLResource();
    catImage.makeGLResource();

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

    batch.begin();

    testRot += 0.01f;
    testScale += scaleMod;
    if(testScale > 2f || testScale < 0.5f) {
      scaleMod *= -1;
    }

    batch.draw(
      testImage,
      new Vec2(250, 320),
      null,
      new Vec2(testImage.getWidth() / 2, testImage.getHeight() / 2),
      testRot,
      1f,
      0
    );   

    batch.draw(
      testImage,
      new Vec2(550, 320),
      null,
      new Vec2(testImage.getWidth() / 2, testImage.getHeight() / 2),
      0f,
      testScale,
      0
    );


    if(posX > 800 || posX < 0) {
      dirX *= -1;
    }

    if(posY > 640 || posY < 0 ) {
      dirY *= -1;
    }

    posX += dirX * 0.05f;
    posY += dirY * 0.05f;

    batch.draw(
      catImage,
      new Vec2(posX, posY),
      null,
      new Vec2(catImage.getWidth() / 2, catImage.getHeight() / 2),
      testRot * 0.75f,
      testScale * 0.25f,
      0
    );       


    batch.end();

    Display.update();
  }

  public static void terminate() {
    Display.destroy();
  }
}