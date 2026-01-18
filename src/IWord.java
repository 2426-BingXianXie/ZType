import javalib.funworld.*;
import javalib.worldimages.*;

// represents a word
interface IWord {
  // Move this word down by 2 pixel
  IWord moveDown();

  // Is this word lower than the given bottom?
  boolean atBottom(int bottom);

  // Is this word an active word?
  boolean isActive();

  // Does this word start with the given letter?
  boolean wordStartsWith(String given);

  // Make this word into an active word
  IWord makeActive();

  // Reduce the given letter from this word
  IWord reduce(String letter);

  // Is this word empty?
  boolean isEmpty();

  // Draw this word on to the given scene
  WorldScene drawWord(WorldScene scene);
}