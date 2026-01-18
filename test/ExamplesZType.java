import tester.*;
import java.awt.Color;
import javalib.funworld.*;
import javalib.worldimages.*;
import java.util.Random;

public class ExamplesZType {

  IWord activeA = new ActiveWord("apple", 100, 100);
  IWord activeZ = new ActiveWord("zoo", 200, 200);
  IWord inactiveM = new InactiveWord("moon", 300, 300);
  IWord inactiveA = new InactiveWord("ant", 400, 400);
  IWord bottomWord = new InactiveWord("end", 500, 600);
  IWord emptyActive = new ActiveWord("", 0, 0);
  IWord edgeWord = new ActiveWord("test", 0, 598);
  IWord mixedCaseWord = new InactiveWord("Apple", 100, 100);
  IWord partialWord = new ActiveWord("a", 0, 0);
  IWord unicodeWord = new InactiveWord("café", 100, 100);

  // Lists of words
  ILoWord mt = new MtLoWord();
  ILoWord singleA = new ConsLoWord(this.activeA, this.mt);
  ILoWord singleM = new ConsLoWord(this.inactiveM, this.mt);
  ILoWord mixed = new ConsLoWord(this.activeZ, new ConsLoWord(this.inactiveA, this.mt));
  ILoWord withEmpty = new ConsLoWord(this.emptyActive, new ConsLoWord(this.inactiveM, this.mt));
  ILoWord moved = new ConsLoWord(this.edgeWord, this.mt).moveAllDown();
  ILoWord bottom = new ConsLoWord(this.bottomWord, this.mt);
  ILoWord multiMatch = new ConsLoWord(this.inactiveA,
          new ConsLoWord(new InactiveWord("ark", 200, 200), this.mt));
  ILoWord multiMatch2 = new ConsLoWord(new InactiveWord("ark", 200, 200),
          new ConsLoWord(this.activeA, this.mt));
  ILoWord list = new ConsLoWord(this.mixedCaseWord, this.mt);
  ILoWord reduced = new ConsLoWord(this.partialWord, this.mt);

  WorldScene scene = new WorldScene(500, 600);

  Random r1 = new Random(500);
  Random r2 = new Random(500);

  ZTypeWorld emptyWorld = new ZTypeWorld();
  ZTypeWorld nonEmptyWorld1 = new ZTypeWorld(this.singleA, 2, false, 3, 0, r1);
  ZTypeWorld nonEmptyWorld2 = new ZTypeWorld(this.singleM, 2, false, 3, 0, r1);
  ZTypeWorld gameOverWorld = new ZTypeWorld(this.bottom, 2, true, 3, 0, r1);
  ZTypeWorld winWorld = new ZTypeWorld(this.reduced, 187, false, 99, 9, r1);
  ZTypeWorld tickWorld1 = new ZTypeWorld(this.singleM, 49, false, 3, 0, r1);
  ZTypeWorld tickWorld2 = new ZTypeWorld(this.singleM, 30, false, 3, 0, r1);
  ZTypeWorld keyWorld1 = new ZTypeWorld(this.singleM, 30, false, 5, 0, r1);
  ZTypeWorld keyWorld2 = new ZTypeWorld(this.multiMatch2, 30, false, 3, 0, r1);
  ZTypeWorld keyWorld3 = new ZTypeWorld(this.reduced, 30, false, 9, 0, r1);

  Utils u = new Utils(new Random());
  Utils u0 = new Utils(new Random(0));
  Utils u1 = new Utils(new Random(1));
  Utils u2 = new Utils(new Random(2));
  Utils u3 = new Utils(new Random(3));
  Utils u4 = new Utils(new Random(4));

  // Test the method makeScene in the class ZTypeWorld
  boolean testMakeScene(Tester t) {
    // Test if this default world make an empty scene
    return t.checkExpect(this.emptyWorld.makeScene(),
            this.scene.placeImageXY(new TextImage("Score: 0", 20, Color.BLACK), 420, 570)
                    .placeImageXY(new TextImage("Level: 0", 20, Color.BLACK), 60, 570))
            // Test if this world with a list with active word make a text image on scene
            && t.checkExpect(this.nonEmptyWorld1.makeScene(),
            this.scene.placeImageXY(new TextImage("apple", Color.BLUE), 100, 100)
                    .placeImageXY(new TextImage("Score: 3", 20, Color.BLACK), 420, 570)
                    .placeImageXY(new TextImage("Level: 0", 20, Color.BLACK), 60, 570))
            // Test if this world with a list with inactive make a text image on scene
            && t.checkExpect(this.nonEmptyWorld2.makeScene(),
            this.scene.placeImageXY(new TextImage("moon", Color.BLACK), 300, 300)
                    .placeImageXY(new TextImage("Score: 3", 20, Color.BLACK), 420, 570)
                    .placeImageXY(new TextImage("Level: 0", 20, Color.BLACK), 60, 570));
  }

  //Test the method onTick in the class ZTypeWorld
  boolean testOnTick(Tester t) {
    // Test if this method return itself when the game is over
    return t.checkExpect(this.gameOverWorld.onTick(), this.gameOverWorld)
            // Test if this method generate a new word, move down, and add tick as expected
            && t.checkExpect(this.tickWorld1.onTick(),
            new ZTypeWorld(new ConsLoWord(new InactiveWord(
                    new Utils(this.r2).generateWord(3 + this.r2.nextInt(5)),
                    r2.nextInt(400) + 50, 0), this.singleM.moveAllDown()),
                    50, false, 3, 0, r1))
            // Test if this method update the level as expected
            && t.checkExpect(this.keyWorld3.onKeyEvent("a").onTick(),
            new ZTypeWorld(this.mt, 31, false, 10, 1, r1))
            // Test if this method move down and add tick as expected
            && t.checkExpect(this.tickWorld2.onTick(),
            new ZTypeWorld(this.singleM.moveAllDown(), 31, false, 3, 0, r1));
  }

  //Test the method onKeyEvent in the class ZTypeWorld
  boolean testOnKeyEvent(Tester t) {
    // Test if this method return itself when the game is over
    return t.checkExpect(this.gameOverWorld.onKeyEvent(""), this.gameOverWorld)
            // Test if this method restart a new game when the user pressed space
            && t.checkExpect(this.keyWorld1.onKeyEvent(" "), new ZTypeWorld())
            // Test if this method return itself when the user pressed a key that
            // is not a character
            && t.checkExpect(this.keyWorld1.onKeyEvent("Shift"), this.keyWorld1)
            // Test if this method return an empty world when run on an empty world
            && t.checkExpect(this.emptyWorld.onKeyEvent("a"), this.emptyWorld)
            // Test if this method check and reduce the word correctly when enter
            // a capitalized letter
            && t.checkExpect(this.keyWorld1.onKeyEvent("M"),
            new ZTypeWorld(new ConsLoWord(new ActiveWord("oon", 300, 300), mt),
                    30, false, 5, 0, r1))
            // Test if this method check and reduce the word correctly on a world
            // with a list of inactive word
            && t.checkExpect(this.keyWorld1.onKeyEvent("m"),
            new ZTypeWorld(new ConsLoWord(new ActiveWord("oon", 300, 300), mt),
                    30, false, 5, 0, r1))
            // Test if this method didn't check and reduce the word when the user
            // pressed a key different from the word
            && t.checkExpect(this.keyWorld1.onKeyEvent("a"), this.keyWorld1)
            // Test if this method reduce the active word correctly on a world with
            // a list of active word and inactive word
            && t.checkExpect(this.keyWorld2.onKeyEvent("a"),
            new ZTypeWorld(new ConsLoWord(new InactiveWord("ark", 200, 200),
                    new ConsLoWord(new ActiveWord("pple", 100, 100), this.mt)), 30, false, 3, 0, r1))
            // Test if this method add score when cleared a word
            && t.checkExpect(this.keyWorld3.onKeyEvent("a"),
            new ZTypeWorld(this.mt, 30, false, 10, 0, r1));
  }

  //Test the method worldEnds in the class ZTypeWorld
  boolean testWorldEnds(Tester t) {
    // Test if this world continue to draw game scene when game is not over
    return t.checkExpect(this.nonEmptyWorld1.worldEnds(),
            new WorldEnd(false, this.scene.placeImageXY(new TextImage("apple", Color.BLUE), 100, 100)
                    .placeImageXY(new TextImage("Score: 3", 20, Color.BLACK), 420, 570)
                    .placeImageXY(new TextImage("Level: 0", 20, Color.BLACK), 60, 570)))
            // Test if this world draw the end scene when game is over
            && t.checkExpect(this.winWorld.onKeyEvent("a").onTick().worldEnds(),
            new WorldEnd(true,
                    this.scene.placeImageXY(new TextImage("Congrats! You Win!",
                                    40, Color.RED), 250, 260)
                            .placeImageXY(new TextImage("Score: 100", 30, Color.BLACK), 250, 300)
                            .placeImageXY(new TextImage("Level: 10", 30, Color.BLACK), 250, 340)))
            // Test if this world draw the end scene when the user win the game
            && t.checkExpect(this.gameOverWorld.worldEnds(),
            new WorldEnd(true, this.scene.placeImageXY(new TextImage("Game Over", 40, Color.RED),
                            250, 260).placeImageXY(new TextImage("Score: 3", 30, Color.BLACK), 250, 300)
                    .placeImageXY(new TextImage("Level: 0", 30, Color.BLACK), 250, 340)));
  }

  // Test the method moveAllDown in the interface ILoWord
  boolean testMoveAllDown(Tester t) {
    // Tests if an empty list remains unchanged
    return t.checkExpect(this.mt.moveAllDown(), this.mt)
            // Tests if a single active word moves downward correctly
            && t.checkExpect(this.singleA.moveAllDown(),
            new ConsLoWord(new ActiveWord("apple", 100, 102), this.mt))
            // Tests if a mixed list of active and inactive words moves downward correctly
            && t.checkExpect(this.mixed.moveAllDown(),
            new ConsLoWord(new ActiveWord("zoo", 200, 202),
                    new ConsLoWord(new InactiveWord("ant", 400, 402), this.mt)))
            // Tests if a word at the edge of the screen is detected at the bottom after moving
            && t.checkExpect(this.moved.anyAtBottom(600), true);
  }

  // Test the method anyAtBottom in the interface ILoWord
  boolean testAnyAtBottom(Tester t) {
    // Tests if an empty list correctly returns false
    return t.checkExpect(this.mt.anyAtBottom(600), false)
            // Tests if a list containing a word at the bottom correctly returns true
            && t.checkExpect(this.bottom.anyAtBottom(600), true)
            // Tests if a list without words at the bottom correctly returns false
            && t.checkExpect(this.singleA.anyAtBottom(600), false);
  }

  // Test the method containActive in the interface ILoWord
  boolean testContainActive(Tester t) {
    // Tests if an empty list correctly returns false
    return t.checkExpect(this.mt.containActive(), false)
            // Tests if a list with only inactive words correctly returns false
            && t.checkExpect(this.singleM.containActive(), false)
            // Tests if a list containing at least one active word correctly returns true
            && t.checkExpect(this.mixed.containActive(), true);
  }

  // Test the method createActive in the interface ILoWord
  boolean testCreateActive(Tester t) {
    // Tests if an empty list remains unchanged
    return t.checkExpect(this.mt.createActive("a"), mt)
            // Tests if a matching inactive word becomes active when the first letter matches
            && t.checkExpect(this.singleM.createActive("m"),
            new ConsLoWord(new ActiveWord("moon", 300, 300), mt))
            // Tests if a list with an already active word remains unchanged
            && t.checkExpect(this.mixed.createActive("z"), this.mixed)
            // Tests if only the first matching inactive word is converted to active
            && t.checkExpect(this.multiMatch.createActive("a"),
            new ConsLoWord(new ActiveWord("ant", 400, 400),
                    new ConsLoWord(new InactiveWord("ark", 200, 200), this.mt)));
  }

  // Test the method reverse in the interface ILoWord
  boolean testReverse(Tester t) {
    // Tests if reversing an empty list returns an empty list
    return t.checkExpect(this.mt.reverse(), this.mt)
            // Tests if reversing a single-word list returns the same list
            && t.checkExpect(this.singleA.reverse(), this.singleA)
            // Tests if reversing a two-word list correctly swaps their order
            && t.checkExpect(new ConsLoWord(this.activeA,
                    new ConsLoWord(this.inactiveM, this.mt)).reverse(),
            new ConsLoWord(this.inactiveM, new ConsLoWord(this.activeA, this.mt)));
  }

  // Test the method addToEnd in the interface ILoWord
  boolean testAddToEnd(Tester t) {
    // Tests adding an active word to an empty list
    return t.checkExpect(this.mt.addToEnd(this.activeA), new ConsLoWord(this.activeA, this.mt))
            // Tests adding an inactive word to an empty list
            && t.checkExpect(this.mt.addToEnd(this.inactiveA), new ConsLoWord(this.inactiveA, this.mt))
            // Tests adding an active word to a non-empty list
            && t.checkExpect(new ConsLoWord(this.activeZ, this.mt).addToEnd(this.activeA),
            new ConsLoWord(this.activeZ, new ConsLoWord(this.activeA, this.mt)))
            // Tests adding an inactive word to a non-empty list
            && t.checkExpect(new ConsLoWord(this.activeZ, this.mt).addToEnd(this.inactiveA),
            new ConsLoWord(this.activeZ, new ConsLoWord(this.inactiveA, this.mt)));
  }

  // Test the method checkAndReduce in the interface ILoWord
  boolean testCheckAndReduce(Tester t) {
    // Tests if an empty list remains unchanged
    return t.checkExpect(this.mt.checkAndReduce("a"), this.mt)
            // Tests if the first matching letter is removed from an active word
            && t.checkExpect(this.singleA.checkAndReduce("a"),
            new ConsLoWord(new ActiveWord("pple", 100, 100), this.mt))
            // Tests if an inactive word remains unchanged
            && t.checkExpect(this.singleM.checkAndReduce("m"), this.singleM);
  }

  // Test the method filterOutEmpties in the interface ILoWord
  boolean testFilterOutEmpties(Tester t) {
    // Tests if an empty list remains unchanged.
    return t.checkExpect(this.mt.filterOutEmpties(), this.mt)
            // Tests if an empty active word is removed from a list
            && t.checkExpect(this.withEmpty.filterOutEmpties(), this.singleM)
            // Tests if a list without empty words remains unchanged
            && t.checkExpect(this.mixed.filterOutEmpties(), this.mixed)
            // Tests if a word reduced to empty is removed from the list
            && t.checkExpect(this.reduced.checkAndReduce("a").filterOutEmpties(), this.mt);
  }

  // Test the method draw in the interface ILoWord
  boolean testDraw(Tester t) {
    // Tests if an empty list does not modify the scene
    return t.checkExpect(this.mt.draw(this.scene), this.scene)
            // Tests if a single active word is drawn correctly
            && t.checkExpect(this.singleA.draw(this.scene),
            this.scene.placeImageXY(new TextImage("apple", Color.BLUE), 100, 100))
            // Tests if multiple words (active and inactive) are drawn correctly
            && t.checkExpect(this.mixed.draw(this.scene),
            this.scene.placeImageXY(new TextImage("zoo", Color.BLUE), 200, 200)
                    .placeImageXY(new TextImage("ant", Color.BLACK), 400, 400));
  }

  // Test the method count in the interface ILoWord
  boolean testCount(Tester t) {
    // Tests if an empty list returns a count of 0
    return t.checkExpect(this.mt.count(), 0)
            // Tests if a single-word list returns a count of 1
            && t.checkExpect(this.singleA.count(), 1)
            // Tests if a list with multiple words returns the correct count
            && t.checkExpect(this.mixed.count(), 2);
  }

  // Test the method moveDown in the interface IWord
  boolean testMoveDown(Tester t) {
    // Tests if an active word moves downward by 2 pixels
    return t.checkExpect(this.activeA.moveDown(), new ActiveWord("apple", 100, 102))
            // Tests if an inactive word moves downward by 2 pixels
            && t.checkExpect(this.inactiveM.moveDown(), new InactiveWord("moon", 300, 302))
            // Tests if an empty active word still moves downward
            && t.checkExpect(this.emptyActive.moveDown(), new ActiveWord("", 0, 2));
  }

  // Test the method atBottom in the interface IWord
  boolean testAtBottom(Tester t) {
    // Tests if a word not at the bottom correctly returns false
    return t.checkExpect(this.activeA.atBottom(600), false)
            // Tests if a word exactly at the bottom correctly returns true
            && t.checkExpect(this.bottomWord.atBottom(600), true)
            // Tests if a word below the bottom correctly returns true
            && t.checkExpect(new ActiveWord("test", 0, 601).atBottom(600), true);
  }

  // Test the method isActive in the interface IWord
  boolean testIsActive(Tester t) {
    // Tests if an active word correctly returns true
    return t.checkExpect(this.activeA.isActive(), true)
            // Tests if an inactive word correctly returns false
            && t.checkExpect(this.inactiveM.isActive(), false)
            // Tests if an empty active word still returns true
            && t.checkExpect(this.emptyActive.isActive(), true);
  }

  // Test the method wordStartsWith in the interface IWord
  boolean testwordStartsWith(Tester t) {
    // Tests if the first letter of an active word matches correctly
    return t.checkExpect(this.activeA.wordStartsWith("a"), true)
            // Tests if the first letter of an inactive word matches correctly
            && t.checkExpect(this.inactiveM.wordStartsWith("m"), true)
            // Tests if a non-matching letter correctly returns false
            && t.checkExpect(this.activeZ.wordStartsWith("x"), false)
            // Tests if a Unicode character in the first position matches correctly
            && t.checkExpect(this.unicodeWord.wordStartsWith("c"), true)
            // Tests if a Unicode character not in the first position correctly returns false
            && t.checkExpect(this.unicodeWord.wordStartsWith("é"), false);
  }

  // Test the method makeActive in the interface IWord
  boolean testMakeActive(Tester t) {
    // Tests if an inactive word is correctly converted to an active word
    return t.checkExpect(this.inactiveM.makeActive(), new ActiveWord("moon", 300, 300))
            // Tests if another inactive word is converted correctly
            && t.checkExpect(this.inactiveA.makeActive(), new ActiveWord("ant", 400, 400))
            // Tests if an already active word remains unchanged
            && t.checkExpect(this.activeA.makeActive(), this.activeA);
  }

  // Test the method reduce in the interface IWord
  boolean testReduce(Tester t) {
    // Tests if the first matching letter is removed from an active word
    return t.checkExpect(this.activeA.reduce("a"), new ActiveWord("pple", 100, 100))
            // Tests if reducing an inactive word has no effect
            && t.checkExpect(this.inactiveM.reduce("m"), this.inactiveM)
            // Tests if reducing an empty word remains unchanged
            && t.checkExpect(this.emptyActive.reduce("a"), this.emptyActive);
  }

  // Test the method isEmpty in the interface IWord
  boolean testIsEmpty(Tester t) {
    // Tests if a non-empty active word correctly returns false
    return t.checkExpect(this.activeA.isEmpty(), false)
            // Tests if an empty active word correctly returns true
            && t.checkExpect(this.emptyActive.isEmpty(), true)
            // Tests if an empty inactive word correctly returns true
            && t.checkExpect(new InactiveWord("", 0, 0).isEmpty(), true);
  }

  // Test the method drawWord in the interface IWord
  boolean testDrawWord(Tester t) {
    // Tests if an active word is drawn in blue at the correct position
    return t.checkExpect(this.activeA.drawWord(this.scene),
            this.scene.placeImageXY(new TextImage("apple", Color.BLUE), 100, 100))
            // Tests if an inactive word is drawn in black at the correct position
            && t.checkExpect(this.inactiveM.drawWord(this.scene),
            this.scene.placeImageXY(new TextImage("moon", Color.BLACK), 300, 300))
            // Tests if an empty active word is not drawn
            && t.checkExpect(this.emptyActive.drawWord(this.scene), this.scene);
  }

  //Test the method generateWord in the Utils class
  boolean testGenerateWord(Tester t) {
    // Generate 1-letter word with seed 1
    return t.checkExpect(this.u1.generateWord(1), "r")
            // Generate 2-letter word with seed 2
            && t.checkExpect(this.u2.generateWord(2), "sg")
            // Generate 3-letter word with seed 3
            && t.checkExpect(this.u3.generateWord(3), "smm");
  }

  //Test the method generateHelper in the Utils class
  boolean testGenerateHelper(Tester t) {
    return
            // Base case: remaining = 0 returns accum
            t.checkExpect(this.u.generateHelper(0, "", new Random()), "")
                    // Test with remaining = 1 and seed 1
                    && t.checkExpect(this.u1.generateHelper(1, "", new Random(1)), "r")
                    // Test with remaining = 3 and seed 4
                    && t.checkExpect(this.u4.generateHelper(3, "", new Random(4)), "qsn")
                    // Test with remaining = 2 and seed 0
                    && t.checkExpect(this.u0.generateHelper(2, "b", new Random(0)), "bss");
  }

  // Tests if the game world starts successfully with the correct settings
  boolean testBigBang(Tester t) {
    int worldWidth = 500;
    int worldHeight = 600;
    double tickRate = 0.05;
    return this.emptyWorld.bigBang(worldWidth, worldHeight, tickRate);
  }
}