import javalib.funworld.*;
import javalib.worldimages.*;
import java.awt.Color;

// represents an active word
class ActiveWord implements IWord {
  String text;
  int x;
  int y;

  ActiveWord(String text, int x, int y) {
    this.text = text;
    this.x = x;
    this.y = y;
  }

  //Template:
  /*
   * FIELDS:
   * this.text ... String
   * this.x ... int
   * this.y ... int
   *
   * METHODS:
   * this.moveDown() ... IWord
   * this.atBottom(int bottom) ... boolean
   * this.isActive() ... boolean
   * this.wordStartsWith(String given) ... boolean
   * this.makeActive() ... IWord
   * this.reduce(String letter) ... IWord
   * this.isEmpty() ... boolean
   * this.drawWord(WorldScene scene) ... WorldScene
   *
   * METHODS ON FIELDS:
   * this.text.substring(int start, int end) ... String
   * this.text.startsWith(String prefix) ... boolean
   * this.text.isEmpty() ... boolean
   */

  // Move this word down by 2 pixel
  public IWord moveDown() {
    return new ActiveWord(this.text, this.x, this.y + 2);
  }

  // Is this word lower than the given bottom?
  public boolean atBottom(int bottom) {
    return (this.y >= bottom);
  }

  // Is this word an active word?
  public boolean isActive() {
    return true;
  }

  // Does this word start with the given letter?
  public boolean wordStartsWith(String given) {
    return this.text.startsWith(given);
  }

  // Make this word into an active word
  public IWord makeActive() {
    return this;
  }

  // Reduce the given letter from this word
  public IWord reduce(String letter) {
    if (!this.text.isEmpty() && this.text.startsWith(letter)) {
      String remaining = this.text.substring(1);
      if (remaining.isEmpty()) {
        return new InactiveWord("", this.x, this.y);
      }
      else {
        return new ActiveWord(remaining, this.x, this.y);
      }
    }
    return this;
  }

  // Is this word empty?
  public boolean isEmpty() {
    return this.text.isEmpty();
  }

  // Draw this word on to the given scene
  public WorldScene drawWord(WorldScene scene) {
    if (this.text.isEmpty()) {
      return scene;
    }
    else {
      return scene.placeImageXY(new TextImage(this.text, Color.BLUE), this.x, this.y);
    }
  }
}