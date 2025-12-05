/*
 * CollectionManagerTest.java
 * purpose: comprehensive test case showcasing all functionality
 *          tests factory pattern, strategy pattern, and gui integration
 * author: phin
 */

package test;

import factory.ItemFactory;
import model.CollectionItem;
import model.Record;
import model.CD;
import model.Cassette;
import strategy.*;
import gui.CollectionManagerGUI;

import javax.swing.SwingUtilities;
import java.util.ArrayList;
import java.util.List;

// comprehensive test class that demonstrates all features
public class CollectionManagerTest {

    // counters for test results
    private static int testsPassed = 0;
    private static int testsFailed = 0;

    public static void main(String[] args) {
        System.out.println("===========================================");
        System.out.println("  music collection manager - test suite");
        System.out.println("===========================================\n");

        // run all test categories
        testFactoryPattern();
        testStrategyPattern();
        testModelClasses();
        testIntegration();

        // print summary
        System.out.println("\n===========================================");
        System.out.println("  test summary");
        System.out.println("===========================================");
        System.out.println("passed: " + testsPassed);
        System.out.println("failed: " + testsFailed);
        System.out.println("total:  " + (testsPassed + testsFailed));

        if (testsFailed == 0) {
            System.out.println("\nall tests passed! the application is working correctly.");
        } else {
            System.out.println("\nsome tests failed. please review the output above.");
        }

        // offer to launch the gui for visual testing
        System.out.println("\n-------------------------------------------");
        System.out.println("launching gui for visual demonstration...");
        launchGUIDemo();
    }

    // tests the factory pattern implementation
    private static void testFactoryPattern() {
        System.out.println("--- testing factory pattern ---\n");

        // test 1: create a vinyl record using factory
        System.out.print("test 1: create record via factory... ");
        Record record = ItemFactory.createRecord("Abbey Road", "The Beatles", 1969, "Excellent", "12\"", "33");
        assertCondition(record != null && record.getTitle().equals("Abbey Road"), "record created correctly");

        // test 2: create a cd using factory
        System.out.print("test 2: create cd via factory... ");
        CD cd = ItemFactory.createCD("Thriller", "Michael Jackson", 1982, "Mint", 9, true);
        assertCondition(cd != null && cd.getArtist().equals("Michael Jackson"), "cd created correctly");

        // test 3: create a cassette using factory
        System.out.print("test 3: create cassette via factory... ");
        Cassette cassette = ItemFactory.createCassette("Nevermind", "Nirvana", 1991, "Good", "Chrome", 60);
        assertCondition(cassette != null && cassette.getYear() == 1991, "cassette created correctly");

        // test 4: use generic createItem method
        System.out.print("test 4: create item via generic factory method... ");
        CollectionItem item = ItemFactory.createItem(ItemFactory.MediaType.RECORD,
            "Dark Side of the Moon", "Pink Floyd", 1973, "Mint", "12\"", "33");
        assertCondition(item instanceof Record, "generic factory creates correct type");

        // test 5: verify factory creates distinct instances
        System.out.print("test 5: factory creates distinct instances... ");
        CollectionItem item1 = ItemFactory.createRecord("Test", "Artist", 2000, "Good", "7\"", "45");
        CollectionItem item2 = ItemFactory.createRecord("Test", "Artist", 2000, "Good", "7\"", "45");
        assertCondition(item1 != item2, "factory creates new instances each time");

        System.out.println();
    }

    // tests the strategy pattern implementation
    private static void testStrategyPattern() {
        System.out.println("--- testing strategy pattern ---\n");

        // create a test collection using goldmine grading scale
        List<CollectionItem> collection = new ArrayList<>();
        collection.add(ItemFactory.createRecord("Ziggy Stardust", "David Bowie", 1972, "EX", "12\"", "33"));
        collection.add(ItemFactory.createCD("Back in Black", "AC/DC", 1980, "M", 10, true));
        collection.add(ItemFactory.createCassette("Purple Rain", "Prince", 1984, "VG", "Normal", 60));
        collection.add(ItemFactory.createRecord("Abbey Road", "The Beatles", 1969, "G", "12\"", "33"));
        collection.add(ItemFactory.createCD("Appetite for Destruction", "Guns N' Roses", 1987, "P", 12, false));

        // test 6: sort by title
        System.out.print("test 6: sort by title strategy... ");
        SortStrategy titleSort = new SortByTitle();
        titleSort.sort(collection);
        assertCondition(collection.get(0).getTitle().equals("Abbey Road"), "sorted alphabetically by title");

        // test 7: sort by artist
        System.out.print("test 7: sort by artist strategy... ");
        SortStrategy artistSort = new SortByArtist();
        artistSort.sort(collection);
        assertCondition(collection.get(0).getArtist().equals("AC/DC"), "sorted alphabetically by artist");

        // test 8: sort by year
        System.out.print("test 8: sort by year strategy... ");
        SortStrategy yearSort = new SortByYear();
        yearSort.sort(collection);
        assertCondition(collection.get(0).getYear() == 1969, "sorted by year (oldest first)");

        // test 9: sort by condition (using goldmine scale)
        System.out.print("test 9: sort by condition strategy... ");
        SortStrategy conditionSort = new SortByCondition();
        conditionSort.sort(collection);
        assertCondition(collection.get(0).getCondition().equals("M"), "sorted by condition (best first)");

        // test 10: sort by media type
        System.out.print("test 10: sort by media type strategy... ");
        SortStrategy mediaSort = new SortByMediaType();
        mediaSort.sort(collection);
        // cassette comes before cd alphabetically
        assertCondition(collection.get(0).getMediaType().equals("Cassette"), "sorted by media type");

        // test 11: verify strategy names (simplified without "Sort by" prefix)
        System.out.print("test 11: verify strategy names... ");
        assertCondition(titleSort.getStrategyName().equals("Title") &&
                       artistSort.getStrategyName().equals("Artist"),
                       "strategies have proper display names");

        System.out.println();
    }

    // tests the model classes
    private static void testModelClasses() {
        System.out.println("--- testing model classes ---\n");

        // test 12: record-specific properties
        System.out.print("test 12: record specific properties... ");
        Record record = new Record("Test Album", "Test Artist", 2020, "M", "7\"", "45");
        assertCondition(record.getSize().equals("7\"") && record.getSpeed().equals("45"),
                       "record has size and speed");

        // test 13: cd-specific properties
        System.out.print("test 13: cd specific properties... ");
        CD cd = new CD("Test Album", "Test Artist", 2020, "M", 15, true);
        assertCondition(cd.getTrackCount() == 15 && cd.hasBooklet(),
                       "cd has track count and booklet flag");

        // test 14: cassette-specific properties
        System.out.print("test 14: cassette specific properties... ");
        Cassette cassette = new Cassette("Test Album", "Test Artist", 2020, "M", "Metal", 90);
        assertCondition(cassette.getTapeType().equals("Metal") && cassette.getLength() == 90,
                       "cassette has tape type and length");

        // test 15: getMediaType returns correct type
        System.out.print("test 15: media type identification... ");
        assertCondition(record.getMediaType().equals("Vinyl Record") &&
                       cd.getMediaType().equals("CD") &&
                       cassette.getMediaType().equals("Cassette"),
                       "each type returns correct media type string");

        // test 16: getFormatDetails returns format info
        System.out.print("test 16: format details... ");
        assertCondition(record.getFormatDetails().contains("7\"") &&
                       cd.getFormatDetails().contains("15 tracks") &&
                       cassette.getFormatDetails().contains("Metal"),
                       "format details contain type-specific info");

        // test 17: toString produces readable output
        System.out.print("test 17: toString output... ");
        String recordString = record.toString();
        assertCondition(recordString.contains("Test Artist") &&
                       recordString.contains("Test Album") &&
                       recordString.contains("2020"),
                       "toString contains relevant info");

        // test 18: setters work correctly
        System.out.print("test 18: setter methods... ");
        record.setTitle("New Title");
        record.setArtist("New Artist");
        record.setYear(2021);
        record.setCondition("VG+");
        assertCondition(record.getTitle().equals("New Title") &&
                       record.getArtist().equals("New Artist") &&
                       record.getYear() == 2021 &&
                       record.getCondition().equals("VG+"),
                       "setters update values correctly");

        System.out.println();
    }

    // tests integration between components
    private static void testIntegration() {
        System.out.println("--- testing integration ---\n");

        // test 19: factory + strategy work together
        System.out.print("test 19: factory and strategy integration... ");
        List<CollectionItem> items = new ArrayList<>();
        items.add(ItemFactory.createRecord("Z Album", "Z Artist", 2000, "Good", "12\"", "33"));
        items.add(ItemFactory.createCD("A Album", "A Artist", 2010, "Mint", 10, true));

        SortStrategy strategy = new SortByTitle();
        strategy.sort(items);

        assertCondition(items.get(0).getTitle().equals("A Album"),
                       "factory-created items sorted by strategy");

        // test 20: multiple strategy changes
        System.out.print("test 20: strategy switching... ");
        new SortByYear().sort(items);
        boolean yearFirst = items.get(0).getYear() == 2000;
        new SortByArtist().sort(items);
        boolean artistFirst = items.get(0).getArtist().equals("A Artist");
        assertCondition(yearFirst && artistFirst, "can switch strategies dynamically");

        // test 21: large collection handling
        System.out.print("test 21: large collection... ");
        List<CollectionItem> largeCollection = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            largeCollection.add(ItemFactory.createCD("Album " + i, "Artist " + (100 - i), 1950 + i, "VG", 10, true));
        }
        new SortByArtist().sort(largeCollection);
        assertCondition(largeCollection.size() == 100 &&
                       largeCollection.get(0).getArtist().equals("Artist 1"),
                       "handles 100 items correctly");

        // test 22: edge case - empty collection
        System.out.print("test 22: empty collection sort... ");
        List<CollectionItem> emptyCollection = new ArrayList<>();
        new SortByTitle().sort(emptyCollection);
        assertCondition(emptyCollection.isEmpty(), "sorting empty collection doesn't crash");

        // test 23: edge case - single item collection
        System.out.print("test 23: single item collection... ");
        List<CollectionItem> singleItem = new ArrayList<>();
        singleItem.add(ItemFactory.createCassette("Only One", "Solo", 1990, "NM", "Chrome", 60));
        new SortByCondition().sort(singleItem);
        assertCondition(singleItem.size() == 1, "sorting single item works");

        System.out.println();
    }

    // launches the gui with demo data
    private static void launchGUIDemo() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // create the gui
                CollectionManagerGUI gui = new CollectionManagerGUI();

                // add some demo items to show off the application (using goldmine grading)
                gui.addItem(ItemFactory.createRecord("Abbey Road", "The Beatles", 1969, "EX", "12\"", "33"));
                gui.addItem(ItemFactory.createRecord("Dark Side of the Moon", "Pink Floyd", 1973, "M", "12\"", "33"));
                gui.addItem(ItemFactory.createCD("Thriller", "Michael Jackson", 1982, "NM", 9, true));
                gui.addItem(ItemFactory.createCD("Back in Black", "AC/DC", 1980, "VG+", 10, true));
                gui.addItem(ItemFactory.createCassette("Nevermind", "Nirvana", 1991, "VG", "Chrome", 60));
                gui.addItem(ItemFactory.createCassette("Purple Rain", "Prince", 1984, "EX", "Normal", 90));
                gui.addItem(ItemFactory.createRecord("Led Zeppelin IV", "Led Zeppelin", 1971, "G", "12\"", "33"));
                gui.addItem(ItemFactory.createCD("Rumours", "Fleetwood Mac", 1977, "NM", 11, true));

                System.out.println("\ngui launched with 8 demo items.");
                System.out.println("try adding, editing, deleting items and changing sort order!");
            }
        });
    }

    // helper method to check a condition and report result
    private static void assertCondition(boolean condition, String testName) {
        if (condition) {
            System.out.println("PASSED - " + testName);
            testsPassed++;
        } else {
            System.out.println("FAILED - " + testName);
            testsFailed++;
        }
    }
}
