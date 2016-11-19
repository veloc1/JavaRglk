package me.veloc1.rglk.game.objects;

import me.veloc1.rglk.game.World;
import me.veloc1.rglk.game.objects.behavior.SimpleAttackBehavior;

public class SimpleMonster extends BaseObject {

    public SimpleMonster(World world) {
        super(world);
        character = 'm';
        attack = 1;
        hp = 3;

        mBehavior = new SimpleAttackBehavior(world, this);

        SimpleAttackBehavior.SimpleAttackState state  = new SimpleAttackBehavior.SimpleAttackState();
        BaseObject                             player = null;
        for (BaseObject object : world.getObjects()) {
            if (object.isPlayer) {
                player = object;
            }
        }
        state.setTarget(player);
        mBehavior.setState(state);
    }

}
