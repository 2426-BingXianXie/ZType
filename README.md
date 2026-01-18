# ZType - Fast-Paced Typing Game

An adrenaline-pumping typing game where players intercept falling words by typing them accurately before they reach the bottom of the screen. Features progressive difficulty scaling, real-time word generation, and score tracking for an engaging typing challenge experience.

---

## Overview

ZType is a typing game inspired by classic shoot-'em-up mechanics, challenging players to type falling words before they reach the bottom of the screen. The game combines fast reflexes with typing accuracy, providing an engaging way to improve typing skills while having fun.

### Key Features

- **Dynamic Word Generation** - Procedurally generated random words (3-8 letters)
- **Progressive Difficulty** - 10 escalating levels with increasing speed
- **Active Word System** - Highlight and track the current word being typed
- **Real-time Feedback** - Immediate visual response to typing accuracy
- **Score Tracking** - Points awarded for each completed word
- **Level Progression** - Automatic level-up based on score milestones

### Game Objectives

- **Primary Goal**: Type falling words before they reach the bottom
- **Challenge**: Achieve higher levels by maintaining accuracy and speed
- **High Score**: Complete words efficiently to maximize points
- **Win Condition**: Reach level 10 without losing

---

## Game Mechanics

### Core Components

#### IWord Interface
Represents words falling down the screen:

**Active Words** (Blue)
- Currently being typed by the player
- Only one active word at a time
- Letters removed as player types correctly

**Inactive Words** (Black)
- Waiting to be selected
- Become active when player types first letter
- Multiple inactive words can exist simultaneously

#### ILoWord (List of Words)
Collection of words on screen:
- `MtLoWord` - Empty list (base case)
- `ConsLoWord` - Non-empty list (recursive structure)

#### ZTypeWorld
Main game engine managing:
- **Word List**: All active/inactive words on screen
- **Game State**: Playing, Game Over, or Won
- **Score**: Total words completed
- **Level**: Current difficulty (0-10)
- **Tick Counter**: Frame-based timing for word generation

### Word Lifecycle

```
1. Word Generation
   ├── Random length (3-8 letters)
   ├── Random x-position (50-450 pixels)
   ├── Spawns at top (y = 0)
   └── Added to inactive word list

2. Word Activation
   ├── Player types first letter
   ├── Search for matching inactive word
   ├── Convert to active word (turns blue)
   └── Track for subsequent letters

3. Letter Reduction
   ├── Player types next letter
   ├── Remove letter from active word
   ├── Continue until word empty
   └── Award point when complete

4. Word Removal
   ├── Completed (empty) → Filtered out
   ├── Reached bottom → Game Over
   └── Filter runs after each keystroke
```

---

## Technology Stack

### Core Technologies
- **Java 11** - Primary programming language
- **JavaLib FunWorld** - Functional world-based animation
- **JavaLib WorldImages** - Graphics rendering
- **JUnit/Tester** - Testing framework

### Development Tools
- **Eclipse/IntelliJ IDEA** - IDE
- **Git** - Version control
- **Random** - Pseudorandom number generation

### Key Libraries
```java
import javalib.funworld.*;      // Functional world framework
import javalib.worldimages.*;   // Image rendering
import tester.*;                // Testing framework
import java.awt.Color;          // Color management
import java.util.Random;        // Random generation
```

---

## System Architecture

### Class Hierarchy

```
┌──────────────────┐
│   ZTypeWorld     │
│  (extends World) │
├──────────────────┤
│ - words: ILoWord │───┐
│ - tick: int      │   │  Contains
│ - isGameOver     │   │
│ - score: int     │   │
│ - level: int     │   │
│ - rand: Random   │   │
└──────────────────┘   │
                       │
                       ▼
           ┌────────────────┐
           │    ILoWord     │
           │  (interface)   │
           ├────────────────┤
           │ + moveAllDown()│
           │ + containActive│
           │ + createActive │
           │ + checkReduce  │
           └────────────────┘
                ▲
                │
        ┌───────┴────────┐
        │                │
┌───────────────┐  ┌────────────────┐
│   MtLoWord    │  │  ConsLoWord    │
├───────────────┤  ├────────────────┤
│ (empty list)  │  │ - first: IWord │
└───────────────┘  │ - rest: ILoWord│
                   └────────────────┘
                            │
                            │ Contains
                            ▼
                   ┌────────────────┐
                   │     IWord      │
                   │  (interface)   │
                   ├────────────────┤
                   │ + moveDown()   │
                   │ + reduce()     │
                   │ + isActive()   │
                   └────────────────┘
                            ▲
                            │
                    ┌───────┴────────┐
                    │                │
            ┌───────────────┐  ┌──────────────┐
            │ ActiveWord    │  │InactiveWord  │
            ├───────────────┤  ├──────────────┤
            │ - text: String│  │- text: String│
            │ - x, y: int   │  │- x, y: int   │
            │ (draws blue)  │  │(draws black) │
            └───────────────┘  └──────────────┘
```

### Data Flow

```
User Types Letter → onKeyEvent → Find/Activate Word → Reduce Letter → 
Filter Empties → Update Score → Check Win/Loss → Render Scene
```

---

## Installation & Setup

### Prerequisites

1. **Java Development Kit (JDK) 11+**
   ```bash
   java -version  # Verify installation
   ```

2. **JavaLib FunWorld Library**
    - Download from course resources
    - Add to project classpath

3. **IDE** (Eclipse or IntelliJ IDEA)
    - Configure Java project
    - Import JavaLib library

### Setup Instructions

1. **Clone or Download Project**
   ```bash
   # If using Git
   git clone <repository-url>
   cd ztype-game
   ```

2. **Import to IDE**
    - **Eclipse**: File → Import → Existing Projects
    - **IntelliJ**: File → Open → Select project directory

3. **Add JavaLib to Build Path**
    - Right-click project → Build Path → Add External JARs
    - Select `javalib.jar` file

4. **Compile and Run**
   ```java
   // Run the testBigBang method in ExamplesZType class
   public boolean testBigBang(Tester t) {
       ZTypeWorld world = new ZTypeWorld();
       return world.bigBang(500, 600, 0.05);
   }
   ```

---

## How to Play

### Starting the Game

```java
ZTypeWorld game = new ZTypeWorld();
game.bigBang(500, 600, 0.05);  // width, height, tick rate (20 FPS)
```

### Controls

| Action | Control | Description |
|--------|---------|-------------|
| **Type Letters** | A-Z Keys | Type letters to match falling words |
| **Restart Game** | Spacebar | Reset to level 0 with empty board |
| **Quit Game** | ESC or Close Window | Exit the game |

### Objective

1. **Type Quickly**: Match falling words before they reach bottom
2. **Stay Accurate**: Typos don't help; only matching letters count
3. **Progress Through Levels**: Reach level 10 to win the game
4. **Maximize Score**: Each completed word = 1 point

### Game States

#### Playing
- Words fall from top of screen
- Type letters to activate and reduce words
- Score increases with each completed word
- Level increases every 10 points

#### Game Over
- **Trigger**: Any word reaches bottom of screen
- **Display**: "Game Over" in red with final score/level
- **Action**: Press spacebar to restart

#### Victory
- **Trigger**: Reach level 10 (100 points)
- **Display**: "Congrats! You Win!" with perfect score
- **Achievement**: Maximum difficulty completed

### Strategy Tips

1. **Prioritize Lower Words**: Type words closest to bottom first
2. **Plan Ahead**: Anticipate which word to type next
3. **Stay Calm**: Don't panic-type; accuracy matters
4. **Use Full Screen**: Watch all words simultaneously
5. **Practice**: Typing speed improves with repetition

---

## Implementation Details

### Word Generation System

**Random Word Algorithm**:

```java
class Utils {
    Random rand;
    
    String generateWord(int length) {
        return generateHelper(length, "", this.rand);
    }
    
    String generateHelper(int remaining, String accum, Random rand) {
        String letters = "abcdefghijklmnopqrstuvwxyz";
        
        if (remaining == 0) {
            return accum;  // Base case
        }
        
        int index = rand.nextInt(26);  // Random letter index
        return generateHelper(
            remaining - 1, 
            accum + letters.substring(index, index + 1), 
            rand
        );
    }
}
```

**Features**:
- Recursive generation for functional programming style
- Uniform distribution across all 26 letters
- Configurable length (3-8 characters)
- Deterministic with seed for testing

### Active Word Selection

**Algorithm**: First-Match Strategy with Reverse Search

```java
public ILoWord createActive(String given) {
    if (this.containActive()) {
        return this;  // Already have active word
    }
    
    // Search from bottom of screen (reversed list)
    ILoWord reversed = this.reverse();
    ILoWord activated = reversed.findAndActivateFirst(given);
    return activated.reverse();  // Restore original order
}
```

**Why Reverse?**
- Words lower on screen are more urgent
- Reverse → First match = Lowest word
- Fair prioritization mechanism

### Progressive Difficulty System

**Level Progression**:

| Level | Score Range | Speed (frames/word) | Word Length |
|-------|-------------|---------------------|-------------|
| 0 | 0-9 | 50 | 3-7 letters |
| 1 | 10-19 | 45 | 3-7 letters |
| 2 | 20-29 | 40 | 3-7 letters |
| 5 | 50-59 | 25 | 3-7 letters |
| 9 | 90-99 | 5 | 3-7 letters |
| 10 | 100+ | **WIN** | N/A |

**Speed Formula**:
```java
int framesPerWord = 50 - (5 * level);
```

At 20 FPS (0.05 tick rate):
- Level 0: 2.5 seconds per word
- Level 5: 1.25 seconds per word
- Level 9: 0.25 seconds per word

### Game Loop (onTick)

```java
public ZTypeWorld onTick() {
    if (this.isGameOver) {
        return this;  // Freeze game
    }
    
    // Check if it's time to spawn new word
    if ((this.tick + 1) % (50 - (5 * level)) == 0) {
        // Generate new word
        int length = 3 + this.rand.nextInt(5);  // 3-7 letters
        int xPos = this.rand.nextInt(400) + 50;  // x: 50-450
        IWord newWord = new InactiveWord(
            new Utils(this.rand).generateWord(length), 
            xPos, 
            0  // Spawn at top
        );
        
        return new ZTypeWorld(
            new ConsLoWord(newWord, this.words.moveAllDown()),
            this.tick + 1,
            this.words.anyAtBottom(SCREEN_HEIGHT),  // Check game over
            this.score,
            this.score / 10,  // Auto-level
            this.rand
        );
    } else {
        // Just move words down
        return new ZTypeWorld(
            this.words.moveAllDown(),
            this.tick + 1,
            this.words.anyAtBottom(SCREEN_HEIGHT),
            this.score,
            this.score / 10,
            this.rand
        );
    }
}
```

### Key Event Handling

```java
public ZTypeWorld onKeyEvent(String key) {
    // Restart on spacebar
    if (key.equals(" ")) {
        return new ZTypeWorld();
    }
    
    // Ignore if game over or invalid key
    if (this.isGameOver || key.length() != 1) {
        return this;
    }
    
    String letter = key.toLowerCase();
    
    // Process: Reverse → Activate → Reduce → Filter → Reverse
    ILoWord processed = this.words
        .reverse()                    // Prioritize lower words
        .createActive(letter)         // Activate first match
        .reverse()                    // Restore order
        .checkAndReduce(letter)       // Remove letter from active
        .filterOutEmpties();          // Remove completed words
    
    // Calculate words destroyed (score increment)
    int destroyed = this.words.count() - processed.count();
    
    return new ZTypeWorld(
        processed,
        this.tick,
        this.isGameOver,
        this.score + destroyed,
        this.level,
        this.rand
    );
}
```

### Visual Rendering

```java
public WorldScene makeScene() {
    WorldScene base = new WorldScene(SCREEN_WIDTH, SCREEN_HEIGHT);
    
    // Draw all words
    WorldScene withWords = this.words.draw(base);
    
    // Add score (bottom-right)
    WorldScene withScore = withWords.placeImageXY(
        new TextImage("Score: " + this.score, 20, Color.BLACK),
        SCREEN_WIDTH - 80,
        SCREEN_HEIGHT - 30
    );
    
    // Add level (bottom-left)
    return withScore.placeImageXY(
        new TextImage("Level: " + this.level, 20, Color.BLACK),
        60,
        SCREEN_HEIGHT - 30
    );
}
```

**Color Coding**:
- **Blue Text**: Active word (currently typing)
- **Black Text**: Inactive words (waiting)
- **Red Text**: "Game Over" message
- **Green Text**: "You Win!" message

---

## Game Design

### Design Principles

#### 1. Functional Programming Style
- Immutable data structures
- Pure functions (no side effects)
- Recursive operations
- List-based data flow

#### 2. Progressive Challenge
- Gradual difficulty increase
- Clear level milestones
- Achievable but challenging
- Satisfying victory condition

#### 3. Immediate Feedback
- Visual color change on activation
- Letters disappear as typed
- Real-time score updates
- Clear win/loss states

#### 4. Fair Gameplay
- Bottom-priority word selection
- Consistent spawn patterns
- Predictable word generation
- No unfair difficulty spikes

### Balance Considerations

**Word Speed**:
- Too slow: Boring, no challenge
- Too fast: Frustrating, impossible
- Sweet spot: 1-2 seconds per word (mid-levels)

**Word Length**:
- 3 letters: Too easy
- 8+ letters: Too hard
- 3-7 letters: Balanced variety

**Spawn Rate**:
- Tied to level for scaling
- Prevents screen overcrowding
- Allows strategic planning

---

## Testing

### Test Coverage

Comprehensive test suite with **30+ test methods**:

#### World Tests
- `testMakeScene()` - Scene rendering
- `testOnTick()` - Game loop timing
- `testOnKeyEvent()` - Input handling
- `testWorldEnds()` - End conditions

#### List Operations
- `testMoveAllDown()` - Word movement
- `testAnyAtBottom()` - Boundary detection
- `testContainActive()` - Active word checking
- `testCreateActive()` - Word activation
- `testFilterOutEmpties()` - Completion removal

#### Word Operations
- `testMoveDown()` - Individual word movement
- `testReduce()` - Letter removal
- `testIsActive()` - State checking
- `testDrawWord()` - Rendering

#### Word Generation
- `testGenerateWord()` - Random word creation
- `testGenerateHelper()` - Recursive generation

### Running Tests

```java
// In ExamplesZType class
public static void main(String[] args) {
    Tester.run(new ExamplesZType());
}
```

**Test Examples**:

```java
// Test word activation
boolean testCreateActive(Tester t) {
    ILoWord singleM = new ConsLoWord(
        new InactiveWord("moon", 300, 300), 
        new MtLoWord()
    );
    
    return t.checkExpect(
        singleM.createActive("m"),
        new ConsLoWord(new ActiveWord("moon", 300, 300), new MtLoWord())
    );
}

// Test letter reduction
boolean testReduce(Tester t) {
    IWord activeA = new ActiveWord("apple", 100, 100);
    
    return t.checkExpect(
        activeA.reduce("a"),
        new ActiveWord("pple", 100, 100)
    );
}
```
