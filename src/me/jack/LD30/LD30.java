package me.jack.LD30;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by Jack on 22/08/2014.
 */
public class LD30 extends StateBasedGame {

    public LD30(String name) {
        super(name);
    }

    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        this.addState(new MenuState());
        this.addState(new InGameState());
        this.addState(new TutorialState());
        this.addState(new GameOverState());
    }
}
