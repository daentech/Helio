package uk.co.daentech.helio.level.gameplay;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import uk.co.daentech.helio.level.util.MapDraw;

/**
 * Created by dangilbert on 20/04/2014.
 */
public class Level1 extends BaseGameScreen {

    public Level1() {
        assetManager.load("levels/level1.tmx", TiledMap.class);
        assetManager.finishLoading();
        map = assetManager.get("levels/level1.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(map, 1 / 16f);
        tiledMapRenderer.setView(camera);
    }
}
