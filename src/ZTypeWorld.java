import javalib.funworld.*;
import javalib.worldimages.*;
import java.awt.Color;
import java.util.Random;

// represents a world class to animate a ZType game
class ZTypeWorld extends World implements IZType {
  ILoWord words;
  int tick;
  boolean isGameOver;
  int score;
  int level;
  Random rand;

  ZTypeWorld(ILoWord words, int tick, boolean isGameOver, int score, int level, Random rand) {
    this.words = words;
    this.tick = tick;
    this.isGameOver = isGameOver;
    this.score = score;
    this.level = level;
    this.rand = rand;
  }

  // default world constructor
  ZTypeWorld() {
    this(new MtLoWord(), 0, false, 0, 0, new Random());
  }

  //Template :
  /*
   * FIELDS:
   * this.words ... ILoWord
   * this.tick ... int
   * this.isGameOver ... boolean
   * this.score ... int
   * this.level ... int
   * this.rand ... Random
   *
   * METHODS:
   * this.makeScene() ... WorldScene
   * this.onTick() ... ZTypeWorld
   * this.onKeyEvent(String key) ... ZTypeWorld
   * this.worldEnds() ... WorldEnd
   *
   * METHODS FOR FIELDS:
   * this.words.moveAllDown() ... ILoWord
   * this.words.anyAtBottom(int bottom) ... boolean
   * this.words.containActive() ... boolean
   * this.words.createActive(String given) ... ILoWord
   * this.words.checkAndReduce(String letter) ... ILoWord
   * this.words.reverse() ... ILoWord
   * this.words.addToEnd(IWord current) ... ILoWord
   * this.words.filterOutEmpties() ... ILoWord
   * this.words.draw(WorldScene scene) ... WorldScene
   * this.words.count() ... int
   */

  // Draw the list of words, score, and level on to the background
  public WorldScene makeScene() {
    // draw each words on to the background
    return this.words.draw(new WorldScene(SCREEN_WIDTH, SCREEN_HEIGHT))
            // draw with score information on the bottom
            .placeImageXY(new TextImage("Score: " + this.score, 20, Color.BLACK),
                    (SCREEN_WIDTH - 80), (SCREEN_HEIGHT - 30))
            // draw with level information on the bottom
            .placeImageXY(new TextImage("Level: " + this.level, 20, Color.BLACK),
                    60, (SCREEN_HEIGHT - 30));
  }

  // Move the list of words on the screen and generate new word according to the level
  public ZTypeWorld onTick() {
    // Check whether the game is over
    if (this.isGameOver) {
      return this;
    }
    else if ((this.tick + 1) % (50 - (5 * level)) == 0) {
      return new ZTypeWorld(
              // Generate new word for a random length from 3 to 8 at a speed according to the
              // level at a random x coordinate from 50 to 450, and move down this list of words
              new ConsLoWord(new InactiveWord(new Utils(this.rand)
                      .generateWord(3 + this.rand.nextInt(5)), this.rand.nextInt(400) + 50, 0),
                      this.words.moveAllDown()),
              // update this tick number
              this.tick + 1,
              // update this gameOver to true if any words in the list reached the bottom
              this.words.anyAtBottom(SCREEN_HEIGHT),
              this.score,
              // update the level according to the score
              (this.score / 10),
              this.rand);
    }
    else {
      return new ZTypeWorld(
              // move down this list of words
              this.words.moveAllDown(),
              // update this tick number
              this.tick + 1,
              // update this gameOver to true if any words in the list reached the bottom
              this.words.anyAtBottom(SCREEN_HEIGHT),
              this.score,
              // update the level according to the score
              (this.score / 10),
              this.rand);
    }
  }

  // Delete the letter from the active word when the according key is pressed
  public ZTypeWorld onKeyEvent(String key) {
    // Restart the game if users press space
    if (key.equals(" ")) {
      return new ZTypeWorld();
    }
    // Check whether the game is over or is the key not a single letter 
    if (this.isGameOver  || (key.length() != 1)) {
      return this;
    }
    // find the first word in the reversed list that start with given letter and set it as 
    // active, reverse to make sure it find the word with lower y coordinate
    ILoWord checked = this.words.reverse().createActive(key.toLowerCase());
    // reversed back the list and reduce the letter according to the given key from the 
    // active word in the list and filter out any empty word from the list
    ILoWord updated = checked.reverse().checkAndReduce(key.toLowerCase()).filterOutEmpties();
    // compute the amount of words destroyed 
    int destroyed = words.count() - updated.count();
    // return the updated list of words and add the amount of words that is destroyed to the score
    return new ZTypeWorld(updated, this.tick, this.isGameOver, this.score + destroyed,
            this.level, this.rand);
  }

  // Make scene according to the world state 
  public WorldEnd worldEnds() {
    if (this.isGameOver) {
      // The game is over, display the end scene
      return new WorldEnd(true, new WorldScene(SCREEN_WIDTH, SCREEN_HEIGHT)
              .placeImageXY(new TextImage("Game Over", 40, Color.RED),
                      SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2 - 40)
              .placeImageXY(new TextImage("Score: " + this.score, 30, Color.BLACK),
                      SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2)
              .placeImageXY(new TextImage("Level: " + this.level, 30, Color.BLACK),
                      SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2 + 40));
    }
    else if (this.level == 10) {
      return new WorldEnd(true, new WorldScene(SCREEN_WIDTH, SCREEN_HEIGHT)
              .placeImageXY(new TextImage("Congrats! You Win!", 40, Color.RED),
                      SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2 - 40)
              .placeImageXY(new TextImage("Score: 100", 30, Color.BLACK),
                      SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2)
              .placeImageXY(new TextImage("Level: 10", 30, Color.BLACK),
                      SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2 + 40));
    }
    else {
      // The game is not over, continue to make scene
      return new WorldEnd(false, this.makeScene());
    }
  }
}