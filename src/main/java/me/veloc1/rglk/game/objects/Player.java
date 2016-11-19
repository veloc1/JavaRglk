package me.veloc1.rglk.game.objects;

import me.veloc1.rglk.game.World;
import me.veloc1.rglk.game.objects.behavior.InputBehavior;

public class Player extends BaseObject {

    public String lastAction;

    public Player(World world) {
        super(world);
        character = '@';
        attack = 2;
        hp = 10;
        isPlayer = true;

        lastAction = "";

        mBehavior = new InputBehavior(world, this);
    }


}
