package me.jack.LD30.Entity;

import me.jack.LD30.Level.Level;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.util.pathfinding.Path;

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
                zombie = new Image("/res/zombie.png");
                zombie.setFilter(Image.FILTER_NEAREST);
                zombie = zombie.getScaledCopy(4f);
            }
        } catch (SlickException e) {
            e.printStackTrace();
        }

    }

    Point patrol1;
    Point patrol2;


    boolean nearPlayer = false;


    Path movement;
    int pos = 1;
    @Override
    public void update(Level level) {

        //todo Select two points near Zombie, patrol between them, If player comes within 5 tiles of Zombie, move towards him
        //todo Don't try to use a star to attack player, zombies are not clever

        if(nearPlayer == false) {
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
                    System.out.println(sX + ":" + sY);
                    System.out.println(pX + ":" + pY);

                    if (movement == null) {
                        System.out.println("No path found");
                        patrol1 = null;
                        patrol2 = null;
                    } else {
                        System.out.println("Patrol path found");
                    }

                }
            } else {
                if (movement != null) {
                    //System.out.println("Attempting to follow path");
                    Path.Step nextStep = movement.getStep(pos);

                    /// System.out.println("At: " + (getX()/128) + "-" + (getY()/128));
                    //System.out.println("Next: " + nextStep.getX() + "-" + nextStep.getY());


                    if (getX() > nextStep.getX() * 128) {
                        System.out.println(" X >");
                        x -= 3;
                    }
                    if (getX() < nextStep.getX() * 128) {
                        System.out.println(" X <");
                        x += 3;
                    }

                    if (getY() > nextStep.getY() * 128) {

                        System.out.println(" Y >");

                        y -= 3;
                    }

                    if (getY() < nextStep.getY() * 128) {

                        System.out.println(" Y <");
                        y += 3;
                    }

                    if (getX() / 128 == nextStep.getX() && getY() / 128 == nextStep.getY()) {
                        System.out.println("Close, moving to next step");
                        pos++;

                        if (pos >= movement.getLength()) {
                            System.out.println("Patrol point reached, turning");
                            pos = 1;
                            patrol1 = null;
                            patrol2 = null;
                        }

                    }
                }
            }

        }else{
            if(getX() > level.p.getX()){
                x-=3;
            }

            if(getX() < level.p.getX()){
                x+=3;
            }

            if(getY() > level.p.getY()){
                y -=3;
            }

            if(getY() < level.p.getY()){
                y+=3;
            }
        }



        Rectangle z = new Rectangle(getX(),getY(),64,64);
        if(level.p.attackDistance.contains(getX(),getY())){
            nearPlayer= true;
        }else{
            nearPlayer = false;
        }
    }


    public Point getRandomPoint(int nearX,int nearY){
        nearX = nearX/128;
        nearY = nearY/128;
        Random r = new Random();
        int minValueX;
        int maxValueX;
        if(nearX > 20) {
             minValueX = nearX - 5;
             maxValueX = nearX + 5;
        }else{
            minValueX = 0;
            maxValueX = nearX + 5;
        }
        int minValueY;
        int maxValueY;


        if(nearY > 20) {
            minValueY = nearY - 5;
            maxValueY = nearY + 5;
        }else{
            minValueY = nearY;
            maxValueY = nearY + 5;
        }


        int x = r.nextInt(maxValueX -minValueX) + minValueX;

        int y = r.nextInt(maxValueY -minValueY) + minValueY;
        if(x >=30){
            if(nearX != 29){
                x= 29;
            }else{
                x = 20;
            }
        }


        if(y >=30){
            if(nearX != 29){
                y= 29;
            }else{
                y = 20;
            }
        }

        System.out.println("Point created: " + x + "-" + y);
        return new Point(x * 128,y*128);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(zombie, getX(), getY());
        if(patrol2 != null) {
            g.fillRect((int) patrol2.getX(), (int) patrol2.getY(), 128, 128);
            g.setColor(Color.green);
            g.fillRect(movement.getX(pos)*128,movement.getY(pos) * 128,128,128);
        }
    }
}
