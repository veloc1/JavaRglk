package me.veloc1.rglk.game;

import java.util.ArrayList;

public class World {

    private Map mMap;
    private ArrayList<BaseObject> mObjects;

    public World() {
        mMap = new Map();
        mObjects = new ArrayList<>();

        BaseObject player = new BaseObject();
        player.character = '@';
        mObjects.add(player);
    }

    public void update(int command) {
        switch (command) {
            case Commands.UP:
                mObjects.get(0).y -= 1;
                break;
            case Commands.DOWN:
                mObjects.get(0).y += 1;
                break;
            case Commands.LEFT:
                mObjects.get(0).x -= 1;
                break;
            case Commands.RIGHT:
                mObjects.get(0).x += 1;
                break;
        }
    }

    public ArrayList<BaseObject> getObjects() {
        return mObjects;
    }

    public char getCharAtPosition(int x, int y) {
        char result = mMap.getCharAtPosition(x, y);
        for (BaseObject object : mObjects) {
            if (object.x == x && object.y == y) {
                return object.character;
            }
        }
        return result;
    }
}