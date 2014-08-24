package me.jack.LD30.Item;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Jack on 23/08/2014.
 */
public class Inventory {

    private CopyOnWriteArrayList<ItemStack> items = new CopyOnWriteArrayList<ItemStack>();

    public void addItem(Item i, int count) {
        for (ItemStack s : items) {
            if (s.getItem().id == i.id) {
                s.addItems(count);
                return;
            }
        }

        ItemStack stack = new ItemStack(i, count,this);
        items.add(stack);
    }


    public CopyOnWriteArrayList<ItemStack> getItems() {
        return items;
    }

    public boolean contains(ItemStack itemStack) {
        for (ItemStack item : items) {
            if (item.getItem().id == itemStack.getItem().id) {
                if (item.getCount() >= itemStack.getCount()) {
                    return true;
                }
            }

        }

        return false;
    }

    public void removeItemStack(ItemStack remove){
        items.remove(remove);
    }
}
