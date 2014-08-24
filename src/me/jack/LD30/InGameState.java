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

/**
 * Created by Jack on 23/08/2014.
 */
public class InGameState extends BasicGameState{


    Level[] levels = new Level[3];
    @Override
    public int getID() {
        return 1;
    }

    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {
        super.mouseClicked(button, x, y, clickCount);
        if(current != null)return;
        if(button == 0){
            getCurrentLevel().p.click(x,y,getCurrentLevel());
        }
    }

    private static int currentLevel = 0;

    public Level getCurrentLevel(){
       return levels[currentLevel];
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        Tile.load();
        Level.initTiles();
        HUD.init();
        Item.init();
        Numbers.init();
        Text.init();

        levels[0] = LevelGenerator.generateLevel(30,30,false,GenerationOptions.BEACHES);
        levels[1] = LevelGenerator.generateLevel(30,30,false, GenerationOptions.WATER);
        levels[2] = LevelGenerator.generateLevel(30,30,false,GenerationOptions.LARGE_ISLANDS);
     }

    private PopupDialog current = null;
    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        //graphics.scale(4f,4f);

        getCurrentLevel().render(graphics);
        HUD.drawHUD(graphics,getCurrentLevel());

        if(current != null){
            current.render(graphics);
        }
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
           if(current ==null) getCurrentLevel().update(gameContainer);
        if(current != null)current.update(gameContainer,getCurrentLevel());
    }


    @Override
    public void keyReleased(int key, char c) {
        super.keyReleased(key, c);
        if(c == 'c'){
            System.out.println("C pressed");
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

            if(current != null){
                current.keyReleased(key,getCurrentLevel().p);
            }

    }

    public static void next() {

        currentLevel++;
    }
}
