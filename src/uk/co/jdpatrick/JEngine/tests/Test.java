package uk.co.jdpatrick.JEngine.tests;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import uk.co.jdpatrick.JEngine.JEngineGame;
import uk.co.jdpatrick.JEngine.Level.TiledLevel;
import uk.co.jdpatrick.JEngine.Particle.BloodParticle;
import uk.co.jdpatrick.JEngine.Particle.ParticleSystem;
import uk.co.jdpatrick.JEngine.ui.Elements.BasicButton;
import uk.co.jdpatrick.JEngine.ui.OnClickListener;
import uk.co.jdpatrick.JEngine.ui.UIElement;

/**
 * @author Jack Patrick
 */
public class Test extends JEngineGame {

    TiledLevel level;

    public Test(String title) {
        super(title);
    }

    ParticleSystem p = new ParticleSystem();

    BasicButton b;
    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        level = new TiledLevel("/res/level.png");
      addElement(new BasicButton(50,50,200,55,"Test").setOnClickListener(new OnClickListener() {
          @Override
          public void onPress(UIElement element) {
              System.out.println("Press");
          }

          @Override
          public void onRelease(UIElement element) {
              System.out.println("Release");
          }

          @Override
          public void onClick(UIElement element) {
            System.out.println("Click");
          }
      }));


    }

    int pX = 0;
    int pY = 0;

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {
        super.update(gameContainer,i);
        if (Mouse.isButtonDown(0)) {
            p.addParticle(new BloodParticle(pX, pY));
        }

        p.update();

        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            pY-=5;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            pX-=5;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            pY+=5;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            pX+=5;
        }

        System.out.println(pX + ":" + pY);


        level.c.center(pX, pY, level.getWidth(), level.getHeight());
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {

        level.render(graphics);
        p.render(graphics, level.c.getX(), level.c.getY());
        super.render(gameContainer,graphics);

    }
}
