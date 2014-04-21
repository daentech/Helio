package uk.co.daentech.helio.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.World;

import uk.co.daentech.helio.HelioGame;
import uk.co.daentech.helio.base.Entity;
import uk.co.daentech.helio.controllers.InputHandler;

import static com.badlogic.gdx.graphics.Texture.TextureFilter.Linear;

/**
 * Created by dangilbert on 15/04/2014.
 */
public class Helicopter extends Entity {

    private static float anglePerSecond = 50.0f;

    public Helicopter() {
        super(10, 40);

        texture = new Texture(Gdx.files.internal("texture/player.png"));
        sprite = new Sprite(texture);
        sprite.setScale(0.05f);

        texture.setFilter(Linear, Linear);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        this.rotation -= anglePerSecond * deltaTime;
        this.position = this.position.mulAdd(InputHandler.getInstance().dragVector().limit(2f), -0.05f);
    }
}
