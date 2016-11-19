package me.veloc1.rglkconsole.console.input;

import me.veloc1.rglk.game.Commands;
import me.veloc1.rglk.input.GameInput;

import java.io.IOException;

public class ConsoleInput implements GameInput {

    public ConsoleInput() {

    }

    @Override
    public int update() {
        try {
            int character = RawConsoleInput.read(true);
//            System.out.println(character);
            switch (character) {
                case 27:
                    character = Commands.EXIT;
                    break;
                case 57416:
                    character = Commands.UP;
                    break;
                case 57424:
                    character = Commands.DOWN;
                    break;
                case 57419:
                    character = Commands.LEFT;
                    break;
                case 57421:
                    character = Commands.RIGHT;
                    break;
            }
            return character;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

}