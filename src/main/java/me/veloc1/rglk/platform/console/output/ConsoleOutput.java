package me.veloc1.rglk.platform.console.output;

import me.veloc1.rglk.game.World;
import me.veloc1.rglk.game.objects.BaseObject;
import me.veloc1.rglk.game.objects.Player;
import me.veloc1.rglk.output.GameOutput;

public class ConsoleOutput implements GameOutput {

    private static final int OFFSET = 6;

    private int mConsoleWidth;
    private int mConsoleHeight;
    private int mOffsetX;
    private int mOffsetY;

    public ConsoleOutput() {
        mConsoleWidth = 40;
        mConsoleHeight = 20;
        mOffsetX = 0;
        mOffsetY = 0;
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
        if (world.isGameOver()){
            System.out.println("Game over!");
            return;
        }
        Player player = null;
        for (BaseObject baseObject : world.getObjects()) {
            if (baseObject.isPlayer) {
                player = (Player) baseObject;
                if (baseObject.x > mOffsetX + mConsoleWidth - OFFSET) {
                    mOffsetX = baseObject.x - mConsoleWidth + OFFSET;
                } else if (baseObject.x < mOffsetX + OFFSET) {
                    mOffsetX = baseObject.x - OFFSET;
                }
                if (baseObject.y >= mOffsetY + mConsoleHeight - OFFSET) {
                    mOffsetY = baseObject.y - mConsoleHeight + OFFSET;
                } else if (baseObject.y <= mOffsetY + OFFSET) {
                    mOffsetY = baseObject.y - OFFSET;
                }
            }
        }
        if (mOffsetX < 0) {
            mOffsetX = 0;
        }
        if (mOffsetY < 0) {
            mOffsetY = 0;
        }
        StringBuilder builder = new StringBuilder();
        builder.append(player.lastAction + "\n");
        builder.append("HP: " + player.hp + "\n");
        for (int i = mOffsetY; i < mOffsetY + mConsoleHeight; i++) {
            for (int j = mOffsetX; j < mOffsetX + mConsoleWidth; j++) {
                builder.append(world.getCharAtPosition(j, i));
            }
            builder.append('\n');
        }
        System.out.print(builder.toString());
    }

}