package me.jack.LD30.Level;

import me.jack.LD30.Entity.Entity;
import me.jack.LD30.Entity.Player;
import me.jack.LD30.Entity.Zombie;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;
import uk.co.jdpatrick.JEngine.Particle.ParticleSystem;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Jack on 23/08/2014.
 */
public class Level implements TileBasedMap{


    public ParticleSystem system = new ParticleSystem();

    private float[][] level;

    private float[][] trees;

    ArrayList<Rectangle> hitboxes = new ArrayList<Rectangle>();

    public int tileSize = 128;



    public CopyOnWriteArrayList<Entity> entities = new CopyOnWriteArrayList<Entity>();


    static Tile water;
    static Tile grass;
    static Tile sand;

    static Tile tree;

    public Camera c;


    public AStarPathFinder pathFinder = null;

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

        pathFinder = new AStarPathFinder(this,50,false);
        Random r = new Random();

        for(int i = 0;i!= 30;i++) {
            int x = r.nextInt(getWidth());
            int y = r.nextInt(getHeight());

            float tile = getTiles()[x][y];
            float tree = getTrees()[x][y];
            if (tile == 0 && tree != 1) {
                entities.add(new Zombie(x * tileSize, y * tileSize));
            }

        }



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

    @Override
    public int getWidthInTiles() {
        return getWidth();
    }

    @Override
    public int getHeightInTiles() {
        return getHeight();
    }

    @Override
    public void pathFinderVisited(int i, int i2) {

    }

    @Override
    public boolean blocked(PathFindingContext pathFindingContext, int i, int i2) {
        //System.out.println("Checking: " + i +  ":" + i2);
        if(i < 0 || i2 < 0)return false;
        float t = getTiles()[i][i2];
        float tr = getTrees()[i][i2];
        boolean blocked = false;


        if(t == 1)
        {
            blocked = true;
            return blocked;
        }

        if(tr == 1){
            blocked = true;
           // System.out.println("Blocked");
            return blocked;
        }


      //  System.out.println("Not blocked");
        return blocked;


    }

    @Override
    public float getCost(PathFindingContext pathFindingContext, int i, int i2) {
        return 0;
    }
}
