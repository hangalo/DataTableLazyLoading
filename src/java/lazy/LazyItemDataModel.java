/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lazy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import modelo.Item;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author praveen
 */
public class LazyItemDataModel extends LazyDataModel<Item> {

    private List<Item> items;

    public LazyItemDataModel(List<Item> items) {
        this.items = items;
    }

    @Override
    public Item getRowData(String rowKey) {
        Integer id = Integer.valueOf(rowKey);
        for (Item item : items) {
            if (id.equals(item.getId())) {
                return item;
            }
        }
        return null;
    }

    @Override
    public Object getRowKey(Item item) {
        return item.getId();
    }

    public List<Item> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        List<Item> itemData = new ArrayList<>();

        //filter
        for (Item item : items) {
            boolean match = true;
            if (filters != null) {
                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
                    try {
                        String filterProperty = it.next();
                        Object filterValue = filters.get(filterProperty);
                        String fieldValue = String.valueOf(item.getClass().getField(filterProperty).get(item));
                        if (fieldValue == null || fieldValue.startsWith(filterValue.toString())) {
                            match = true;
                        } else {
                            match = false;
                            break;
                        }

                    } catch (Exception e) {
                        match = false;

                    }
                }
            }
            if (match) {
                itemData.add(item);
            }
        }

        //sort
        if (sortField != null) {
            Collections.sort(itemData, new LazySorter(sortField, sortOrder));

        }

        // rowCount
        int dataSize = itemData.size();
        this.setRowCount(dataSize);

        if (dataSize > pageSize) {
            try {
                return itemData.subList(first, first + pageSize);
            } catch (IndexOutOfBoundsException e) {
                return itemData.subList(first, first + (dataSize % pageSize));
            }
        } else {

            return itemData;
        }

    }
}
