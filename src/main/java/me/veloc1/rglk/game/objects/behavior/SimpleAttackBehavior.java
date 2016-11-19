package me.veloc1.rglk.game.objects.behavior;

import me.veloc1.rglk.game.World;
import me.veloc1.rglk.game.objects.BaseObject;

public class SimpleAttackBehavior extends Behavior<SimpleAttackBehavior.SimpleAttackState> {

    public SimpleAttackBehavior(World world, BaseObject object) {
        super(world, object);
    }

    @Override
    public void processCurrentState() {
        if (mCurrentState != null) {
            if (mCurrentState.target != null) {
                int dx = 0;
                int dy = 0;
                dx = mCurrentState.target.x - mObject.x;
                dy = mCurrentState.target.y - mObject.y;

                if (dx >= -1 && dx <= 1 && dy >= -1 && dy <= 1) {
                    mWorld.objectIntentsToAttack(mObject, mCurrentState.target);
                    return;
                }

                if (dx > 1) {
                    dx = 1;
                } else if (dx < -1) {
                    dx = -1;
                }
                if (dy > 1) {
                    dy = 1;
                } else if (dy < -1) {
                    dy = -1;
                }

                mWorld.objectIntentsToMove(mObject, dx, dy);
            }
        }
    }

    public static class SimpleAttackState extends State {

        public BaseObject target;

        public void setTarget(BaseObject target) {
            this.target = target;
        }
    }

}
