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
        Image s = new Image("/res/tiles.png");
        s.setFilter(Image.FILTER_NEAREST);
                s = s.getScaledCopy(8f);
        sprites = new SpriteSheet(s, 128, 128);
    }

    public Tile(int x, int y,boolean solid) {
        sprite = sprites.getSprite(x,y);
        this.solid = solid;
    }
}
