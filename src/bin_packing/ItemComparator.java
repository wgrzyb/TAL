/*
 * Wojciech Grzyb
 * WCY19KB2S4
 */
package bin_packing;

import java.util.Comparator;

public class ItemComparator implements Comparator<Item> {
    @Override
    public int compare(Item o1, Item o2) {
        return Double.compare(o1.getSize(), o2.getSize());
    }
}
