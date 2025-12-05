/*
 * CD.java
 * purpose: represents a compact disc in the collection
 *          extends CollectionItem as part of factory pattern
 * author: phin
 */

package model;

// compact disc class - the digital era format
public class CD extends CollectionItem {
    // cd-specific attributes
    private int trackCount;
    private boolean hasBooklet;

    // constructor with cd-specific stuff
    public CD(String title, String artist, int year, String condition, int trackCount, boolean hasBooklet) {
        super(title, artist, year, condition);
        this.trackCount = trackCount;
        this.hasBooklet = hasBooklet;
    }

    // getters for cd properties
    public int getTrackCount() {
        return trackCount;
    }

    public boolean hasBooklet() {
        return hasBooklet;
    }

    // setters for editing
    public void setTrackCount(int trackCount) {
        this.trackCount = trackCount;
    }

    public void setHasBooklet(boolean hasBooklet) {
        this.hasBooklet = hasBooklet;
    }

    // identifies this as a cd
    @Override
    public String getMediaType() {
        return "CD";
    }

    // returns the cd-specific details
    @Override
    public String getFormatDetails() {
        return trackCount + " tracks" + (hasBooklet ? ", includes booklet" : "");
    }
}
