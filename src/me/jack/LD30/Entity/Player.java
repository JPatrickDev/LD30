package me.jack.LD30.Entity;

import me.jack.LD30.Item.Inventory;
import me.jack.LD30.Item.Item;
import me.jack.LD30.Level.Level;
import me.jack.LD30.Particle.TreeBreakParticle;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;

/**
 * Created by Jack on 23/08/2014.
 */
public class Player extends Mob {


    public Inventory i = new Inventory();

    public Image player = null;

    public Circle attackDistance = new Circle(50,50, 2*128);

    public Player(int x, int y) {
        super(x, y);
        this.health = 20;
        try {
            player = new Image("/res/player.png");
            player.setFilter(Image.FILTER_NEAREST);
            player = player.getScaledCopy(4f);
        } catch (SlickException e) {
            e.printStackTrace();
        }

        attackDistance.setCenterX(x + 32);
        attackDistance.setCenterY(y + 32);
    }

    @Override
    public void update(Level level) {


        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            if (level.canMove(x, y - 3, 64, 64)) {
                y -= 4;
            }
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            if (level.canMove(x, y + 3, 64, 64)) {
                y += 4;
            }
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            if (level.canMove(x - 3, y, 64, 64)) {
                x -= 4;
            }
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            if (level.canMove(x + 3, y, 64, 64)) {
                x += 4;
            }
        }


        attackDistance.setCenterX(x + 32);
        attackDistance.setCenterY(y + 32);

    }

    @Override
    public void render(Graphics g) {

        Color c = Color.magenta;
        g.setColor(c);
        g.draw(attackDistance);
        g.setColor(Color.white);



        g.drawImage(player, getX(), getY());
    }

    public void click(int x, int y, Level level) {
        int tX = (x + level.c.x) / 128;
        int tY = (y + level.c.y) / 128;

        System.out.println(level.getTiles().length);
        float tile = level.getTiles()[tX][tY];
        float tree = level.getTrees()[tX][tY];

        System.out.println(tX + ":" + tY);

        if (tree == 1) {
            System.out.println("Tree clicked");

            float[][] trees = level.getTrees();
            trees[tX][tY] = 0;
            level.setTrees(trees);

            for (int i = 0; i != 30; i++) {
                level.system.addParticle(new TreeBreakParticle((tX * 128) + 64, (tY * 128) + 64));
            }


            level.entities.add(new DroppedItem((tX * 128) + 64, (tY * 128) + 64, Item.logs));
            level.entities.add(new DroppedItem((tX * 128) + 64, (tY * 128) + 64, Item.apple));

        } else {
            level.entities.add(new Zombie((tX * 128) + 64, (tY * 128) + 64));
        }


    }
}
