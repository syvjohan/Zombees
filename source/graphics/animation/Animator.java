package graphics.animation;

import java.util.HashMap;

// Class used to encapsulate animation
// Used as an aggregate object to create and play animations.
public class Animator {

  private HashMap<String, Sequence> animations;

  private Sequence currentSequence;
  private Sequence nextSequence;
  private int repeatCurrent, repeatNext;
  private boolean isCurrentContinous, isNextContinuous;

  public Animator() {
    animations = new HashMap<String, Sequence>();
  }

  // Plays the requested animation immediatly without waiting
  // for the current one to finnish, repeats [repeat] times.
  // if -1 is passed as repeat value, animation will be continuous.
  public void playAnimationDirect(String name, int repeat) {
    
    // Put the new animation on the queue..
    enqueueAnimation(name, repeat);

    // and start it right away!
    startNextAnimation();
  }

  // Plays the requested animation once the current one is completed.
  // Repeats [repeat] times. If -1 is passed as repeat value,
  // animation will be continuous.
  public void enqueueAnimation(String name, int repeat) {
    nextSequence = animations.get(name);

    // Make sure we're not requesting animations that don't exist.
    assert nextSequence != null;

    repeatNext = repeatCurrent;
    if(repeatNext == -1) {
      isNextContinuous = true;
    }
  }

  // Updates the animator and its sequences
  public void update(double deltaTime) {

    // If the current animation is done, and there is another animation
    // waiting, we will skip any repeats and play it.
    if(nextSequence != null) {
      if(currentSequence == null || currentSequence.isDone()) {
        startNextAnimation();
      }
    }

    // If there is a sequence playing right now we'll advance it.
    if(currentSequence != null) {

      // Advance the current animation
      currentSequence.tick(deltaTime);

      // Check if the sequence is done, if it is,
      // we'll reset it if there are any repeats left.
      if(currentSequence.isDone()) {
        if(repeatCurrent > 0 || isCurrentContinous) {
          currentSequence.reset();
          --repeatCurrent;
        }
      }

    } 
  }

  // Creates and adds a new sequence to the animator,
  // animTime is the intended time for the whole animation.
  public void addAnimation(String name, double animTime) {
    animations.put(name, new Sequence());
  }

  // Adds a frame to the parameter sequence, will get more parameters..
  public void addFrame(String animationName) {

  }

  // Removes a frame from the paramter sequence.
  public void removeFrame(String animationName, int frameIndex) {

  }

  // Starts the next animation. Asserts that there should be one.
  private void startNextAnimation() {
    
    // This method should never be called if there is no animation on the queue.
    assert nextSequence != null;

    if(currentSequence != null) {
      currentSequence.reset();
    }

    currentSequence = nextSequence;
    nextSequence.reset();

    isCurrentContinous = isNextContinuous;
    isCurrentContinous = false;
    repeatCurrent = repeatNext;
    repeatNext = 0;
  }

  
}


// Private class used by animator.
class Sequence {

  private int numFrames;
  private int currentFrame;
  private double runTime;
  private double currentTime;
  private double timePerFrame;
  private boolean isDone;

  public Sequence() {
    numFrames = 0;    
    runTime = 0;
    timePerFrame = 0;
    
    reset();
  }

  public boolean isDone() {
    return isDone;
  }

  public void reset() {
    currentFrame = 0;
    currentTime = 0;
    isDone = false;
  }

  public void tick(double delta) {
    currentTime += delta;

    if(currentTime >= runTime) {
      // We should never need to look at the number of frames for determining
      // When to reset the animation..
      assert currentFrame < numFrames;

      currentTime = 0;
      currentFrame = 0;
    } else if(currentTime >= timePerFrame) {
      ++currentFrame;
    }
  }

}

class ClipRect {
  public float x, y, w, h;
}