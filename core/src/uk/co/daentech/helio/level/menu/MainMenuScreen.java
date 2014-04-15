package uk.co.daentech.helio.level.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import uk.co.daentech.helio.HelioGame;
import uk.co.daentech.helio.level.LevelManager;
import uk.co.daentech.helio.level.Levels;

/**
 * Created by dangilbert on 15/04/2014.
 */
public class MainMenuScreen implements Screen {

    OrthographicCamera camera;
    Viewport viewport;

    HelioGame game;

    public MainMenuScreen() {
        // Get game instance
        game = HelioGame.getInstance();

        // Setup camera
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(800, 480, camera);
        camera.setToOrtho(false, 800, 480);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, "Welcome to Helio!!! ", 100, 150);
        game.font.draw(game.batch, "Tap anywhere to begin!", 100, 100);
        game.batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(LevelManager.loadLevel(Levels.GAME));
            dispose();
        }
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
