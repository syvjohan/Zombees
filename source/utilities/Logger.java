package utilities;

import eventsystem.IEventListener;
import eventsystem.BaseEvent;
import eventsystem.EventManager;
import java.io.*;
import java.util.Calendar;
import javax.swing.JOptionPane;

public class Logger implements IEventListener {

  private static Logger singleton;

  private String filename;


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

    System.out.println(format + message);
    try {
      FileWriter fstream = new FileWriter(filename);
      fstream.write(format + message);
      fstream.flush();
      fstream.close();
    } catch(Exception e) {
      JOptionPane.showConfirmDialog(null, e.getStackTrace(), "IO ERROR", JOptionPane.OK_OPTION);
    }   
  }
}