import java.util.Random;

// represents a utility class to help generate random words
class Utils {
  Random rand;

  Utils(Random rand) {
    this.rand = rand;
  }
  //Template:
  /*
   * FIELDS:
   * this.rand ... Random
   *
   * METHODS:
   * this.generateWord(int length) ... String
   * this.generateHelper(int remaining, String accum, Random rand) ... String
   *
   * METHODS FOR FIELDS:
   * this.rand.nextInt(int bound) ... int
   */

  // Generates a random word of the specified length.
  String generateWord(int length) {
    return generateHelper(length, "" , this.rand);
  }

  // Recursively builds a word by appending random letters
  String generateHelper(int remaining, String accum, Random rand) {
    String letters = "abcdefghijklmnopqrstuvwxyz";
    if (remaining == 0) {
      // Base case
      return accum;
    }
    int index = rand.nextInt(26);
    // Random index between 0-25
    return generateHelper(remaining - 1, accum + letters.substring(index, index + 1), rand);
  }
}
