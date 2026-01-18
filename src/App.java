import tester.Tester;

// Main application class to run the ZType game
public class App {
  public static void main(String[] args) {
    ZTypeWorld world = new ZTypeWorld();
    world.bigBang(500, 600, 0.05);
  }
}