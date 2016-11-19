package me.veloc1.rglk.game.objects;

import me.veloc1.rglk.game.World;
import me.veloc1.rglk.game.objects.behavior.Behavior;

public abstract class BaseObject {

    public int     x;
    public int     y;
    public char    character;
    public int     hp;
    public int     attack;
    public boolean isAlive;

    public    boolean  isPlayer;
    protected World    mWorld;
    protected Behavior mBehavior;

    public BaseObject(World world) {
        mWorld = world;

        isAlive = true;
    }

    public Behavior getBehavior() {
        return mBehavior;
    }

    public void update() {
        if (mBehavior != null) {
            mBehavior.processCurrentState();
        }
    }

    public void die() {

    }
}