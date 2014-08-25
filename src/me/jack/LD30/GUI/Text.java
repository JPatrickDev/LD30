package me.jack.LD30.GUI;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Created by Jack on 23/08/2014.
 */
public class Text {



    private static SpriteSheet font;
    public static SpriteSheet largeFont;
    public static SpriteSheet eLarge;

    public static void init() throws SlickException {
        font = new SpriteSheet("/res/font.png",8,8);
        Image i = new Image("/res/font.png");
        i.setFilter(Image.FILTER_NEAREST);
        largeFont = new SpriteSheet(i.getScaledCopy(2f),16,16);
        eLarge = new SpriteSheet(i.getScaledCopy(4f),32,32);
    }
    public static void draw(String string, Graphics g, int sX,int sY){
        char[] chars = string.toLowerCase().toCharArray();

        int x = sX;
        int y = sY;
        for(char c : chars) {
            if(c == '\\'){
                y+=12;
                x = sX;
                continue;
            }
            int temp = (int) c;
            int temp_integer = 96; //for lower case
            int pos = 0;
            if (temp <= 122 & temp >= 97)
                pos = temp - 96;
            if (pos == 0) {
                    x+=8;
            } else{
                Image sprite = font.getSprite(pos - 1, 0);
            g.drawImage(sprite, x, y);
            x += 8;
        }
        }
    }

    public static void drawLarge(String string, Graphics g, int sX,int sY) {
        char[] chars = string.toLowerCase().toCharArray();

        int x = sX;
        int y = sY;
        for (char c : chars) {
            if(c == '\\'){
                y+=28;
                x = sX;
                continue;
            }


            if(c == '>'){

                Image sprite = largeFont.getSprite(26, 0);
                g.drawImage(sprite, x, y);
                x += 16;
                continue;
            }else if(c == '<'){



                Image sprite = largeFont.getSprite(27, 0);
                g.drawImage(sprite, x, y);
                x += 16;
                continue;
            }
            int temp = (int) c;
            int temp_integer = 96; //for lower case
            int pos = 0;
            if (temp <= 122 & temp >= 97)
                pos = temp - 96;
            if (pos == 0) {
                x += 16;
            } else {
                Image sprite = largeFont.getSprite(pos - 1, 0);
                g.drawImage(sprite, x, y);
                x += 16;
            }
        }
    }

    public static void drawELarge(String s, Graphics g, int sX, int sY) {
        char[] chars = s.toLowerCase().toCharArray();

        int x = sX;
        int y = sY;
        for (char c : chars) {
            if(c == '\\'){
                y+=42;
                x = sX;
                continue;
            }


            if(c == '>'){

                Image sprite = eLarge.getSprite(26, 0);
                g.drawImage(sprite, x, y);
                x += 32;
                continue;
            }else if(c == '<'){



                Image sprite = eLarge.getSprite(27, 0);
                g.drawImage(sprite, x, y);
                x += 32;
                continue;
            }
            int temp = (int) c;
            int temp_integer = 96; //for lower case
            int pos = 0;
            if (temp <= 122 & temp >= 97)
                pos = temp - 96;
            if (pos == 0) {
                x += 32;
            } else {
                Image sprite = eLarge.getSprite(pos - 1, 0);
                g.drawImage(sprite, x, y);
                x += 32;
            }
        }
    }
}




