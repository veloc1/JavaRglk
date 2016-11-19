package me.veloc1.rglk.game;

import dagger.Component;
import me.veloc1.rglk.di.WorldModule;
import me.veloc1.rglk.game.map.Map;
import me.veloc1.rglk.game.objects.BaseObject;
import me.veloc1.rglk.game.objects.Player;
import me.veloc1.rglk.game.objects.SimpleMonster;
import me.veloc1.rglk.game.objects.behavior.InputBehavior;

import java.util.ArrayList;
import java.util.Random;

public class World {

    public boolean isGameOver() {
        return mIsGameOver;
    }

    @Component(modules = {WorldModule.class})
    public interface WorldDi {
        Map map();
    }

    private boolean               mIsGameOver;
    private Map                   mMap;
    private ArrayList<BaseObject> mObjects;

    public World() {
        mMap = DaggerWorld_WorldDi.builder().build().map();
        mObjects = new ArrayList<>();

        BaseObject player = new Player(this);
        player.x = 3;
        player.y = 3;
        mObjects.add(player);

        for (int i = 0; i < 5; i++) {
            BaseObject monster = new SimpleMonster(this);
            monster.x = new Random().nextInt(mMap.getWidth() - 2) + 1;
            monster.y = new Random().nextInt(mMap.getHeight() - 2) + 1;
            mObjects.add(monster);
        }
    }

    public void update(int inputCommand) {
        for (BaseObject object : mObjects) {
            if (object.getBehavior() instanceof InputBehavior) {
                InputBehavior.InputState state = new InputBehavior.InputState();
                state.setCommand(inputCommand);
                object.getBehavior().setState(state);
            }
            object.update();
        }
        for (int i = 0; i < mObjects.size(); i++) {
            if (!mObjects.get(i).isAlive) {
                mObjects.get(i).die();
                mObjects.remove(i);
                i--;
            }
        }
    }

    public void objectIntentsToMove(BaseObject object, int x, int y) {
        if (mMap.getTileAt(object.x + x, object.y + y).isBlock) {
            return;
        } else {
            for (BaseObject other : mObjects) {
                if (!(other.equals(object)) && object.x + x == other.x && object.y + y == other.y) {
                    objectIntentsToAttack(object, other);
                    return;
                }
            }
            object.x += x;
            object.y += y;
        }
    }

    public void objectIntentsToAttack(BaseObject object, BaseObject target) {
        target.hp -= object.attack;
        if (target.hp <= 0) {
            if (target.isPlayer) {
                mIsGameOver = true;
            } else {
                target.isAlive = false;
            }
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