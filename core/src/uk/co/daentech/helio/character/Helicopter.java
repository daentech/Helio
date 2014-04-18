package uk.co.daentech.helio.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import uk.co.daentech.helio.base.Entity;

import static com.badlogic.gdx.graphics.Texture.TextureFilter.Linear;

/**
 * Created by dangilbert on 15/04/2014.
 */
public class Helicopter extends Entity {

    private static float anglePerSecond = 50.0f;

    public Helicopter(float x, float y) {
        super(x, y);

        texture = new Texture(Gdx.files.internal("texture/player.png"));
        sprite = new Sprite(texture);

        texture.setFilter(Linear, Linear);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        this.rotation -= anglePerSecond * deltaTime;
    }
}
