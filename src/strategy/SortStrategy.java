/*
 * SortStrategy.java
 * purpose: interface for the strategy pattern - defines how sorting works
 *          allows different sorting implementations without if/else madness
 * author: phin
 */

package strategy;

import model.CollectionItem;
import java.util.List;

// strategy interface for sorting collection items
// any new sort method just needs to implement this interface
public interface SortStrategy {

    // sorts the given list of items - each implementation decides how
    void sort(List<CollectionItem> items);

    // returns a nice name for display in the ui
    String getStrategyName();
}
