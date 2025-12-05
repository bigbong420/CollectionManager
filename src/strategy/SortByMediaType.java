/*
 * SortByMediaType.java
 * purpose: sorts collection items by media type (groups similar formats together)
 *          implements the strategy pattern for flexible sorting
 * author: phin
 */

package strategy;

import model.CollectionItem;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// sorts items by media type - groups records, cds, cassettes together
public class SortByMediaType implements SortStrategy {

    @Override
    public void sort(List<CollectionItem> items) {
        Collections.sort(items, new Comparator<CollectionItem>() {
            @Override
            public int compare(CollectionItem item1, CollectionItem item2) {
                // alphabetical by media type name
                return item1.getMediaType().compareToIgnoreCase(item2.getMediaType());
            }
        });
    }

    @Override
    public String getStrategyName() {
        return "Format";
    }
}
