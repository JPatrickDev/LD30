package me.jack.LD30.Level;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Created by Jack on 23/08/2014.
 */
public class Tile {



    public Image sprite;

    public static SpriteSheet sprites;

    public static void load() throws SlickException {
        sprites = new SpriteSheet(new Image("/res/sprites.png"),64,64);
    }

    public Tile(int x,int y){
           sprite = sprites.getSprite(x,y);
    }
}
