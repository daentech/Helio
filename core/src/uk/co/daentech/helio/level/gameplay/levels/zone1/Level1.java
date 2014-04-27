package uk.co.daentech.helio.level.gameplay.levels.zone1;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import uk.co.daentech.helio.level.gameplay.BaseGameScreen;


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

        setupWalls();
        setupStartAndEnd();
        character.setTarget(end);
    }
}
