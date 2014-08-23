package me.jack.LD30.Level;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import uk.co.jdpatrick.JEngine.Level.Camera;

import java.security.Key;

/**
 * Created by Jack on 23/08/2014.
 */
public class Level {


    private float[][] level;


    private int tileSize = 64;

    private int width, height;


    static Tile water;
    static Tile grass;
    static Tile sand;


    public static void initTiles(){
        water = new Tile(0,0);
        grass = new Tile(2,0);
        sand = new Tile(1,0);
    }


    public Level(int width, int height) {
        level = LevelGenerator.generateLevel(width, height);
        this.width = width;
        this.height = height;
    }



    int px;
    int py;

    public void render(Graphics g) {


        if(Keyboard.isKeyDown(Keyboard.KEY_W)){
            py-=2;
        } if(Keyboard.isKeyDown(Keyboard.KEY_S)){
            py+=2;
        } if(Keyboard.isKeyDown(Keyboard.KEY_A)){
            px-=2;
        } if(Keyboard.isKeyDown(Keyboard.KEY_D)){
            px+=2;
        }



        for (int x = 0; x != width; x++) {
            for (int y = 0; y != height; y++) {
                float tileValue = level[x][y];
                 Tile c = null;


                if (tileValue == 1) c = water;
                else if (tileValue == 0.5) c = sand;
                else c = grass;


                g.drawImage(c.sprite,(x*tileSize),(y*tileSize));


            }
        }

        g.fillRect(px,py,4,4);

    }


}
