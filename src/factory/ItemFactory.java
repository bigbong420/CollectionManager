/*
 * ItemFactory.java
 * purpose: factory pattern implementation for creating collection items
 *          centralizes object creation and makes it easy to add new formats
 * author: phin
 */

package factory;

import model.CollectionItem;
import model.Record;
import model.CD;
import model.Cassette;

// factory class that creates the appropriate collection item based on type
// this is the heart of the factory pattern - all item creation goes through here
public class ItemFactory {

    // enum to define the supported media types
    // makes it type-safe and easy to add new formats later
    public enum MediaType {
        RECORD, CD, CASSETTE
    }

    // main factory method - creates items based on type
    // this is where the magic happens - no need for if/else everywhere
    public static CollectionItem createItem(MediaType type, String title, String artist,
                                            int year, String condition, Object... extras) {
        switch (type) {
            case RECORD:
                // extras[0] = size, extras[1] = speed
                return createRecord(title, artist, year, condition,
                    (String) extras[0], (String) extras[1]);

            case CD:
                // extras[0] = trackCount, extras[1] = hasBooklet
                return createCD(title, artist, year, condition,
                    (Integer) extras[0], (Boolean) extras[1]);

            case CASSETTE:
                // extras[0] = tapeType, extras[1] = length
                return createCassette(title, artist, year, condition,
                    (String) extras[0], (Integer) extras[1]);

            default:
                // shouldn't happen with enum but just in case
                throw new IllegalArgumentException("unknown media type: " + type);
        }
    }

    // helper method specifically for records
    // nice and clean, all the vinyl-specific logic in one place
    public static Record createRecord(String title, String artist, int year,
                                       String condition, String size, String speed) {
        return new Record(title, artist, year, condition, size, speed);
    }

    // helper method specifically for cds
    public static CD createCD(String title, String artist, int year,
                              String condition, int trackCount, boolean hasBooklet) {
        return new CD(title, artist, year, condition, trackCount, hasBooklet);
    }

    // helper method specifically for cassettes
    public static Cassette createCassette(String title, String artist, int year,
                                          String condition, String tapeType, int length) {
        return new Cassette(title, artist, year, condition, tapeType, length);
    }
}
