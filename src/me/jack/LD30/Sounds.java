package me.jack.LD30;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

/**
 * Created by Jack on 24/08/2014.
 */
public class Sounds {


    public static Sound hurt;
    public static Sound portal;
    public static Sound health;


    public static void init() throws SlickException {
        hurt = new Sound("res/hurt.wav");
        portal = new Sound("res/portal.wav");
        health = new Sound("res/health.wav");
    }
}
