package me.veloc1.rglk.di;

import dagger.Module;
import dagger.Provides;
import me.veloc1.rglk.game.map.mapgeneration.DungeonGenerator;
import me.veloc1.rglk.game.map.mapgeneration.MapGenerator;
import me.veloc1.rglk.game.map.mapgeneration.SimpleRoomGenerator;

@Module
public class WorldModule {

    @Provides
    MapGenerator provideMapGenerator() {
        return new DungeonGenerator();
    }


}
