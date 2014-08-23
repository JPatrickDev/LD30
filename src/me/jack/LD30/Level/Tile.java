package me.jack.LD30.Level;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Created by Jack on 23/08/2014.
 */
public class Tile {



    public Image sprite;
    public boolean solid = false;
    public static SpriteSheet sprites;

    public static void load() throws SlickException {
        sprites = new SpriteSheet(new Image("/res/sprites.png").getScaledCopy(8f),128,128);
    }

    public Tile(String image,boolean solid){
        try {
            sprite = new Image(image);
            sprite.setFilter(Image.FILTER_NEAREST);
            sprite = sprite.getScaledCopy(8f);
        } catch (SlickException e) {
            e.printStackTrace();
        }
        this.solid = solid;
    }
}
