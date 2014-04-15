package uk.co.daentech.helio.level.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

import uk.co.daentech.helio.HelioGame;
import uk.co.daentech.helio.character.Helicopter;
import uk.co.daentech.helio.level.LevelManager;
import uk.co.daentech.helio.level.Levels;

/**
 * Created by dangilbert on 15/04/2014.
 */
public class BaseGameScreen implements Screen {
    protected OrthographicCamera camera;

    protected HelioGame game;
    protected Helicopter character;

    public BaseGameScreen() {
        // Get game instance
        game = HelioGame.getInstance();

        // Character
        character = new Helicopter();

        // Setup camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.2f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, "Base Game Screen ", 100, 150);
        game.font.draw(game.batch, "Override this for your level", 100, 190);
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
