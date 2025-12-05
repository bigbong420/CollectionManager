/*
 * SortByYear.java
 * purpose: sorts collection items by release year (oldest first)
 *          implements the strategy pattern for flexible sorting
 * author: phin
 */

package strategy;

import model.CollectionItem;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// sorts items by year - oldest to newest
public class SortByYear implements SortStrategy {

    @Override
    public void sort(List<CollectionItem> items) {
        // numeric comparison for years
        Collections.sort(items, new Comparator<CollectionItem>() {
            @Override
            public int compare(CollectionItem item1, CollectionItem item2) {
                // simple integer comparison
                return Integer.compare(item1.getYear(), item2.getYear());
            }
        });
    }

    @Override
    public String getStrategyName() {
        return "Year";
    }
}
