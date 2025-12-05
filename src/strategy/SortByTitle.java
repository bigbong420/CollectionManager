/*
 * SortByTitle.java
 * purpose: sorts collection items alphabetically by title
 *          implements the strategy pattern for flexible sorting
 * author: phin
 */

package strategy;

import model.CollectionItem;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// sorts items by their title - alphabetically, case insensitive
public class SortByTitle implements SortStrategy {

    @Override
    public void sort(List<CollectionItem> items) {
        // using comparator for clean, readable sorting
        Collections.sort(items, new Comparator<CollectionItem>() {
            @Override
            public int compare(CollectionItem item1, CollectionItem item2) {
                // case insensitive comparison for better user experience
                return item1.getTitle().compareToIgnoreCase(item2.getTitle());
            }
        });
    }

    @Override
    public String getStrategyName() {
        return "Title";
    }
}
