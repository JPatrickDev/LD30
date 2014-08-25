package me.jack.LD30.Entity;

import me.jack.LD30.InGameState;
import me.jack.LD30.Item.Inventory;
import me.jack.LD30.Item.Item;
import me.jack.LD30.Item.ItemStack;
import me.jack.LD30.Level.Level;
import me.jack.LD30.Particle.TreeBreakParticle;
import me.jack.LD30.Sounds;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Circle;

import java.util.ArrayList;

/**
 * Created by Jack on 23/08/2014.
 */
public class Player extends Mob {


    public Inventory i = new Inventory();

    public Image player = null;
    public Image damaged = null;
    public Image swimming = null;

    public SpriteSheet sprites;

    public Circle attackDistance = new Circle(50,50, 4*128);

    public Circle attackRange = new Circle(50,50,128);


    public boolean isSwimming = false;


    private Animation run_down = new Animation();
    private Animation run_up = new Animation();
    private Animation run_right = new Animation();
    private Animation run_left = new Animation();

    private Image swim_up;
    private Image swim_down;
    private Image swim_left;
    private Image swim_right;

    public Player(int x, int y) {
        super(x, y);
        this.health = 20;
        try {
            Image spritesheet = new Image("/res/player.png");
            spritesheet.setFilter(Image.FILTER_NEAREST);
            spritesheet = spritesheet.getScaledCopy(4f);
            sprites = new SpriteSheet(spritesheet,64,64);

            player = sprites.getSprite(0,0);
            damaged = sprites.getSprite(3,3);
            swimming = sprites.getSprite(3,2);

            run_down.addFrame(sprites.getSprite(0,0),250);
            run_down.addFrame(sprites.getSprite(1,0),250);
            run_down.setLooping(true);
            run_down.setAutoUpdate(true);

            run_up.addFrame(sprites.getSprite(0,1),250);
            run_up.addFrame(sprites.getSprite(1,1),250);
            run_up.setLooping(true);
            run_up.setAutoUpdate(true);


            run_right.addFrame(sprites.getSprite(0,2),250);
            run_right.addFrame(sprites.getSprite(1,2),250);
            run_right.setLooping(true);
            run_right.setAutoUpdate(true);



            run_left.addFrame(sprites.getSprite(0,3),250);
            run_left.addFrame(sprites.getSprite(1,3),250);
            run_left.setLooping(true);
            run_left.setAutoUpdate(true);

            swim_up = sprites.getSprite(3,3);
            swim_down = sprites.getSprite(3,2);
            swim_right = sprites.getSprite(2,3);
            swim_left = sprites.getSprite(2,2);
        } catch (SlickException e) {
            e.printStackTrace();
        }

        attackDistance.setCenterX(x + 32);
        attackDistance.setCenterY(y + 32);
        attackRange.setCenterX(x + 32);
        attackRange.setCenterY(y + 32);

    }

    private int facing = 0;

    @Override
    public void update(Level level) {

        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            if (level.canMove(x - 3, y, 64, 64,this)) {
                x -= 4;

            }
            facing = 3;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            if (level.canMove(x + 3, y, 64, 64,this)) {
                x += 4;

            }
            facing = 1;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            if (level.canMove(x, y - 3, 64, 64,this)) {
                y -= 4;

            }
            facing = 0;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            if (level.canMove(x, y + 3, 64, 64,this)) {
                y += 4;

            }
            facing = 2;
        }

        attackDistance.setCenterX(x + 32);
        attackDistance.setCenterY(y + 32);

        attackRange.setCenterX(x + 32);
        attackRange.setCenterY(y + 32);

        int tX = getX()/128;
        int tY = getY() /128;
        float t = level.getTiles()[tX][tY];

        if(t ==1){
            isSwimming = true;
        }else{
            isSwimming = false;
        }

    }

    @Override
    public void render(Graphics g) {

    if(!isSwimming) {
        if(facing == 0)run_up.draw(getX(),getY());
        if(facing == 1)run_right.draw(getX(),getY());
        if(facing == 2)run_down.draw(getX(),getY());
        if(facing == 3)run_left.draw(getX(),getY());
    }
    else {
        if (facing == 0) swim_up.draw(getX(), getY());
        if (facing == 1) swim_right.draw(getX(), getY());
        if (facing == 2) swim_down.draw(getX(), getY());
        if (facing == 3) swim_left.draw(getX(), getY());
    }
    }

    @Override
    public void notifyTouchedPlayer(Level parent) {

    }

    public void click(int x, int y, Level level, int button) {
        if(button ==2){
            if(i.contains(new ItemStack(Item.apple,1,null))){
                for(ItemStack iii : i.getItems()){
                    if(iii.getItem().id == Item.apple.id){
                        iii.removeItem();
                        if(health <20){
                            health+=2;
                            Sounds.health.play();
                        }
                    }
                }
            }
            return;
        }
        int tX = (x + level.c.x) / 128;
        int tY = (y + level.c.y) / 128;

        float tile = level.getTiles()[tX][tY];
        float tree = level.getTrees()[tX][tY];



        if (tree == 1) {

            float[][] trees = level.getTrees();
            trees[tX][tY] = 0;
            level.setTrees(trees);

            for (int i = 0; i != 30; i++) {
                level.system.addParticle(new TreeBreakParticle((tX * 128) + 64, (tY * 128) + 64));
            }

            InGameState.score+=5;

            if(i.contains(new ItemStack(Item.woodAxe,1,null))){
                level.entities.add(new DroppedItem((tX * 128) + 64, (tY * 128) + 64, Item.logs));
                level.entities.add(new DroppedItem((tX * 128) + 64, (tY * 128) + 64, Item.logs));
                level.entities.add(new DroppedItem((tX * 128) + 64, (tY * 128) + 64, Item.apple));
            }else{
                level.entities.add(new DroppedItem((tX * 128) + 64, (tY * 128) + 64, Item.logs));
            }


        }
        if(button == -1 || button == 0){


            ArrayList<Entity> attack = level.getEntitiesInArea(attackRange);

            for(Entity e : attack){
                if(e instanceof Zombie){

                    int cX = getX() + 32;
                    int cY = getY() + 32;

                    boolean inRange = false;

                    if(facing == 0 && e.getY()< getY())inRange =true;
                    else if(facing == 1 && e.getX() > getX())inRange = true;
                    else if(facing == 2 && e.getY() > getY()) inRange = true;
                    else if(facing == 3 && e.getX() < getX()) inRange = true;

                    if(!inRange)continue;

                    if(i.contains(new ItemStack(Item.woodSword,1,null))){
                    ((Zombie) e).health-=10;
                    }else{
                        ((Zombie) e).health-=5;
                    }
                    e.knockback(level,2,facing);
                }
            }

        }


    }
}
