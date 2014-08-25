package me.jack.LD30.Item;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import java.util.ArrayList;

/**
 * Created by Jack on 23/08/2014.
 */
public class Item {


    private static SpriteSheet items;
    private static SpriteSheet icons;

    public static Item logs;
    public static Item apple;




    public static Item woodAxe;
    public static Item woodSword;

    public static Item key;
    public static void init() throws SlickException {

        Image i = new Image("res/items.png");
        i.setFilter(Image.FILTER_NEAREST);
        i = i.getScaledCopy(4f);

        items = new SpriteSheet(i,64,64);

        icons = new SpriteSheet(i.getScaledCopy(0.5f),32,32);
        logs = new Item(0,0,0);
        apple = new Item(1,0,1);
        woodAxe = new Item(2,0,2);
        woodAxe.craftRequirements.add(new ItemStack(Item.logs,2,null));
        woodSword = new Item(3,0,3);
        woodSword.craftRequirements.add(new ItemStack(Item.logs,3,null));

        key = new Item(0,1,4);
    }

    private Image sprite;
    private Image icon;
    private ArrayList<ItemStack> craftRequirements = new ArrayList<ItemStack>();

    public int id;
    public Item(int x,int y,int id){
        this.sprite = items.getSprite(x,y);
        this.icon = icons.getSprite(x,y);
        this.id = id;
    }


    public Image getSprite(){
        return sprite;
    }

    public Image getIcon(){
        return icon;
    }

    public ArrayList<ItemStack> getCraftRequirements(){
        return craftRequirements;
    }

}
