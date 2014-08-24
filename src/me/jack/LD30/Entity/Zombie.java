package me.jack.LD30.Entity;

import me.jack.LD30.Level.Level;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.pathfinding.Path;

/**
 * Created by Jack on 24/08/2014.
 */
public class Zombie extends Mob {

    static Image zombie;

    public Zombie(int x, int y) {
        super(x, y);

        try {
            if (zombie == null) {
                zombie = new Image("/res/zombie.png");
                zombie.setFilter(Image.FILTER_NEAREST);
                zombie = zombie.getScaledCopy(4f);
            }
        } catch (SlickException e) {
            e.printStackTrace();
        }

    }

    private Path findPlayer;
    private int step = 0;

    @Override
    public void update(Level level) {

        //todo Select two points near Zombie, patrol between them, If player comes within 5 tiles of Zombie, move towards him
        //todo Don't try to use a star to attack player, zombies are not clever

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(zombie, getX(), getY());
    }
}
