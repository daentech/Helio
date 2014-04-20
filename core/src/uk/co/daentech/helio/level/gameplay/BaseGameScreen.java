package uk.co.daentech.helio.level.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

import uk.co.daentech.helio.HelioGame;
import uk.co.daentech.helio.base.Entity;
import uk.co.daentech.helio.character.Helicopter;
import uk.co.daentech.helio.controllers.InputHandler;
import uk.co.daentech.helio.level.LevelManager;
import uk.co.daentech.helio.level.Levels;
import uk.co.daentech.helio.utils.InputDebug;

/**
 * Created by dangilbert on 15/04/2014.
 */
public class BaseGameScreen implements Screen {
    protected OrthographicCamera camera;

    protected HelioGame game;
    protected Helicopter character;
    protected InputDebug inputDebug;

    protected List<Entity> entities = new ArrayList<Entity>();

    protected TiledMap map;
    protected TiledMapRenderer tiledMapRenderer;
    protected AssetManager assetManager;

    private Vector2 unitVector = new Vector2(1,1);

    public BaseGameScreen() {
        // Get game instance
        game = HelioGame.getInstance();

        // Character
        character = new Helicopter();
        inputDebug = new InputDebug();

        // Setup camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 30, 20);
        //camera.translate(-5, 80);
        entities.add(character);
        //entities.add(inputDebug);

        // Setup the input processor
        InputHandler.getInstance().setCamera(camera);
        Gdx.input.setInputProcessor(InputHandler.getInstance());

        assetManager = new AssetManager();
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.2f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        for (Entity entity : entities) {
            entity.update(delta);
        }

        camera.position.set(character.position.x + 5, character.position.y + 50, 0);
        camera.update();

        if (tiledMapRenderer != null) {
            tiledMapRenderer.setView(camera);
            tiledMapRenderer.render();
        }

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        for (Entity entity : entities) {
            entity.render();
        }

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
