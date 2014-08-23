package me.jack.LD30.Item;

/**
 * Created by Jack on 23/08/2014.
 */
public class ItemStack {

    private Item item;
    private int count;

    public ItemStack(Item item, int count){
        this.item = item;
        this.count = count;
    }

    public Item getItem(){
        return item;
    }

    public int getCount(){
        return count;
    }

    public void addItem(){
        count++;
    }

    public void addItems(int amount){
        count+= amount;
    }

    public void removeItem(){
        count--;
    }

    public void removeItems(int amount){
        count -=amount;
    }

}
