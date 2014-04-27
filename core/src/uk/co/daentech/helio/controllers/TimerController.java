package uk.co.daentech.helio.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import uk.co.daentech.helio.HelioGame;

/**
 * Created by dangilbert on 27/04/2014.
 */
public class TimerController extends Actor {

    private static TimerController instance;

    private float xPos = Gdx.graphics.getWidth() - 150;
    private float yPos = Gdx.graphics.getHeight() - 20;
    private String timeStr = "0:0.0";

    public static TimerController getInstance() {
        if (instance == null) {
            instance = new TimerController();
        }

        return instance;
    }

    private float time;
    private float timeLimit;

    private boolean playerBegun;

    public void act(float delta) {
        if (GameStateController.getInstance().getState() == GameStateController.State.PLAYING) {
            if (playerBegun) {
                time += delta;
                float timeTmp = time * 1000;

                int seconds = (int)(timeTmp / 1000) % 60;
                int minutes = (int)(timeTmp / 1000) / 60;
                int milliseconds = (int)timeTmp % 1000;


                timeStr = timeString(minutes, seconds, milliseconds);
            }
        }
    }

    private String timeString(int minutes, int seconds, int milliseconds) {
        return minutes + ":" + seconds + "." + (milliseconds / 10);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        HelioGame.getInstance().font.draw(batch, timeStr, xPos, yPos);
    }

    public void startTimer() {
        playerBegun = true;
    }

    public void reset() {
        time = 0;
        playerBegun = false;
    }

    public void setTimeLimit(float timeLimit) {
        this.timeLimit = timeLimit;
    }

    public float getTime() {
        return time;
    }

}
