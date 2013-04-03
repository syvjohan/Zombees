package graphics;

import utilities.math.Vec2;       
import utilities.math.ClipRect;
import utilities.Texture2D;
import org.lwjgl.opengl.*;
import java.util.Comparator;
import java.util.Arrays;


 

public class SpriteBatch {


  BatchList batch;

  int maxBatchSize;
  int spriteIndex;

  public SpriteBatch(int maxBatchSize) {
    this.maxBatchSize = maxBatchSize;
    batch = new BatchList(maxBatchSize);
  }

  public void begin() {
    batch.clear();
  }

  public void end() {
    renderBatch();
    batch.clear();
  }

  public void draw(Texture2D tex, Vec2 position, ClipRect clip,
   Vec2 origin, float rotation, float scale, int layer) {


    BatchEntry entry = new BatchEntry();
    entry.texture = tex;
    entry.position = position;

    if(clip == null) {
      entry.clip = new ClipRect(0, 0, tex.getWidth(), tex.getHeight());
    } else {
      entry.clip = clip;  
    }
    
    if(origin == null) {
      entry.origin = new Vec2(0,0);
    } else {
      entry.origin = origin;  
    }
    
    entry.rotation = rotation;
    entry.scale = scale;
    entry.layer = layer;

    batch.addBatch(entry);
  }



  private void renderBatch() {

    //batch.sortEntries();
    BatchEntry[] entries = batch.getEntries();


    for(int i = 0; i < batch.getBatchCount(); ++i) {

      // Bind our texture.
      entries[i].texture.bindToGLContext();

      GL11.glLoadIdentity();

      GL11.glPushMatrix();
      
      // Move to screen position.
      GL11.glTranslatef(entries[i].position.x, entries[i].position.y, 0f);
    
      // Rotate and scale
      GL11.glRotatef(entries[i].rotation, 0f, 0f, 1.0f);
      GL11.glScalef(entries[i].scale, entries[i].scale, 1.0f);

      // Move into origin.
      GL11.glTranslatef(
        -entries[i].origin.x,
        -entries[i].origin.y,
        0f
      );

      
      assert entries[i] != null;
      assert entries[i].clip != null;
      assert entries[i].texture != null;

      GL11.glBegin(GL11.GL_QUADS);

        GL11.glTexCoord2f(
          entries[i].clip.x / entries[i].texture.getWidth(),
          entries[i].clip.y / entries[i].texture.getHeight()
        );        
        // Top left corner
        GL11.glVertex2f(0, 0);


        GL11.glTexCoord2f(
          (entries[i].clip.x + entries[i].clip.w) / entries[i].texture.getWidth(),
          entries[i].clip.y / entries[i].texture.getHeight()
        );
        // Top right
        GL11.glVertex2f(entries[i].clip.w, 0f);

        GL11.glTexCoord2f(
          (entries[i].clip.x + entries[i].clip.w) / entries[i].texture.getWidth(),
          (entries[i].clip.y + entries[i].clip.h) / entries[i].texture.getHeight()
        );
        // Bottom right
        GL11.glVertex2f(entries[i].clip.w, entries[i].clip.h);

        GL11.glTexCoord2f(
          entries[i].clip.x / entries[i].texture.getWidth(),
          (entries[i].clip.y + entries[i].clip.h) / entries[i].texture.getHeight()
        );       
        // Bottom left
        GL11.glVertex2f(0, entries[i].clip.h);


      GL11.glEnd();
      GL11.glPopMatrix();

      entries[i].texture.unbindFromGLContext();

    }

  }


}

class BatchList {

  private BatchEntry[] entries;
  private int size;
  private int index;


  public BatchList(int size) {
    entries = new BatchEntry[size];
    this.size = size;
  }

  public int getBatchCount() {
    return index;
  }

  public BatchEntry[] getEntries() {
    return entries;
  }

  public void addBatch(BatchEntry entry) {
    entries[index++] = entry;
  }

  public void sortEntries() {
    Arrays.sort(entries, 0, index, new SpriteComparer());
  }

  public void clear() {
    for(int i = 0; i < index; ++i) {
      entries[i] = null;
    }

    index = 0;
  }

  private class SpriteComparer implements Comparator<BatchEntry> {
    @Override
    public int compare(BatchEntry b1, BatchEntry b2) {
      if(b1.layer == b2.layer) return 0;

      return b1.layer < b2.layer ? -1 : 1;
    }
  }

}

class BatchEntry {

  public Texture2D texture;
  public Vec2 position;
  public ClipRect clip;
  public float rotation;
  public float scale;
  public Vec2 origin;
  public int layer;

}