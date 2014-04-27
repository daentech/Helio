package uk.co.daentech.helio.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import uk.co.daentech.helio.HelioGame;

import static uk.co.daentech.helio.controllers.GameStateController.State.PAUSED;
import static uk.co.daentech.helio.controllers.GameStateController.State.PLAYING;

/**
 * Created by dangilbert on 27/04/2014.
 */
public class OSDController {

    private static OSDController instance;

    private Image pause;

    public static OSDController getInstance() {
        if (instance == null) {
            instance = new OSDController();
            instance.init();
        }

        return instance;
    }

    public Stage stage;

    private void init() {
        stage = new Stage(new ExtendViewport(640, 320), HelioGame.getInstance().batch);
        float screenWidth = Gdx.graphics.getWidth() - 40;
        float screenHeight = Gdx.graphics.getHeight() - 40;
        pause = new Image(TextureManager.pauseButton);
        pause.setPosition(screenWidth, screenHeight);
        pause.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (GameStateController.getInstance().is(PAUSED)) {
                    GameStateController.getInstance().setState(PLAYING);
                } else {
                    GameStateController.getInstance().setState(PAUSED);
                }

            }
        });
        stage.addActor(pause);

        stage.addActor(TimerController.getInstance());

    }

    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

}
