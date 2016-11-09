package me.veloc1.rglk;

import java.io.IOException;
import me.veloc1.rglk.RawConsoleInput;

public class ConsoleInput {

    public ConsoleInput() {

    }

    public void update() {
        try{
            int character = RawConsoleInput.read(true);
            System.out.println(character);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}