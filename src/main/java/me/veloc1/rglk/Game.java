package me.veloc1.rglk;

import me.veloc1.rglk.game.Commands;
import me.veloc1.rglk.game.World;
import me.veloc1.rglk.input.GameInput;
import me.veloc1.rglk.output.GameOutput;
import me.veloc1.rglk.platform.console.input.ConsoleInput;
import me.veloc1.rglk.platform.console.output.ConsoleOutput;

public class Game {

    private boolean mIsStarted;
    private GameInput mInput;
    private GameOutput mOutput;
    private World mWorld;

    public Game() {
        mIsStarted = true;
        mWorld = new World();
        mOutput = new ConsoleOutput();
        mInput = new ConsoleInput();
    }

    public void run() {
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