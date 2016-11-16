package me.veloc1.rglk.game.map;

import me.veloc1.rglk.game.map.mapgeneration.MapGenerator;

import javax.inject.Inject;

public class Map {

    public Tile[][] mTiles;

    @Inject
    public Map(MapGenerator generator) {
        mTiles = generator.generate();
    }

    public char getCharAtPosition(int x, int y) {
        if (x >= mTiles.length || y >= mTiles[0].length){
            return ' ';
        }
        return mTiles[x][y].character;
    }

    public Tile getTileAt(int x, int y) {
        return mTiles[x][y];
    }
}
