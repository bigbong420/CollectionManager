/*
 * SortByArtist.java
 * purpose: sorts collection items alphabetically by artist name
 *          implements the strategy pattern for flexible sorting
 * author: phin
 */

package strategy;

import model.CollectionItem;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// sorts items by their artist - alphabetically, case insensitive
public class SortByArtist implements SortStrategy {

    @Override
    public void sort(List<CollectionItem> items) {
        // using comparator for clean sorting by artist name
        Collections.sort(items, new Comparator<CollectionItem>() {
            @Override
            public int compare(CollectionItem item1, CollectionItem item2) {
                // case insensitive for consistency
                return item1.getArtist().compareToIgnoreCase(item2.getArtist());
            }
        });
    }

    @Override
    public String getStrategyName() {
        return "Artist";
    }
}
