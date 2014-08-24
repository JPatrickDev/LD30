package me.jack.LD30.GUI;

import me.jack.LD30.Entity.Player;
import me.jack.LD30.Item.Item;
import me.jack.LD30.Item.ItemStack;
import me.jack.LD30.Level.Level;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Jack on 23/08/2014.
 */
public class CraftingDialog extends PopupDialog {

    private Image gui;

    private String[] craftable = new String[]{"Axe", "Sword"};
    private Item[] craftableItems = new Item[]{Item.woodAxe, Item.woodSword};
    private String[] desc = new String[]{"Used to get more\\wood and apple\\per tree", "Kills zombies faster"};

    int pos = 0;

    public CraftingDialog() throws SlickException {
        this.gui = new Image("/res/crafting.png");
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(gui, 50, 50);

        int y = 70;

        for (String s : craftable) {
            Text.drawLarge(s, g, 70, y);
            y += 24;
        }

        g.drawImage(craftableItems[pos].getSprite(), 244, 84);


        Text.drawLarge(craftable[pos], g, 230, 180);

        Text.draw(desc[0], g, 230, 212);

        Text.drawLarge("Requires", g, 230, 260);

        ArrayList<ItemStack> requirements = craftableItems[pos].getCraftRequirements();

        int startY = 292;
        for (ItemStack items : requirements) {
            g.drawImage(items.getItem().getIcon(), 235, startY);
            Numbers.draw(items.getCount() + "", g, 230, startY);
            startY += 38;

        }
        if(l == null)return;


        Text.drawLarge("You have:", g, 230, startY + 8);
        startY += 32;
        CopyOnWriteArrayList<ItemStack> items = l.p.i.getItems();
        for(ItemStack itemStack : items){
            for(ItemStack s : requirements){
                if(itemStack.getItem().id == s.getItem().id){
                    g.drawImage(itemStack.getItem().getIcon(), 235, startY);
                    Numbers.draw(itemStack.getCount() + "", g, 230, startY);
                    startY += 38;
                }
            }
        }
    }

    public void craft(Player p){
        System.out.println("Crafting");
        Item item = craftableItems[pos];
        System.out.println("crafting: " + item.id);
        ArrayList<ItemStack> requirements = item.getCraftRequirements();

        CopyOnWriteArrayList<ItemStack> pInv = p.i.getItems();

        for(ItemStack i : requirements){
            System.out.println("Requirement: " + i.getItem().id);
            for(ItemStack pItem : pInv){
                System.out.println("Inv item: " + i.getItem().id);
                if(i.getItem().id == pItem.getItem().id){
                    System.out.println("Match found");
                    if(i.getCount() <= pItem.getCount()){
                        pItem.removeItems(i.getCount());
                        p.i.addItem(Item.woodAxe,1);
                    }
                }else{
                    System.out.println(i.getItem().id);
                    System.out.println(pItem.getItem().id);
                }
            }
        }
    }

    Level l;

    @Override
    public void update(GameContainer gc, Level level) {
        this.l = level;
    }

    @Override
    public void keyReleased(int key,Player p) {
            craft(p);
    }
}
