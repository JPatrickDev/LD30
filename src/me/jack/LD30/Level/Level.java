package me.jack.LD30.Level;

import me.jack.LD30.Entity.Player;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Graphics;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Jack on 23/08/2014.
 */
public class Level {


    private float[][] level;

    private float[][] trees;

    ArrayList<Rectangle> hitboxes = new ArrayList<Rectangle>();

    private int tileSize = 128;

    private int width, height;


    static Tile water;
    static Tile grass;
    static Tile sand;

    static Tile tree;

    private Camera c;


    public static void initTiles() {
        water = new Tile(0, 0,true);
        grass = new Tile(2, 0,false);
        sand = new Tile(1, 0,false);

        tree = new Tile(3,0,true);
    }


    public void setTrees(float[][] trees){
        this.trees = trees;
    }

    public void setTiles(float[][] tiles){
        this.level = tiles;
        hitboxes.clear();
        for(int x= 0;x!= width;x++){
            for(int y= 0;y!= height;y++){
                float t = level[x][y];
                if(t == 1){
                    hitboxes.add(new Rectangle(x*tileSize,y*tileSize,tileSize,tileSize));
                }
            }
        }
    }

    public Level(int width, int height) {
        this.width = width;
        this.height = height;

        c = new Camera(width, height, tileSize, 800, 500);




        p = new Player(0,0);
    }

    public Player p;
    public void render(Graphics g) {


        c.calculate(p.getX(), p.getY());
        g.translate(-c.x, -c.y);
        for (int x = 0; x != width; x++) {
            for (int y = 0; y != height; y++) {
                float tileValue = level[x][y];
                Tile c = null;


                if (tileValue == 1) c = water;
                else if (tileValue == 0.5) c = sand;
                else c = grass;


                g.drawImage(c.sprite, (x * tileSize), (y * tileSize));


                float treeValue = trees[x][y];
                if(treeValue == 1){
                    g.drawImage(tree.sprite,x*tileSize,y*tileSize);
                }
            }
        }
        p.render(g);

        g.resetTransform();;

    }

    public void update(){
        p.update(this);
    }

    public boolean canMove(int x,int y,int w,int h){
        Rectangle r = new Rectangle(x,y,w,h);
        for(Rectangle i : hitboxes){
            if(r.intersects(i))return false;
        }
        return true;
    }

    public float[][] getTrees() {
        return trees;
    }

    public float[][] getTiles() {
        return level;
    }
}
