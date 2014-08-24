package me.jack.LD30.Level;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import java.awt.*;


/**
 * Created by Jack on 23/08/2014.
 */
public class Tile {


    public Image sprite;
    public boolean solid = false;
    public static SpriteSheet sprites;

    public Animation animation;



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

    public Tile(Point[] tilesPoint, boolean solid){

        animation = new Animation();


        for(Point p : tilesPoint){

            System.out.println("Adding animation");
            Image i = sprites.getSprite((int)p.getX(),(int)p.getY());
            animation.addFrame(i,100);
        }
        animation.setLooping(true);
        animation.start();
        animation.setAutoUpdate(true);
    }
}
