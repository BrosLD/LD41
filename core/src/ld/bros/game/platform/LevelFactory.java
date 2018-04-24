package ld.bros.game.platform;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class LevelFactory {

    private static final TmxMapLoader MAP_LOADER = new TmxMapLoader();

    private static final String[] levels = {
            "level/test_map_1.tmx",
            "level/2.tmx",
            "level/3.tmx",
            "level/4.tmx"
    };
    private static int currLevel = 0;

    public static TiledMap getNextLevel() {
//        return MAP_LOADER.load("level/test_map_1.tmx");
        TiledMap map = MAP_LOADER.load(levels[currLevel]);
        currLevel++;
        currLevel %= levels.length;

        return map;
    }

    public static void reset() {
        currLevel = 0;
    }
}
