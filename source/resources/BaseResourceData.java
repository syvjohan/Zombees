package resources;


// Class defines basic behaviour for resource types.
// Intention is that different implementations of this base
// will be able to make correct decisions about how to load
// and unload certain resources, and nobody else will need to care.
public abstract class BaseResourceData {

  // For loading and unloading the actual resource type.
  public abstract void load(String filepath);
  public abstract void unload();

  // should only be true when load has been called, but unload has not.
  public abstract boolean isLoaded();

}