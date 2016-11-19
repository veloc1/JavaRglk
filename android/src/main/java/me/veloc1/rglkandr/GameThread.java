package me.veloc1.rglkandr;

import android.app.Activity;
import me.veloc1.rglk.Game;

public class GameThread extends Thread {

    private final Game mGame;

    public GameThread(Activity activity) {
        super();
        mGame = DaggerGameDi.builder().gameModule(new GameModule(activity)).build().game();
    }

    @Override
    public void run() {
        super.run();
        mGame.run();
    }
}
