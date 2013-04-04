
package graphics.providers;

import utilities.*;
import utilities.math.*;
import java.awt.Dimension;


// An interface that any graphics wrapper should implement to serve the engine.
// This is done as a means to reach a better level of abstraction between the engine
// and rendering systems implementation.
public interface IGraphicsProvider {

	public abstract void setResolution(Dimension resolution);
	public abstract Dimension getResolution();

	public abstract void toggleFullScreen(boolean value);
	public abstract boolean isFullScreen();

	// Method should clear the screen and anything else needed to start a new drawing batch.
	public abstract void clearSurface(Color4 clearColor);	


	public abstract void renderTexturedQuad(Texture2D tex, Vec2 position, ClipRect rect,
		Vec2 origin, float rotation, float scale);

}

