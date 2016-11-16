package me.veloc1.rglk;

import dagger.Component;
import me.veloc1.rglk.di.GameModule;

public class Main {

    @Component(modules = {GameModule.class})
    public interface GameDi {
        Game game();
    }

    public static void main(String[] args) {
        Game game = DaggerMain_GameDi.builder().build().game();
        game.run();
    }

}
