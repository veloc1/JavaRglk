package me.veloc1.rglk.platform.console.output;

import me.veloc1.rglk.game.World;
import me.veloc1.rglk.output.GameOutput;

public class ConsoleOutput implements GameOutput {

    private int mConsoleWidth;
    private int mConsoleHeight;

    public ConsoleOutput() {
        mConsoleWidth = 40;
        mConsoleHeight = 20;
    }

    @Override
    public void clear() {
        // TODO: 13.11.2016 fix this
        // сейчас этот метод занимает некоторое время на отрисовку и картинка ощутимо дерагется
        // выхода два
        // 1) перерисовывать только изменившиеся тайлы
        // 2) определять высоту терминала и перерисовывать исходя из этой высоты
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < mConsoleHeight; i++) {
            builder.append('\n');
        }
        System.out.print(builder.toString());
    }

    @Override
    public void draw(World world) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < mConsoleHeight; i++) {
            for (int j = 0; j < mConsoleWidth; j++) {
                builder.append(world.getCharAtPosition(j, i));
            }
            builder.append('\n');
        }
        System.out.print(builder.toString());
    }

}