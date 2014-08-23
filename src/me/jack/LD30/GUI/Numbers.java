package me.jack.LD30.GUI;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Created by Jack on 23/08/2014.
 */
public class Numbers {


    private static SpriteSheet font;

    public static void init() throws SlickException {
        font = new SpriteSheet("/res/numbers.png",8,8);
    }
    public static void draw(String numbers, Graphics g, int sX,int sY){
            char[] chars = numbers.toCharArray();

        int x = sX;
        int y = sY;
        for(char c : chars){
            int value = Integer.valueOf(c + "");
            Image sprite = font.getSprite(value,0);
            g.drawImage(sprite,x,y);
            x+=8;
        }
    }
}
