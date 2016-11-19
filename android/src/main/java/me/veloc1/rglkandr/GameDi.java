package me.veloc1.rglkandr;

import dagger.Component;
import me.veloc1.rglk.Game;

@Component(modules = {GameModule.class})
public interface GameDi {
    Game game();
}