package uk.co.daentech.helio.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

import uk.co.daentech.helio.HelioGame;
import uk.co.daentech.helio.base.Entity;
import uk.co.daentech.helio.controllers.InputHandler;

/**
 * Created by dangilbert on 19/04/2014.
 */
public class InputDebug extends Entity {

    ShapeRenderer shapeRenderer = new ShapeRenderer();

    public InputDebug() {
        super(0,0);
    }

    @Override
    public void render() {
        if (!InputHandler.getInstance().touching) return;
        shapeRenderer.setProjectionMatrix(HelioGame.getInstance().batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.WHITE);
        List<Vector2> points = InputHandler.getInstance().debugPoints();
        shapeRenderer.line(points.get(0), points.get(1));
        shapeRenderer.end();
    }
}
