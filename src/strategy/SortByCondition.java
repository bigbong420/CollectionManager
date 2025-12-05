/*
 * SortByCondition.java
 * purpose: sorts collection items by condition quality (best first)
 *          implements the strategy pattern for flexible sorting
 * author: phin
 */

package strategy;

import model.CollectionItem;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// sorts items by condition - mint first, poor last
public class SortByCondition implements SortStrategy {

    // map condition strings to numeric values for comparison
    // lower number = better condition = appears first
    // uses goldmine grading scale
    private static final Map<String, Integer> CONDITION_RANK = new HashMap<>();

    static {
        // initialize condition rankings - goldmine scale (plus EX)
        CONDITION_RANK.put("M", 1);    // mint
        CONDITION_RANK.put("NM", 2);   // near mint
        CONDITION_RANK.put("EX", 3);   // excellent
        CONDITION_RANK.put("VG+", 4);  // very good plus
        CONDITION_RANK.put("VG", 5);   // very good
        CONDITION_RANK.put("G+", 6);   // good plus
        CONDITION_RANK.put("G", 7);    // good
        CONDITION_RANK.put("F", 8);    // fair
        CONDITION_RANK.put("P", 9);    // poor
    }

    @Override
    public void sort(List<CollectionItem> items) {
        Collections.sort(items, new Comparator<CollectionItem>() {
            @Override
            public int compare(CollectionItem item1, CollectionItem item2) {
                // get rank for each condition, default to 99 if unknown
                int rank1 = CONDITION_RANK.getOrDefault(item1.getCondition(), 99);
                int rank2 = CONDITION_RANK.getOrDefault(item2.getCondition(), 99);
                return Integer.compare(rank1, rank2);
            }
        });
    }

    @Override
    public String getStrategyName() {
        return "Condition";
    }
}
