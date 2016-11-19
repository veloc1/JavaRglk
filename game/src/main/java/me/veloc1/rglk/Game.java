package me.veloc1.rglk;

import me.veloc1.rglk.game.Commands;
import me.veloc1.rglk.game.World;
import me.veloc1.rglk.input.GameInput;
import me.veloc1.rglk.output.GameOutput;

import javax.inject.Inject;

public class Game {

    private boolean    mIsStarted;
    private GameInput  mInput;
    private GameOutput mOutput;
    private World      mWorld;

    @Inject
    public Game(GameInput input, GameOutput output) {
        mIsStarted = true;
        mWorld = new World();
        mInput = input;
        mOutput = output;
    }

    public void run() {
        // initial draw
        mOutput.clear();
        mOutput.draw(mWorld);

        while (mIsStarted) {
            int command = -1;
            command = mInput.update();
            if (command == Commands.EXIT) {
                mIsStarted = false;
                continue;
            }
            mWorld.update(command);

            mOutput.clear();
            mOutput.draw(mWorld);
        }
    }

}