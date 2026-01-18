import javalib.funworld.*;
import javalib.worldimages.*;

// represents a non-empty list of words
class ConsLoWord implements ILoWord {
  IWord first;
  ILoWord rest;

  ConsLoWord(IWord first, ILoWord rest) {
    this.first = first;
    this.rest = rest;
  }

  //Template :
  /*
   * FIELDS:
   * this.first ... IWord
   * this.rest ... ILoWord
   *
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
   *
   * METHODS ON FIELDS:
   * this.first.moveDown() ... IWord
   * this.first.atBottom(int bottom) ... boolean
   * this.first.isActive() ... boolean
   * this.first.wordStartsWith(String given) ... boolean
   * this.first.makeActive() ... IWord
   * this.first.reduce(String letter) ... IWord
   * this.first.isEmpty() ... boolean
   * this.first.drawWord(WorldScene scene) ... WorldScene
   * this.rest.moveAllDown() ... ILoWord
   * this.rest.anyAtBottom(int bottom) ... boolean
   * this.rest.containActive() ... boolean
   * this.rest.createActive(String given) ... ILoWord
   * this.rest.reverse() ... ILoWord
   * this.rest.addToEnd(IWord current) ... ILoWord
   * this.rest.checkAndReduce(String letter) ... ILoWord
   * this.rest.filterOutEmpties() ... ILoWord
   * this.rest.draw(WorldScene scene) ... WorldScene
   * this.rest.count() ... int
   *
   */

  // Move all the words in the list down by 2 pixel
  public ILoWord moveAllDown() {
    return new ConsLoWord(this.first.moveDown(), this.rest.moveAllDown());
  }

  // Are there any word at the bottom of the screen?
  public boolean anyAtBottom(int bottom) {
    return this.first.atBottom(bottom) || this.rest.anyAtBottom(bottom);
  }

  // Check whether this list contain any active word
  public boolean containActive() {
    return this.first.isActive() || this.rest.containActive();
  }

  // Find the first word in the list that start with the given string and set it as active word
  public ILoWord createActive(String given) {
    // check does this list already contain any active word
    if (this.containActive()) {
      return this;
    }
    // Create the first word the first word that start with the given letter active
    if (this.first.wordStartsWith(given)) {
      return new ConsLoWord(this.first.makeActive(), this.rest);
    }
    else {
      return new ConsLoWord(this.first, this.rest.createActive(given));
    }
  }

  // Reverse the order of this list of word
  public ILoWord reverse() {
    return this.rest.reverse().addToEnd(this.first);
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
    return new ConsLoWord(this.first, this.rest.addToEnd(current));
  }

  // Reduces the words in this list that start with a given letter
  public ILoWord checkAndReduce(String letter) {
    return new ConsLoWord(this.first.reduce(letter), this.rest.checkAndReduce(letter));
  }

  // Remove any empty words from this list of words
  public ILoWord filterOutEmpties() {
    if (this.first.isEmpty()) {
      return this.rest.filterOutEmpties();
    }
    else {
      return new ConsLoWord(this.first, this.rest.filterOutEmpties());
    }
  }

  // Draw this list of words on to the scene
  public WorldScene draw(WorldScene scene) {
    return this.rest.draw(this.first.drawWord(scene));
  }

  // Compute the amount of words in this list of words
  public int count() {
    return 1 + this.rest.count();
  }
}