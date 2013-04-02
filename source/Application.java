
import gamestates.*;

import graphics.Renderer;
import graphics.SpriteManager;
import resources.ResourceManager;
import resources.ResourceType;
import org.lwjgl.opengl.Display;
import org.lwjgl.input.Keyboard;
import eventsystem.*;
import eventsystem.events.*;
import utilities.Logger;

import graphics.TestSprite;

public class Application implements IEventListener {

  static Application singleton = null;

  private GameStateManager gameStateManager;
  private boolean isRunning;
  private int loops;

  private Application() {
    gameStateManager = GameStateManager.getInstance();
  }

  // Initializes the game-systems, and then runs memberRun to run the gameloop.
  public static void run() {

    if(singleton == null) {
      singleton = new Application();
      
      EventManager.initialize();

      singleton.setupLogger();

      // Make it easier to find the latest session in the log file.
      Logger.log("----------Engine Started!---------");

      // Initialize sprites/textures systems.
      SpriteManager.initialize();

      // Initialize resource systems.
      ResourceManager.initialize();

      // Create a test resource..
      ResourceManager.createResource(ResourceType.Texture, "DerpCat", "derp.png");

      // Random test with a sprite..
      SpriteManager.registerSprite(new TestSprite());



      EventManager.registerEventType(EventType.kWildCard);
      EventManager.registerEventListener(EventType.kWildCard, Logger.getInstance());
      EventManager.registerEventType(WindowCloseRequestEvent.kEventType);
      EventManager.registerEventListener(WindowCloseRequestEvent.kEventType, singleton);  


      Renderer.initialize();
    }

    singleton.memberRun();

  }

  @Override
  public String getName() {
    return "Application";
  }

  @Override
  public boolean handleEvent(BaseEvent event) {

    if(event.getEventType().getID() == WindowCloseRequestEvent.kEventType.getID()) {
      isRunning = false;
    }


    return false;
  }

  private void memberRun() {

    // setup time variables
    double now = System.nanoTime(), then = now, delta = 0;

    loops = 0;
    double frameTimer = 0;
    isRunning = true;
    while(isRunning) {

      now = System.nanoTime();

      // We get time in a given amount of nanoseconds, we convert to seconds here.
      delta = (now - then) / 1000000000.0;

      gameStateManager.update(delta);
      EventManager.processEvents();
      
      Renderer.renderScene();
      
      // Wannabe fps meter.
      ++loops;
      frameTimer += delta;
      if(frameTimer >= 1.0) {
        Display.setTitle("Zombees!   FPS: " + loops);
        loops = 0;
        frameTimer = 0.0;
      }

      // Fire a close window request event if the user presses escape or clicks the X!
      if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) || Display.isCloseRequested()) {
        EventManager.pushEventDirect(new WindowCloseRequestEvent());
      }

      then = now;
    }   
  }

  private void setupLogger() {
    // Obtain the absolute path of the class' execution instance.
    String logFilePath = singleton.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
    
    // Find the last fwd-slash so we can filter the filename from the path.
    int li = -1;
    for(int i = 0; i < logFilePath.length(); ++i) {
      if(logFilePath.charAt(i) == '/') {
        li = i;
      }
    }

    // Append our logfiles name to the path.
    logFilePath = logFilePath.substring(1, li) + "/game.log";

    // Announce the path for debugging purposes.
    System.out.println(logFilePath);

    Logger.initialize(logFilePath);
  }
}