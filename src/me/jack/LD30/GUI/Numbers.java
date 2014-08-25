package me.jack.LD30.GUI;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import uk.co.jdpatrick.JEngine.Image.ImageUtil;

/**
 * Created by Jack on 23/08/2014.
 */
public class Numbers {


    private static SpriteSheet font;
    private static SpriteSheet largeFont;

    public static void init() throws SlickException {


        Image baseFont = new Image("res/numbers.png");
        baseFont.setFilter(Image.FILTER_NEAREST);
        font = new SpriteSheet(baseFont, 8, 8);
        baseFont = baseFont.getScaledCopy(4f);
        largeFont = new SpriteSheet(baseFont, 32, 32);
    }

    public static void draw(String numbers, Graphics g, int sX, int sY) {
        char[] chars = numbers.toCharArray();

        int x = sX;
        int y = sY;
        for (char c : chars) {
            int value = Integer.valueOf(c + "");
            Image sprite = font.getSprite(value, 0);
            g.drawImage(sprite, x, y);
            x += 8;
        }
    }

    public static void drawLarge(String numbers, Graphics g, int sX, int sY) {
        char[] chars = numbers.toCharArray();

        int x = sX;
        int y = sY;
        for (char c : chars) {
            int value = Integer.valueOf(c + "");
            Image sprite = largeFont.getSprite(value, 0);
            g.drawImage(sprite, x, y);
            x += 32;
        }
    }
}

