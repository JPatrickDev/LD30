package me.jack.LD30;

import me.jack.LD30.GUI.*;
import me.jack.LD30.Item.Item;
import me.jack.LD30.Level.GenerationOptions;
import me.jack.LD30.Level.Level;
import me.jack.LD30.Level.LevelGenerator;
import me.jack.LD30.Level.Tile;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.*;

/**
 * Created by Jack on 23/08/2014.
 */
public class InGameState extends BasicGameState{


    Level[] levels = new Level[3];


    public static int score = 0;
    public static long startTime = 0;
    public static long timeTaken = 0;
    @Override
    public int getID() {
        return 1;
    }

    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {
        super.mouseClicked(button, x, y, clickCount);
        if(current != null)return;

            getCurrentLevel().p.click(x,y,getCurrentLevel(),button);

    }

    public static int currentLevel = 0;

    public Level getCurrentLevel(){
       return levels[currentLevel];
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        Tile.load();
        Level.initTiles();
        HUD.init();
        Item.init();

        Sounds.init();


        startTime = System.currentTimeMillis();
     }

    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
        super.enter(container, game);
        levels[0] = LevelGenerator.generateLevel(30,30,false,GenerationOptions.BEACHES);


        levels[1] = LevelGenerator.generateLevel(30,30,false, GenerationOptions.MIXED);

        levels[2] = LevelGenerator.generateLevel(30,30,false,GenerationOptions.LARGE_ISLANDS);
        currentLevel = 0;

        score = 0;
        timeTaken =0;

    }

    private PopupDialog current = null;
    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        //graphics.scale(4f,4f);

        getCurrentLevel().render(graphics);
        HUD.drawHUD(graphics,getCurrentLevel());

        Text.drawLarge("Score ",graphics,32,100);
        Text.drawLarge("Time Taken ",graphics,32,150);
        Text.drawLarge("World ",graphics,32,200);

        Numbers.drawLarge(score + "",graphics,116,90);
        Numbers.drawLarge((timeTaken/1000) + "",graphics,200,140);
        Numbers.drawLarge((currentLevel + 1) + "",graphics,128,190);

        if(current != null){
            current.render(graphics);
        }




    }

    static boolean over= false;

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
           if(current ==null) getCurrentLevel().update(gameContainer);
        if(current != null)current.update(gameContainer,getCurrentLevel());
        timeTaken+=i;


        if(getCurrentLevel().p.getHealth() <= 0){
            stateBasedGame.enterState(3);
        }

        if(over){
            stateBasedGame.enterState(3);
        }
    }


    @Override
    public void keyReleased(int key, char c) {
        super.keyReleased(key, c);
        if(c == 'c'){
            if(current == null) {
                try {
                    current = new CraftingDialog();
                } catch (SlickException e) {
                    e.printStackTrace();
                }
            }else{
                current = null;
            }
        }

        if(key == Keyboard.KEY_SPACE){
            getCurrentLevel().p.click(0,0,getCurrentLevel(),-1);
        }

            if(current != null){
                current.keyReleased(key,getCurrentLevel().p);
            }

    }

    public static void next() {
        if(currentLevel != 2) {
            currentLevel++;
        }else{
            over = true;
        }
    }
}
