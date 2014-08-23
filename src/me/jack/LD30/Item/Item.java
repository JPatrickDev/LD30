package me.jack.LD30.Item;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Created by Jack on 23/08/2014.
 */
public class Item {


    private static SpriteSheet items;


    public static Item logs;
    public static void init() throws SlickException {

        Image i = new Image("/res/items.png");
        i.setFilter(Image.FILTER_NEAREST);
        i = i.getScaledCopy(4f);

        items = new SpriteSheet(i,64,64);

        logs = new Item(0,0);
    }

    private Image sprite;

    public Item(int x,int y){
        this.sprite = items.getSprite(x,y);
    }


    public Image getSprite(){
        return sprite;
    }
}
