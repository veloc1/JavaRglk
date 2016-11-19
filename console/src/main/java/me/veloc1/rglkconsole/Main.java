package me.veloc1.rglkconsole;

import dagger.Component;
import me.veloc1.rglk.Game;

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
