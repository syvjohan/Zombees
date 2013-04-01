import java.io.File;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;
import javax.swing.JOptionPane;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
    System.setProperty("org.lwjgl.librarypath", new File("lwjgl\\native\\windows").getAbsolutePath());

    try {
      Application.run();     
      // Gotta catch em' all!
    } catch(Exception e) {
      JOptionPane.showConfirmDialog(null, e.getStackTrace(), e.toString(), JOptionPane.ERROR_MESSAGE);
    }
    
		
	}

}
