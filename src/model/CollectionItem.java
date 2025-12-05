/*
 * CollectionItem.java
 * purpose: base abstract class for all collection items (records, cds, cassettes)
 *          this serves as the foundation for the factory pattern implementation
 * author: phin
 */

package model;

// abstract base class that all collection items extend
// uses template method pattern for common functionality
public abstract class CollectionItem {
    // core attributes every collection item has
    protected String title;
    protected String artist;
    protected int year;
    protected String condition; // mint, excellent, good, fair, poor

    // constructor sets up the basic item info
    public CollectionItem(String title, String artist, int year, String condition) {
        this.title = title;
        this.artist = artist;
        this.year = year;
        this.condition = condition;
    }

    // getters for all the properties - pretty standard stuff
    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public int getYear() {
        return year;
    }

    public String getCondition() {
        return condition;
    }

    // setters in case user wants to edit items later
    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    // each subclass must define what type of media it is
    public abstract String getMediaType();

    // each subclass can have format-specific details
    public abstract String getFormatDetails();

    // nice string representation for display purposes
    @Override
    public String toString() {
        return String.format("%s - %s (%d) [%s] - %s",
            artist, title, year, condition, getMediaType());
    }
}
