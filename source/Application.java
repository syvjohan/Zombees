
import gamestates.*;

import graphics.Renderer;
import org.lwjgl.input.Keyboard;
import eventsystem.*;
import eventsystem.events.*;
import utilities.Logger;

public class Application implements IEventListener {

  static Application singleton = null;

  private GameStateManager gameStateManager;
  private boolean isRunning;

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

    isRunning = true;
    while(isRunning) {

      now = System.nanoTime();
      delta = (now - then) / 1000000.0;

      gameStateManager.update(delta);
      EventManager.processEvents();
      
      Renderer.renderScene();

      if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
        EventManager.pushEventDirect(new WindowCloseRequestEvent());
      }

      then = now;
    }   
  }

  private void setupLogger() {
    String logFilePath = singleton.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
    
    int li = -1;
    for(int i = 0; i < logFilePath.length(); ++i) {
      if(logFilePath.charAt(i) == '/') {
        li = i;
      }
    }

    logFilePath = logFilePath.substring(1, li) + "/game.log";
    System.out.println(logFilePath);
    Logger.initialize(logFilePath);
  }
}