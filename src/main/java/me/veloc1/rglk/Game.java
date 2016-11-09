package me.veloc1.rglk;

import me.veloc1.rglk.ConsoleScreen;
import me.veloc1.rglk.ConsoleInput;

public class Game {

    private boolean mIsStarted;
    private ConsoleInput mInput;
    private ConsoleScreen mScreen;

    public Game() {
        mIsStarted = true;
        mScreen = new ConsoleScreen();
        mInput = new ConsoleInput();
    }

    public void run() {
        while(mIsStarted){
            mScreen.clear();
            mInput.update();
            mScreen.draw();
        }
    }

}