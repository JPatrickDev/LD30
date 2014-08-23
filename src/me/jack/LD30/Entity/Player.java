package me.jack.LD30.Entity;

import me.jack.LD30.Level.Level;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Graphics;

/**
 * Created by Jack on 23/08/2014.
 */
public class Player extends Mob{


    public Player(int x, int y) {
        super(x, y);
        this.health = 20;
    }

    @Override
    public void update(Level level) {



        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            if(level.canMove(x,y-3,64,64)) {
                y -= 4;
            }
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            if(level.canMove(x,y+3,64,64)) {
                y += 4;
            }
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            if(level.canMove(x-3,y,64,64)) {
                x -= 4;
            }
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            if(level.canMove(x+3,y,64,64)) {
                x += 4;
            }
        }




    }

    @Override
    public void render(Graphics g) {
             g.fillRect(getX(),getY(),64,64);
    }
}
