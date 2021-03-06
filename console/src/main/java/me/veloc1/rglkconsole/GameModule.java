package me.veloc1.rglkconsole;

import dagger.Module;
import dagger.Provides;
import me.veloc1.rglk.input.GameInput;
import me.veloc1.rglk.output.GameOutput;
import me.veloc1.rglkconsole.console.input.ConsoleInput;
import me.veloc1.rglkconsole.console.output.ConsoleOutput;

@Module
public class GameModule {

    @Provides
    GameInput provideGameInput() {
        return new ConsoleInput();
    }

    @Provides
    GameOutput provideGameOutput() {
        return new ConsoleOutput();
    }

}
