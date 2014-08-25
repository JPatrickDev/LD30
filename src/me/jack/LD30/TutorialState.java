package me.jack.LD30;

import me.jack.LD30.GUI.Text;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by Jack on 25/08/2014.
 */
public class TutorialState extends BasicGameState{

    @Override
    public int getID() {
        return 2;
    }



    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.drawImage(MenuState.bg,0,0);

        Text.drawLarge("Aim of the game",graphics,0,100);
        Text.draw("You have to explore three worlds, finding the portal and the gemstone in each world.", graphics, 0, 140);
        Text.draw("You cannot use the portal until the Gemstone has been collected", graphics, 0, 180);

        Text.drawLarge("Movement",graphics,0,240);
        Text.draw("Use W A S and D to move your player around", graphics, 0, 280);
        Text.draw("Use SPACEBAR or LEFT MOUSE BUTTON to attack\\any Zombies in a small area in front of you will be damaged by the attack", graphics, 0, 320);

        Text.drawLarge("Crafting",graphics,0,380);
        Text.draw("You can break trees by clicking on them", graphics, 0, 420);
        Text.draw("Walk over the dropped items to collect them", graphics, 0, 460);
        Text.draw("Press C in game to bring up the crafting menu",graphics,0,500);
        Text.draw("Use W and S to scroll through the crafting list and press enter to craft an item",graphics,0,540);

        Text.draw("Apples can be used to heal health using the Middle mouse button or scrollwheel",graphics,0,580);

        Text.drawLarge("spacebar to start",graphics,500,450);

    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
            if(Keyboard.isKeyDown(Keyboard.KEY_SPACE))stateBasedGame.enterState(1);
    }
}
