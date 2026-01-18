import javalib.funworld.*;
import javalib.worldimages.*;

// represents an empty list of word
class MtLoWord implements ILoWord {
  MtLoWord() {}

  //Template :
  /*
   * METHODS:
   * this.moveAllDown() ... ILoWord
   * this.anyAtBottom(int bottom) ... boolean
   * this.containActive() ... boolean
   * this.createActive(String given) ... ILoWord
   * this.reverse() ... ILoWord
   * this.addToEnd(IWord current) ... ILoWord
   * this.checkAndReduce(String letter) ... ILoWord
   * this.filterOutEmpties() ... ILoWord
   * this.draw(WorldScene scene) ... WorldScene
   * this.count() ... int
   */

  // Move all the words in the list down by 2 pixel
  public ILoWord moveAllDown() {
    return this;
  }

  // Are there any word at the bottom of the screen?
  public boolean anyAtBottom(int bottom) {
    return false;
  }

  // Check whether this list contain any active word
  public boolean containActive() {
    return false;
  }

  // Find the first word in the list that start with the given string and set it as active word
  public ILoWord createActive(String given) {
    return this;
  }

  // Reverse the order of this list of word
  public ILoWord reverse() {
    return this;
  }

  // Helper to add the given word to the end of the list 
  public ILoWord addToEnd(IWord current) {
    //Template for parameter:
    /*
     * FIELDS of parameter:
     * this.text ... String
     * this.x ... int
     * this.y ... int
     *
     * METHODS on parameter:
     * this.moveDown() ... IWord
     * this.atBottom(int bottom) ... boolean
     * this.isActive() ... boolean
     * this.wordStartsWith(String given) ... boolean
     * this.makeActive() ... IWord
     * this.reduce(String letter) ... IWord
     * this.isEmpty() ... boolean
     * this.drawWord(WorldScene scene) ... WorldScene
     */
    return new ConsLoWord(current, this);
  }

  // Reduces the words in this list that start with a given letter
  public ILoWord checkAndReduce(String letter) {
    return this;
  }

  // Remove any empty words from this list of words
  public ILoWord filterOutEmpties() {
    return this;
  }

  // Draw this list of words on to the scene
  public WorldScene draw(WorldScene s) {
    return s;
  }

  // Compute the amount of words in this list of words
  public int count() {
    return 0;
  }
}