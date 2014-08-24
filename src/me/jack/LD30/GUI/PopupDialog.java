package me.jack.LD30.GUI;

import me.jack.LD30.Entity.Player;
import me.jack.LD30.Level.Level;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * Created by Jack on 23/08/2014.
 */
public abstract class PopupDialog {

    public abstract void render(Graphics g);
    public abstract void update(GameContainer gc,  Level level);

    public abstract void keyReleased(int key, Player p);
}
