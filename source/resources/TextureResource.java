package resources;

import utilities.Logger;
import utilities.Texture2D;
import java.io.IOException;

public class TextureResource extends BaseResourceData {

  private boolean isLoaded;

  private Texture2D resource;
  private String name;

  public TextureResource(String name) {
    this.name = name;
  }

  @Override
  public void load(String filepath) {

    try {
      resource = Texture2D.fromFile(filepath);  
    } catch (IOException ioe) {
      Logger.log("Failed to load textureresource: " + name + 
        " from: " + filepath);
      Logger.log("EXCEPTION: " + ioe.toString());
      Logger.log("Stacktrace: " + ioe.getStackTrace());
    }
    

    isLoaded = true;
  }

  @Override
  public void unload() {
    // THis control needs to be made to make sure we don't leak video memory.
    if(resource.isGLResource()) {
      resource.deleteGLResource();
    }

    // Set resource to null, hoping that the GC will figure it out.
    resource = null;

    isLoaded = false;
    Logger.log("Unloaded textureresource: " + name);
  }

  @Override
  public boolean isLoaded() {
    return isLoaded;
  }
}