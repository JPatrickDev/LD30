package me.jack.LD30.Entity;

import me.jack.LD30.Level.Level;
import org.newdawn.slick.Graphics;

import java.awt.*;

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

    public  void steppedOn(Player player, Level level){}

    public abstract void notifyTouchedPlayer(Level parent);


    public void knockback(Level l, int strength, int direction){
       // System.out.println("Knockback");s

        int xa = 0;
        int ya = 0;
            if(direction == 0){
                    ya -= 32 * strength;

            }else if(direction == 1){
                    xa+=32* strength;

            }else if(direction == 2){

                        ya+=32 * strength;
                    }

            else if(direction ==3){
                xa-=32 * strength;
            }

        if(l.canMove(x+xa,y+ya,64,64,this)){
            x+=xa;
            y+=ya;
        }
    }

}
