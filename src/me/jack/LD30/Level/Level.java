package me.jack.LD30.Level;

import me.jack.LD30.Entity.DroppedItem;
import me.jack.LD30.Entity.Entity;
import me.jack.LD30.Entity.Player;
import me.jack.LD30.Entity.Zombie;
import me.jack.LD30.InGameState;
import me.jack.LD30.Item.Item;
import me.jack.LD30.Item.ItemStack;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.*;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;
import uk.co.jdpatrick.JEngine.Particle.ParticleSystem;

import java.awt.Point;
import java.awt.Rectangle;
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



    static Tile portal;
    public Camera c;

    public Point level_portal;
    public Point level_key;

    static Image unactive_portal;


    public AStarPathFinder pathFinder = null;

    private int width,height;

    public Point spawn;


    public static void initTiles() {
        water = new Tile(2,0,true);
        grass = new Tile(0,0,false);
        sand = new Tile(1,0,false);

        tree = new Tile(3,0,true);

        portal = new Tile(new Point[]{ new Point(0,2),new Point(1,2),new Point(2,2), new Point(3,2)},false);

        unactive_portal = Tile.sprites.getSprite(3,1);
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

    public void postGeneration(boolean testing, float[][] grass){
        Point spawn = LevelGenerator.findSpawnLocation(this,grass);
        this.spawn = spawn;
        if(!testing) {

            p = new Player((int) spawn.getX() * tileSize, (int) spawn.getY() * tileSize);


            pathFinder = new AStarPathFinder(this, 50, false);
            Random r = new Random();

            for (int i = 0; i != 30; i++) {
                int x = r.nextInt(getWidth());
                int y = r.nextInt(getHeight());

                float tile = getTiles()[x][y];
                float tree = getTrees()[x][y];
                if (tile == 0 && tree != 1) {
                    entities.add(new Zombie(x * tileSize, y * tileSize));
                }

            }

            entities.add(new DroppedItem((int)level_key.getX() * 128,(int)level_key.getY()*128,Item.key));

        }


    }

    public static Player p;
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
    if(p.i.contains(new ItemStack(Item.key,1,null)))
       portal.animation.draw((int)level_portal.getX()*128,(int)level_portal.getY() * 128);
        else{
            g.drawImage(unactive_portal,(float)level_portal.getX()*128,(float)level_portal.getY()*128);
    }
        g.resetTransform();;


    }


    int tick_count;


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
    tick_count++;
        if(tick_count == 5){
            tick_count = 0;
            Random r = new Random();
            int x = r.nextInt(getWidth());
            int y = r.nextInt(getHeight());

            float tile = getTiles()[x][y];
            float tree = getTrees()[x][y];
            if (tile == 0 && tree != 1) {

                Zombie zombie = new Zombie(x * tileSize, y * tileSize);

                if(canMove(x*tileSize,y * tileSize,64,64,zombie))
                entities.add(zombie);
            }

        }
    }

    public void nextLevel(){
        InGameState.next();
    }

    public boolean canMove(int x,int y,int w,int h,Entity caller){
        if( x < 0 || x+64 > level.length*tileSize)return false;
        if(y < 0 || y + 64> level[0].length*tileSize) return false;
        Rectangle r = new Rectangle(x,y,w,h);
        for(Rectangle i : hitboxes){
            if(r.intersects(i) && !(caller instanceof Player))return false;
        }

        for(int xx= 0;xx!= trees.length;xx++){
            for(int yy = 0;yy!= trees[0].length;yy++){
                float t = trees[xx][yy];
                if(t == 1){
                    Rectangle tr = new Rectangle(xx*128,yy*128,128,128);
                    if(r.intersects(tr))return false;
                }
            }
        }

        for(Entity e : entities){
            if(e instanceof Zombie && e != caller){
                Rectangle zombie = new Rectangle(e.getX(),e.getY(),64,64);
                if(r.intersects(zombie))return false;
            }
        }

        if(caller instanceof Zombie){
            Rectangle player = new Rectangle(p.getX(),p.getY(),64,64);
          if(player.intersects(r))  caller.notifyTouchedPlayer(this);
            return !player.intersects(r);
        }

        if(caller instanceof  Player){
            int tileX = x/128;
            int tileY = y/ 128;
            if(tileX == level_portal.x && tileY == level_portal.y){
                if(p.i.contains(new ItemStack(Item.key,1,null))) {
                    nextLevel();
                   for(ItemStack ii : p.i.getItems()){
                       if(ii.getItem().id == 4){
                           ii.removeItem();
                       }
                   }
                }
            }
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

    public ArrayList<Entity> getEntitiesInArea(Circle attackRange) {
        ArrayList<Entity> e = new ArrayList<Entity>();
        System.out.println("Getting entities");
        for(Entity i : entities){
            if(attackRange.intersects(new org.newdawn.slick.geom.Rectangle(i.getX(),i.getY(),64,64))){
                System.out.println("Entity added");
                e.add(i);
            }
        }


        return e;
    }

    public void setPortal(int x, int y) {
        level_portal = new Point(x,y);
    }

    public void setKey(Point point) {
        this.level_key = point;
    }
}
