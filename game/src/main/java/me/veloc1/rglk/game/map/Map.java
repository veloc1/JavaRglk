package me.veloc1.rglk.game.map;

import me.veloc1.rglk.game.map.mapgeneration.MapGenerator;
import me.veloc1.rglk.game.objects.BaseObject;

import javax.inject.Inject;

public class Map {

    public Tile[][] mTiles;

    @Inject
    public Map(MapGenerator generator) {
        mTiles = generator.generate();
    }

    public char getCharAtPosition(int x, int y) {
        if (x >= mTiles.length || y >= mTiles[0].length) {
            return ' ';
        }
        return mTiles[x][y].character;
    }

    public Tile getTileAt(int x, int y) {
        return mTiles[x][y];
    }

    public int getWidth() {
        return mTiles.length;
    }

    public int getHeight() {
        return mTiles[0].length;
    }

    public void placePlayer(BaseObject player) {
        for (int i = 0; i < mTiles.length; i++) {
            for (int i1 = 0; i1 < mTiles[i].length; i1++) {
                if (mTiles[i][i1].isStartPosition) {
                    player.x = i;
                    player.y = i1;
                }
            }
        }
    }

    public boolean isInRange(int x, int y) {
        if (x < 0) {
            return false;
        }
        if (y < 0) {
            return false;
        }
        if (x >= mTiles[0].length) {
            return false;
        }
        if (y >= mTiles.length) {
            return false;
        }
        return true;
    }
}
