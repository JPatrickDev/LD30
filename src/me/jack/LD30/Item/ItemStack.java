package me.jack.LD30.Item;

/**
 * Created by Jack on 23/08/2014.
 */
public class ItemStack {

    private Item item;
    private int count;

    private Inventory parent;

    public ItemStack(Item item, int count, Inventory parent) {
        this.item = item;
        this.count = count;
        this.parent = parent;
    }

    public Item getItem() {
        return item;
    }

    public int getCount() {
        return count;
    }

    public void addItem() {
        count++;
    }

    public void addItems(int amount) {
        count += amount;
    }

    public void removeItem() {
        removeItems(1);
    }

    public void removeItems(int amount) {

        count -= amount;
        if (count == 0 && parent != null) {
            parent.removeItemStack(this);
        }

    }

}
