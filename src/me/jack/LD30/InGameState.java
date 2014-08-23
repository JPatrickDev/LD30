package me.jack.LD30;

import me.jack.LD30.GUI.*;
import me.jack.LD30.Item.Item;
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


    Level l = LevelGenerator.generateLevel(30,30);

    @Override
    public int getID() {
        return 1;
    }

    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {
        super.mouseClicked(button, x, y, clickCount);
        if(current != null)return;
        if(button == 0){
            l.p.click(x,y,l);
        }
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        Tile.load();
        Level.initTiles();
        HUD.init();
        Item.init();
        Numbers.init();
        Text.init();
    }

    private PopupDialog current = null;
    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        //graphics.scale(4f,4f);

        l.render(graphics);
        HUD.drawHUD(graphics,l);

        if(current != null){
            current.render(graphics);
        }
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
           if(current ==null) l.update(gameContainer);
        if(current != null)current.update(gameContainer,l);
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

        if(key == Keyboard.KEY_RETURN){
            if(current != null){
                current.keyReleased(key);
            }
        }
    }
}
