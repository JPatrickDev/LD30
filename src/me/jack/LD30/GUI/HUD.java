package me.jack.LD30.GUI;

import me.jack.LD30.Item.Inventory;
import me.jack.LD30.Item.ItemStack;
import me.jack.LD30.Level.Level;
import org.newdawn.slick.*;

/**
 * Created by Jack on 23/08/2014.
 */
public class HUD {


    private static SpriteSheet icons;

    private static Image heart;
    private static Image halfHeart;

    public static void init() throws SlickException {
        icons = new SpriteSheet(new Image("/res/icons.png"),16,16);
        heart = icons.getSprite(0,0);
        halfHeart = icons.getSprite(1,0);
    }
    public static void drawHUD(Graphics g,Level l){
        g.fillRect(0,500,800,100);

        int gap_per_line = 800/3;
        g.setColor(Color.black);
        g.drawLine(gap_per_line,500,gap_per_line,600);

        g.drawLine(gap_per_line*2,500,gap_per_line * 2,600);

        g.setColor(Color.white);

        int health = l.p.getHealth();

        int full_hearts = health/2;

        for(int i = 0;i!= full_hearts;i++){
            g.drawImage(heart,(i*18),510);
        }




        //draw inventory

        Inventory i = l.p.i;

        int startX = (gap_per_line) + 5;
        int startY = 505;
        for(ItemStack items : i.getItems()){
            g.drawImage(items.getItem().getIcon(),startX + 5,startY);
            Numbers.draw(items.getCount() + "",g,startX,startY);
            startY += 38;

        }




        startX = gap_per_line*2;
        startY = 505;

        Text.drawLarge("Crafting",g,startX,startY);
    }
}
