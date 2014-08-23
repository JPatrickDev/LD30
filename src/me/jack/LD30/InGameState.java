package me.jack.LD30;

import me.jack.LD30.GUI.HUD;
import me.jack.LD30.Item.Item;
import me.jack.LD30.Level.Level;
import me.jack.LD30.Level.LevelGenerator;
import me.jack.LD30.Level.Tile;
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
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        //graphics.scale(4f,4f);

        l.render(graphics);
        HUD.drawHUD(graphics,l);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
            l.update(gameContainer);
    }
}
