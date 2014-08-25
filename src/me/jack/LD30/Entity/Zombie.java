package me.jack.LD30.Entity;

import me.jack.LD30.InGameState;
import me.jack.LD30.Level.Level;
import me.jack.LD30.Sounds;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.pathfinding.Path;
import uk.co.jdpatrick.JEngine.Particle.BloodParticle;

import java.awt.*;
import java.util.Random;

/**
 * Created by Jack on 24/08/2014.
 */
public class Zombie extends Mob {

    static Image zombie;

    public Zombie(int x, int y) {
        super(x, y);


        try {
            if (zombie == null) {
                zombie = new Image("res/zombie.png");
                zombie.setFilter(Image.FILTER_NEAREST);
                zombie = zombie.getScaledCopy(4f);
            }
        } catch (SlickException e) {
            e.printStackTrace();
        }

        health = 10;
    }

    Point patrol1;
    Point patrol2;


    boolean nearPlayer = false;



    Path movement;
    int pos = 1;

    @Override
    public void update(Level level) {
        if(cooldown > 0)cooldown--;
        if(health <=0){
            level.entities.remove(this);
            InGameState.score+=10;
            return;
        }
        if (nearPlayer == false) {
            if (patrol1 == null) {
                patrol1 = new Point(getX(), getY());
                patrol2 = getRandomPoint(getX(), getY());
                if (patrol1.x == patrol2.getX() && patrol1.y == patrol2.y) {
                    patrol1 = null;
                    patrol2 = null;
                } else {
                    movement = level.pathFinder.findPath(this, getX() / 128, getY() / 128, (int) patrol2.getX() / 128, (int) patrol2.getY() / 128);

                    int sX = getX() / 128;
                    int sY = getY() / 128;

                    int pX = (int) patrol2.getX() / 128;
                    int pY = (int) patrol2.getY() / 128;

                    if (movement == null) {
                        patrol1 = null;
                        patrol2 = null;
                    } else {
                    }

                }
            } else {
                if (movement != null) {
                    //System.out.println("Attempting to follow path");
                    Path.Step nextStep = movement.getStep(pos);

                    float xSpeed = ((nextStep.getX() * 128) - x);
                    float ySpeed = ((nextStep.getY() * 128) - y);
                    float factor = (float) (2 / Math.sqrt(xSpeed * xSpeed + ySpeed * ySpeed));
                    xSpeed *= factor;
                    ySpeed *= factor;

                    if (level.canMove((int) (x + xSpeed), (int) (y + ySpeed), 64, 64,this)) {
                        x += xSpeed;
                        y += ySpeed;
                    } else {
                        movement = null;
                        patrol1 = null;
                        patrol2 = null;
                        pos = 1;
                    }

                    if (getX() / 128 == nextStep.getX() && getY() / 128 == nextStep.getY()) {

                        pos++;

                        if (pos >= movement.getLength()) {
                            pos = 1;
                            patrol1 = null;
                            patrol2 = null;
                        }

                    }
                }
            }

        } else {
            float xSpeed = ((level.p.getX()) - x);
            float ySpeed = ((level.p.getY()) - y);
            float factor = (float) (2 / Math.sqrt(xSpeed * xSpeed + ySpeed * ySpeed));
            xSpeed *= factor;
            ySpeed *= factor;
            if (level.canMove((int) (x + xSpeed), (int) (y + ySpeed), 62, 62,this)) {
                x += xSpeed;
                y += ySpeed;
            }
        }


        Rectangle z = new Rectangle(getX(), getY(), 62, 62);
        if (level.p.attackDistance.contains(getX(), getY())) {
            nearPlayer = true;
        } else {

            if (patrol1 != null && nearPlayer) {
                pos = 1;
                patrol1 = null;
                patrol2 = null;
            }

            nearPlayer = false;
        }
    }

    int cooldown =0;


    public Point getRandomPoint(int nearX, int nearY) {
        nearX = nearX / 128;
        nearY = nearY / 128;
        Random r = new Random();
        int minValueX;
        int maxValueX;
        if (nearX > 20) {
            minValueX = nearX - 5;
            maxValueX = nearX + 5;
        } else {
            minValueX = 0;
            maxValueX = nearX + 5;
        }
        int minValueY;
        int maxValueY;


        if (nearY > 20) {
            minValueY = nearY - 5;
            maxValueY = nearY + 5;
        } else {
            minValueY = nearY;
            maxValueY = nearY + 5;
        }


        int x = r.nextInt(maxValueX - minValueX) + minValueX;

        int y = r.nextInt(maxValueY - minValueY) + minValueY;
        if (x >= 30) {
            if (nearX != 29) {
                x = 29;
            } else {
                x = 20;
            }
        }


        if (y >= 30) {
            if (nearX != 29) {
                y = 29;
            } else {
                y = 20;
            }
        }

        return new Point(x * 128, y * 128);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(zombie, getX(), getY());
    }



    @Override
    public void notifyTouchedPlayer(Level parent) {
        if(cooldown != 0)return;
            Player p = parent.p;

        for(int i = 0;i != 2;i++)
        parent.system.addParticle(new BloodParticle(p.getX(),p.getY()));

        int direction =0;
        if(getX() > p.getX())direction = 3;
        if(getX() < p.getX()) direction = 1;

        if(getY() > p.getY())direction = 0;
        if(getY() < p.getY())direction = 2;
        p.knockback(parent,1,direction);
        if(p.health - 0.5 >=0) {
            if(p.health - (int)p.health ==0)
            Sounds.hurt.play();;
            p.health -= 0.5;

        }

        cooldown = 60;

    }
}
