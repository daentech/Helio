package uk.co.daentech.helio.controllers;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dangilbert on 18/04/2014.
 */
public class InputHandler extends InputAdapter {

    private static InputHandler sInstance;

    public boolean touching = false;

    private Vector2 touchStartPos = new Vector2();
    private Vector2 touchPos = new Vector2();
    private OrthographicCamera camera;

    public boolean tapped;
    public boolean dragged;
    public Vector2 tapPos;

    protected InputHandler() {}

    public static InputHandler getInstance() {
        if (sInstance == null) {
            sInstance = new InputHandler();
        }
        return sInstance;
    }

    public void setCamera(OrthographicCamera camera) {
        this.camera = camera;
    }

    public Vector2 dragVector() {
        if (!touching) {
            return Vector2.Zero;
        }
        Vector2 touchChange = new Vector2(touchPos);
        touchChange.sub(touchStartPos);
        touchChange.x = -touchChange.x;
        return touchChange;
    }

    public Vector3 dragVector3() {
        if (!touching) {
            return Vector3.Zero;
        }
        Vector3 touchChange = new Vector3(touchPos.x, touchPos.y, 0);
        touchChange.sub(new Vector3(touchStartPos.x, touchStartPos.y, 0));
        touchChange.x = -touchChange.x;
        return touchChange;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touching = true;
        dragged = false;
        tapped = false;
        tapPos = null;
        touchStartPos.x = screenX;
        touchStartPos.y = screenY;
        touchPos.x = screenX;
        touchPos.y = screenY;
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        touching = false;
        if (!dragged) {
            tapped = true;
            tapPos = new Vector2(screenX, screenY);
        }
        dragged = false;
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        dragged = true;
        tapped = false;
        touchPos.x = screenX;
        touchPos.y = screenY;
        TimerController.getInstance().startTimer();
        return true;
    }

    List<Vector2> points = new ArrayList<Vector2>();

    public List<Vector2> debugPoints() {
        points.clear();
        points.add(touchStartPos);
        points.add(touchPos);
        return points;
    }

    public void handleTap() {
        tapped = false;
        tapPos = null;
    }
}
