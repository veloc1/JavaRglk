package me.veloc1.rglk.game.map.mapgeneration;

import me.veloc1.rglk.game.map.Tile;

public class SimpleRoomGenerator implements MapGenerator {
    @Override
    public Tile[][] generate() {
        int      width  = 48;
        int      height = 24;
        Tile[][] result = new Tile[width][height];

        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                result[i][j] = new Tile();
                result[i][j].x = j;
                result[i][j].y = i;
                if (j == 0 || j == result[i].length - 1 || i == 0 || i == result.length - 1) {
                    result[i][j].isBlock = true;
                    result[i][j].character = '#';
                } else {
                    result[i][j].character = '.';
                }
            }
        }

        return result;
    }
}
