import javalib.worldimages.*;
import javalib.funworld.*;

// represents a list of word
interface ILoWord {
  // Move all the words in the list down by 2 pixel
  ILoWord moveAllDown();

  // Are there any word at the bottom of the screen?
  boolean anyAtBottom(int bottom);

  // Check whether this list contain any active word
  boolean containActive();

  // Find the first word in the list that start with the given string and set it as active word
  ILoWord createActive(String given);

  // Reverse the order of this list of word
  ILoWord reverse();

  // Helper to add the given word to the end of the list
  ILoWord addToEnd(IWord current);

  // Reduces the words in this list that start with a given letter
  ILoWord checkAndReduce(String letter);

  // Remove any empty words from this list of words
  ILoWord filterOutEmpties();

  // Draw this list of words on to the scene
  WorldScene draw(WorldScene scene);

  // Compute the amount of words in this list of words
  int count();
}