# Music Collection Manager

A JavaSwing application for cataloguing, grading, and sorting vinyl records, CDs, and cassettes.

You can view a demo of the program and test cases at: https://youtu.be/7W-CENAy4A0

## Features

- **Catalogue music collections** - Add, edit, and delete vinyl records, CDs, and cassettes
- **Goldmine grading scale** - Industry-standard condition grading (M, NM, EX, VG+, VG, G+, G, F, P)
- **Multiple sort options** - Sort by Artist, Title, Year, Condition, or Format
- **Search/filter** - Find items across all fields in real-time
- **Format-specific details** - Track vinyl size/speed, CD track count/booklet, cassette tape type/length

## Design Patterns

### Factory Pattern
The `ItemFactory` class centralizes object creation for all collection item types. This provides:
- Simplified, consistent item creation through `createRecord()`, `createCD()`, `createCassette()` methods
- Easy extensibility - new formats can be added by extending the factory and adding a subclass
- Single point of change for item creation logic

**Key files:**
- `factory/ItemFactory.java`
- `model/CollectionItem.java` (base class)
- `model/Record.java`, `model/CD.java`, `model/Cassette.java` (subclasses)

### Strategy Pattern
The `SortStrategy` interface enables interchangeable sorting algorithms without if/else chains. This provides:
- Encapsulated sorting behavior in separate classes
- Runtime switching between sort methods via dropdown
- Easy addition of new sort criteria by implementing the interface

**Key files:**
- `strategy/SortStrategy.java` (interface)
- `strategy/SortByArtist.java`, `strategy/SortByTitle.java`, `strategy/SortByYear.java`, `strategy/SortByCondition.java`, `strategy/SortByMediaType.java` (implementations)

## Project Structure

```
src/
├── Main.java                    # application entry point
├── model/
│   ├── CollectionItem.java      # abstract base class
│   ├── Record.java              # vinyl record class
│   ├── CD.java                  # compact disc class
│   └── Cassette.java            # cassette tape class
├── factory/
│   └── ItemFactory.java         # factory pattern implementation
├── strategy/
│   ├── SortStrategy.java        # strategy interface
│   ├── SortByArtist.java        # sort by artist name
│   ├── SortByTitle.java         # sort by album title
│   ├── SortByYear.java          # sort by release year
│   ├── SortByCondition.java     # sort by goldmine grade
│   └── SortByMediaType.java     # sort by format type
├── gui/
│   ├── CollectionManagerGUI.java # main application window
│   ├── AddItemDialog.java       # dialog for adding items
│   └── EditItemDialog.java      # dialog for editing items
└── test/
    └── CollectionManagerTest.java # comprehensive test suite
```

## Running in IntelliJ IDEA

### Opening the Project
1. Open IntelliJ IDEA
2. Select **File > Open**
3. Navigate to the `CollectionManager` folder and click **Open**
4. IntelliJ will automatically detect the project structure

### Running the Main Application
1. In the Project panel, navigate to `src/Main.java`
2. Right-click on `Main.java` and select **Run 'Main.main()'**
3. Alternatively, open `Main.java` and click the green play button in the gutter next to the `main` method

### Running the Test Suite
1. In the Project panel, navigate to `src/test/CollectionManagerTest.java`
2. Right-click on `CollectionManagerTest.java` and select **Run 'CollectionManagerTest.main()'**
3. The console will display test results, then launch the GUI with demo data

The test suite runs 23 tests covering:
- Factory pattern (item creation)
- Strategy pattern (all sort implementations)
- Model classes (properties, getters, setters)
- Integration (factory + strategy together, edge cases)

After tests complete, the GUI launches with demo data for visual testing.

## Goldmine Grading Scale (for reference purposes)

| Grade | Meaning |
|-------|---------|
| M | Mint - perfect, unplayed condition |
| NM | Near Mint - nearly perfect, minimal signs of handling |
| EX | Excellent - shows some signs of play but well cared for |
| VG+ | Very Good Plus - surface noise evident but does not overpower music |
| VG | Very Good - groove wear and light scratches audible |
| G+ | Good Plus - significant wear but still plays through |
| G | Good - plays through without skipping but with significant noise |
| F | Fair - significant damage, plays with difficulty |
| P | Poor - barely playable, severe damage |
