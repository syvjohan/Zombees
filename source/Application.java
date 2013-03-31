
import gamestates.*;

import graphics.Renderer;
import org.lwjgl.input.Keyboard;
import eventsystem.EventManager;
import utilities.Logger;

public class Application {

  static Application singleton = null;

  private GameStateManager gameStateManager;
  private boolean isRunning;

  private Application() {

  }

  // Delegates to memberRun()
  public static void run() {

    if(singleton == null) {
      singleton = new Application();
      
      EventManager.initialize();
      Renderer.initialize();


      String logFilePath = singleton.getClass().getProtectionDomain().getCodeSource().getLocation().getPath() ;
      int li = -1;
      for(int i = 0; i < logFilePath.length(); ++i) {
        if(logFilePath.charAt(i) == '/') {
          li = i;
        }
      }

      logFilePath = logFilePath.substring(1, li) + "/game.log";
      System.out.println(logFilePath);
      Logger.initialize(logFilePath);
      Logger.log("Engine Started!");
    }

    singleton.memberRun();

  }

  private void memberRun() {

    // setup time variables
    double now = System.nanoTime(), then = now, delta = 0;

    isRunning = true;
    while(isRunning) {

      now = System.nanoTime();
      delta = (now - then) / 1000000.0;

      // gameStateManager.update(delta);
      EventManager.processEvents();
      
      Renderer.renderScene();

      if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
        isRunning = false;
      }

      then = now;
    }   
  }
}