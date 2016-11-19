package me.veloc1.rglk.game.objects.behavior;

import me.veloc1.rglk.game.Commands;
import me.veloc1.rglk.game.World;
import me.veloc1.rglk.game.objects.BaseObject;

public class InputBehavior extends Behavior<InputBehavior.InputState> {

    public InputBehavior(World world, BaseObject object) {
        super(world, object);
    }

    @Override
    public void processCurrentState() {
        switch (mCurrentState.command) {
            case Commands.UP:
                mWorld.objectIntentsToMove(mObject, 0, -1);
                break;
            case Commands.DOWN:
                mWorld.objectIntentsToMove(mObject, 0, 1);
                break;
            case Commands.LEFT:
                mWorld.objectIntentsToMove(mObject, -1, 0);
                break;
            case Commands.RIGHT:
                mWorld.objectIntentsToMove(mObject, 1, 0);
                break;
        }
    }

    public static class InputState extends State {

        public int command;

        public void setCommand(int inputCommand) {
            command = inputCommand;
        }
    }
}
