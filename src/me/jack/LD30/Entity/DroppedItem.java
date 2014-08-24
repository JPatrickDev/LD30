package me.jack.LD30.Entity;

import me.jack.LD30.Item.Item;
import me.jack.LD30.Level.Level;
import org.newdawn.slick.Graphics;

import java.util.Random;

/**
 * Created by Jack on 23/08/2014.
 */
public class DroppedItem extends Entity{

    private  double xa;
    private  double ya;
    private Item i;
    public DroppedItem(int x, int y,Item item) {
        super(x, y);
        this.i = item;
        Random random = new Random();
        xa = random.nextGaussian() * 5;
        ya = random.nextGaussian() * 5;
        if(random.nextBoolean())xa = -xa;
    }
int time = 0;



    @Override
    public void update(Level level) {
        time++;
        if (time < 5) {


            x+= xa;
            y += ya;



            if (xa > 0) {
                x -= 0.05;
            } else {
                x += 0.05;
            }

            if (ya > 0) {
                y-= 0.1;
            } else {
                y+= 0.1;
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(i.getSprite(),x,y);
    }

    @Override
    public void steppedOn(Player p,Level level){
        level.entities.remove(this);
        p.i.addItem(i,1);
    }

    @Override
    public void notifyTouchedPlayer(Level parent) {

    }


}
