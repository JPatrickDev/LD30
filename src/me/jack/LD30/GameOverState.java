package me.jack.LD30;

import me.jack.LD30.GUI.Numbers;
import me.jack.LD30.GUI.Text;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.*;

/**
 * Created by Jack on 25/08/2014.
 */
public class GameOverState extends BasicGameState{
    @Override
    public int getID() {
        return 3;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        InGameState.score =(int)( InGameState.score + ( 500- (InGameState.timeTaken/1000)));
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.drawImage(MenuState.bg,0,0);

        if(InGameState.currentLevel == 2) {
            Text.drawELarge("Victory",graphics,50,50);


            Text.drawLarge("Score ", graphics, 50, 100);

            Numbers.drawLarge((InGameState.score *10) + "", graphics, 114, 100);

            Text.drawLarge("Time taken: ",graphics,50,140);

            Numbers.drawLarge((int)(InGameState.timeTaken/1000) + "",graphics,402,140);
        }else {
            Text.drawLarge("Score ", graphics, 50, 100);

            Numbers.drawLarge((InGameState.score / 10) + "", graphics, 150, 85);


            Text.drawLarge("Time taken: ",graphics,50,140);

            Numbers.drawLarge((int)(InGameState.timeTaken/1000) + "",graphics,230,140);
        }
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        if(Keyboard.isKeyDown(Keyboard.KEY_RETURN)){

        }
    }
}
