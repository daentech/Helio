package uk.co.daentech.helio.level.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

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

    public BaseGameScreen() {
        // Get game instance
        game = HelioGame.getInstance();

        // Character
        character = new Helicopter(70, 70);
        inputDebug = new InputDebug();

        // Setup camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        entities.add(character);
        //entities.add(inputDebug);

        // Setup the input processor
        InputHandler.getInstance().setCamera(camera);
        Gdx.input.setInputProcessor(InputHandler.getInstance());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.2f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        for (Entity entity : entities) {
            entity.update(delta);
        }

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();

        for (Entity entity : entities) {
            entity.render(game.batch);
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
