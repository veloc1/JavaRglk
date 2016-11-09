package me.veloc1.rglk;

public class ConsoleScreen {

    private int mConsoleHeight;

    public ConsoleScreen() {
        mConsoleHeight = 3;
    }

    public void clear() {
        for(int i = 0; i < mConsoleHeight; i++) {
            System.out.println("");
        }
    }

    public void draw() {
        for(int i = 0; i < mConsoleHeight; i++) {
            System.out.println("#");
        }
    }

}