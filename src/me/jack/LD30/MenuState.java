package me.jack.LD30;

import me.jack.LD30.GUI.Numbers;
import me.jack.LD30.GUI.Text;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by Jack on 25/08/2014.
 */
public class MenuState extends BasicGameState{
    @Override
    public int getID() {
        return 0;
    }

    static Image bg;

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        gameContainer.setShowFPS(false);
        bg = new Image("/res/menu.png");

        Numbers.init();
        Text.init();
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        bg.draw(0,0);

        Text.drawELarge("Abandoned",graphics,256,32);

        Text.drawELarge("Press Enter", graphics, 224, 512);

        Text.drawLarge("Made by Jack Patrick for Ludum Dare Thirty",graphics,64,600-16);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        if(Keyboard.isKeyDown(Keyboard.KEY_RETURN)){
            stateBasedGame.enterState(2);
        }
    }
}
