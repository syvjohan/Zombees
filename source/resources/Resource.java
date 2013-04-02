package resources;

public class Resource {
  
  private boolean isLoaded;
  private ResourceType type;

  // Location of the resource.
  private String filepath;

  // Should match the entry into the map/dictionary used by the resourcemanager.
  private String name;

  // Holds the underlying data type through an abstract polymorphic interface.
  private BaseResourceData data;

  public Resource(ResourceType type, String name, String filepath) {
    this.type = type;
    this.name = name;
    this.filepath = filepath;

    // Create the empty shell for the underlying resource.
    makeResourceShell();
  }

  // Loads the resource using the underlying resource type.
  public void loadResource() {
    // Make sure the resource shell has been created.
    assert data != null;

    data.load(filepath);

    isLoaded = true;
  }

  // Unloads a resource using the underlying resource implementation.
  public void unloadResource() {
    // Make sure the resource shell exists.
    assert data != null && data.isLoaded();

    data.unload();
    isLoaded = false;
  }

  public ResourceType getType() {
    return type;
  }
  public String getName() {
    return name;
  }

  public String getFilePath() {
    return filepath;
  }

  public boolean isLoaded() {
    return isLoaded;
  }

  public BaseResourceData getData() {
    // We should never be attempting to retrieve data
    // that does not exist!
    assert data != null;

    return data;
  }

  // Creates the correct empty resource shell depending on type.
  private void makeResourceShell() {
    switch(type) {
      case Texture:
      data = new TextureResource(name);
      break;
      case SoundEffect:

      break;
      case Music:

      break;
    }
  }

}