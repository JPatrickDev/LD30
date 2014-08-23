package me.jack.LD30.Entity;

import me.jack.LD30.Level.Level;
import org.newdawn.slick.Graphics;

/**
 * Created by Jack on 23/08/2014.
 */
public abstract class Entity {

    protected int x,y;


    public Entity(int x,int y){
        this.x = x;
        this.y = y;
    }


    public abstract void update(Level level);
    public abstract void render(Graphics g);

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
