package me.veloc1.rglk.game.objects.behavior;

import me.veloc1.rglk.game.World;
import me.veloc1.rglk.game.objects.BaseObject;

public abstract class Behavior<STATE> {

    protected final BaseObject mObject;
    protected final World      mWorld;
    protected       STATE      mCurrentState;

    public Behavior(World world, BaseObject object) {
        mWorld = world;
        mObject = object;
    }

    public void setState(STATE state) {
        mCurrentState = state;
    }

    public abstract void processCurrentState();
}
