package uk.co.daentech.helio.base;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by dangilbert on 18/04/2014.
 */
public class Entity {

    // These are common things that all moving entities will need in our game
    public Vector2 position;
    public float rotation;
    public Vector2 velocity;

    protected Texture texture;
    protected Sprite sprite;

    public Entity (float x, float y) {
        this.position = new Vector2(x, y);
        this.rotation = 0;
        this.velocity = new Vector2();
    }

    public void update(float deltaTime) {
        if (sprite == null) return;
        sprite.setPosition(position.x, position.y);
        sprite.setRotation(rotation);
    }

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }

}
