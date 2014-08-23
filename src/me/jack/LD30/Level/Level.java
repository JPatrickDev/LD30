package me.jack.LD30.Level;

import me.jack.LD30.Entity.Entity;
import me.jack.LD30.Entity.Player;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import uk.co.jdpatrick.JEngine.Particle.ParticleSystem;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Jack on 23/08/2014.
 */
public class Level {


    public ParticleSystem system = new ParticleSystem();

    private float[][] level;

    private float[][] trees;

    ArrayList<Rectangle> hitboxes = new ArrayList<Rectangle>();

    private int tileSize = 128;



    public CopyOnWriteArrayList<Entity> entities = new CopyOnWriteArrayList<Entity>();


    static Tile water;
    static Tile grass;
    static Tile sand;

    static Tile tree;

    public Camera c;


    private int width,height;

    public Point spawn;


    public static void initTiles() {
        water = new Tile("/res/water.png",true);
        grass = new Tile("/res/grass.png",false);
        sand = new Tile("/res/sand.png",false);

        tree = new Tile("/res/tree.png",true);
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





    }

    public void postGeneration(){
        Point spawn = LevelGenerator.findSpawnLocation(this);
        p = new Player((int)spawn.getX() * tileSize,(int)spawn.getY() * tileSize);
        this.spawn = spawn;
    }

    public Player p;
    public void render(Graphics g) {



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

        for(Entity e : entities){
            e.render(g);
        }
        p.render(g);

        system.render(g,0,0);
        g.resetTransform();;

    }

    public void update(GameContainer gc){
        p.update(this);
        c.calculate(p.getX(), p.getY());
        system.update();
        Rectangle player = new Rectangle(p.getX(),p.getY(),64,64);
        for(Entity e: entities){
            e.update(this);
            Rectangle rect = new Rectangle(e.getX(),e.getY(),64,64);
            if(rect.intersects(player)){
                e.steppedOn(p,this);
            }
        }

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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
