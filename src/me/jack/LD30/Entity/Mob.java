package me.jack.LD30.Entity;

/**
 * Created by Jack on 23/08/2014.
 */
public abstract class Mob extends Entity{

    protected int health;

    public Mob(int x, int y) {
        super(x, y);
    }

    public int getHealth(){
        return health;
    }
}
