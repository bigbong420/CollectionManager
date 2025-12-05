/*
 * Record.java
 * purpose: represents a vinyl record in the collection
 *          extends CollectionItem as part of factory pattern
 * author: phin
 */

package model;

// vinyl record class - the classic format
public class Record extends CollectionItem {
    // vinyl-specific attributes
    private String size; // 7", 10", or 12"
    private String speed; // 33, 45, or 78 rpm

    // constructor with all the vinyl-specific stuff
    public Record(String title, String artist, int year, String condition, String size, String speed) {
        super(title, artist, year, condition);
        this.size = size;
        this.speed = speed;
    }

    // getters for vinyl properties
    public String getSize() {
        return size;
    }

    public String getSpeed() {
        return speed;
    }

    // setters for editing
    public void setSize(String size) {
        this.size = size;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    // identifies this as a vinyl record
    @Override
    public String getMediaType() {
        return "Vinyl Record";
    }

    // returns the vinyl-specific details
    @Override
    public String getFormatDetails() {
        return size + " @ " + speed + " RPM";
    }
}
