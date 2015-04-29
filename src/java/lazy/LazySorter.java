/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lazy;

import java.util.Comparator;
import modelo.Item;
import org.primefaces.model.SortOrder;

/**
 *
 * @author praveen
 */
public class LazySorter implements Comparator<Item> {

    private String sortField;
    private SortOrder sortOrder;

    public LazySorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    public int compare(Item item1, Item item2) {
        try {
            Object value1 = Item.class.getField(this.sortField).get(item1);
            Object value2 = Item.class.getField(this.sortField).get(item2);
            int value = ((Comparable) value1).compareTo(value2);
            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

}
