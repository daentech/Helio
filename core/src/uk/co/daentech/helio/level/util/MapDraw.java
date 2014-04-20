package uk.co.daentech.helio.level.util;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;

import uk.co.daentech.helio.base.Entity;

/**
 * Created by dangilbert on 20/04/2014.
 */
public class MapDraw extends OrthogonalTiledMapRenderer {


    public MapDraw(TiledMap map) {
        super(map);
    }
}
