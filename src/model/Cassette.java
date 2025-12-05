/*
 * Cassette.java
 * purpose: represents a cassette tape in the collection
 *          extends CollectionItem as part of factory pattern
 * author: phin
 */

package model;

// cassette tape class - the portable format
public class Cassette extends CollectionItem {
    // cassette-specific attributes
    private String tapeType; // normal, chrome, metal
    private int length; // in minutes (60, 90, 120, etc.)

    // constructor with cassette-specific stuff
    public Cassette(String title, String artist, int year, String condition, String tapeType, int length) {
        super(title, artist, year, condition);
        this.tapeType = tapeType;
        this.length = length;
    }

    // getters for cassette properties
    public String getTapeType() {
        return tapeType;
    }

    public int getLength() {
        return length;
    }

    // setters for editing
    public void setTapeType(String tapeType) {
        this.tapeType = tapeType;
    }

    public void setLength(int length) {
        this.length = length;
    }

    // identifies this as a cassette
    @Override
    public String getMediaType() {
        return "Cassette";
    }

    // returns the cassette-specific details
    @Override
    public String getFormatDetails() {
        return tapeType + " tape, " + length + " min";
    }
}
