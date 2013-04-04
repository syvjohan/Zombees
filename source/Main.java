import java.io.File;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;
import javax.swing.JOptionPane;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
	String natives = new Main().getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
    
    int i = natives.length() - 1;
    for( ; i >= 0; --i) {
    	if( natives.charAt(i) == '/' ) {
    		break;
    	}
    }

    natives = natives.substring(0, i);
    System.out.println(natives);
    natives += "/lwjgl/native/linux/";

    System.setProperty("org.lwjgl.librarypath", new File(natives).getAbsolutePath());

    try {
      Application.run();     
      // Gotta catch em' all!
    } catch(Exception e) {
      JOptionPane.showConfirmDialog(null, e.getStackTrace(), e.toString(), JOptionPane.ERROR_MESSAGE);
    }
    
		
	}

}
