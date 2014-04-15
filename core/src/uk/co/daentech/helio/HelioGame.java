package uk.co.daentech.helio;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import uk.co.daentech.helio.level.LevelManager;
import uk.co.daentech.helio.level.Levels;
import uk.co.daentech.helio.level.menu.MainMenuScreen;

/**
 * Created by dangilbert on 15/04/2014.
 */
public class HelioGame extends Game {

    public SpriteBatch batch;
    public BitmapFont font;

    private static HelioGame instance;

    public static HelioGame getInstance() {
        if (instance == null) {
            instance = new HelioGame();
        }
        return instance;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();

        font = new BitmapFont();

        setScreen(LevelManager.loadLevel(Levels.MAIN_MENU));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        font.dispose();
    }
}
