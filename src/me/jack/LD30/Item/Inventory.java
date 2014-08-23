package me.jack.LD30.Item;

import java.util.ArrayList;

/**
 * Created by Jack on 23/08/2014.
 */
public class Inventory {

    private ArrayList<ItemStack> items = new ArrayList<ItemStack>();

    public void addItem(Item i, int count) {
        for (ItemStack s : items) {
            if (s.getItem().id == i.id) {
                s.addItems(count);
                return;
            }
        }

        ItemStack stack = new ItemStack(i, count);
        items.add(stack);
    }


    public ArrayList<ItemStack> getItems() {
        return items;
    }
}
