package utilities;
import eventsystem.*;
import java.io.*;
import java.util.Calendar;
import javax.swing.JOptionPane;
import eventsystem.EventType;

public class Logger implements IEventListener {

  private static Logger singleton;

  private String filename;


  public static Logger getInstance() {

    // Logger must be initialized before use. Needs better planning.
    assert singleton != null;

    return singleton;
  }

  public static void initialize(String filename) {
    singleton = new Logger(filename);
  }

  public static void log(String message) {
    singleton.safeLog(message);
  }

  @Override 
  public String getName() {
    return "Logger";
  }

  @Override
  public boolean handleEvent(BaseEvent event) {

    String msg = "Event: " + event.getEventType().getName();
    log(msg);

    return false;
  } 
 

  private Logger(String filename) {
    this.filename = filename;
  }

  private void safeLog(String message) {
     Calendar now = Calendar.getInstance();

    String format = "%d:%02d:%02d:%02d:%02d:%02d: ";
    format = String.format(
      format,
      now.get(Calendar.YEAR),
      now.get(Calendar.MONTH),
      now.get(Calendar.DAY_OF_MONTH),
      now.get(Calendar.HOUR_OF_DAY),
      now.get(Calendar.MINUTE),
      now.get(Calendar.SECOND)
    );

    // Outputs log entries into the sublime console! - TODO REMOVE
    System.out.println(format + message);

    try {
      File file = new File(filename);

      if(!file.exists()) {
        System.out.println("Attempting to create: " + filename);
        file.createNewFile();
      }

      FileWriter fstream = new FileWriter(filename, true);
      fstream.write(format + message + "\r\n");
      fstream.flush();
      fstream.close();
    } catch(Exception e) {
      JOptionPane.showConfirmDialog(null, e.getStackTrace(), e.toString(), JOptionPane.OK_OPTION);
    }   
  }
}