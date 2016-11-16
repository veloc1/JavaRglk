package me.veloc1.rglk.game;

import dagger.Component;
import me.veloc1.rglk.Game;
import me.veloc1.rglk.di.GameModule;
import me.veloc1.rglk.di.WorldModule;
import me.veloc1.rglk.game.map.Map;

import java.util.ArrayList;

public class World {

    @Component(modules = {WorldModule.class})
    public interface WorldDi {
        Map map();
    }

    private Map                   mMap;
    private ArrayList<BaseObject> mObjects;

    public World() {
        mMap = DaggerWorld_WorldDi.builder().build().map();
        mObjects = new ArrayList<>();

        BaseObject player = new BaseObject();
        player.character = '@';
        player.x = 3;
        player.y = 3;
        mObjects.add(player);
    }

    public void update(int command) {
        switch (command) {
            case Commands.UP:
                objectIntentsToMove(mObjects.get(0), 0, -1);
//                mObjects.get(0).y -= 1;
                break;
            case Commands.DOWN:
                objectIntentsToMove(mObjects.get(0), 0, 1);
//                mObjects.get(0).y += 1;
                break;
            case Commands.LEFT:
                objectIntentsToMove(mObjects.get(0), -1, 0);
//                mObjects.get(0).x -= 1;
                break;
            case Commands.RIGHT:
                objectIntentsToMove(mObjects.get(0), 1, 0);
//                mObjects.get(0).x += 1;
                break;
        }
    }

    private void objectIntentsToMove(BaseObject object, int x, int y) {
        if (mMap.getTileAt(object.x + x, object.y + y).isBlock){
            return;
        } else {
            object.x += x;
            object.y += y;
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