package uk.co.daentech.helio.base;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import uk.co.daentech.helio.HelioGame;

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

    protected BodyDef bodydef = new BodyDef();
    protected FixtureDef spriteShapeDef = new FixtureDef();
    protected Body spriteBody;

    public Entity (float x, float y) {
        this.position = new Vector2(x, y);
        this.rotation = 0;
        this.velocity = new Vector2();
    }

    public void update(float deltaTime) {
        if (sprite == null) return;
        sprite.setPosition(position.x, position.y);
        sprite.setRotation(rotation);

        if (spriteBody == null) return;
        spriteBody.setTransform(position.x + 2.5f, position.y + 50.0f, rotation* MathUtils.degreesToRadians);
    }

    public void render() {
        sprite.draw(HelioGame.getInstance().batch);
    }

    public void initBodyDef(World world) {
        // If we have no sprite, we can't collide
        if (sprite == null) return;

        bodydef.angle = sprite.getRotation();
        bodydef.type = BodyDef.BodyType.KinematicBody;

        spriteBody = world.createBody(bodydef);

        PolygonShape spriteShape = new PolygonShape();
        spriteShape.setAsBox(sprite.getWidth() * 0.02f, sprite.getHeight() * 0.025f);

        spriteShapeDef.shape = spriteShape;
        spriteShapeDef.density = 10.0f;
        spriteShapeDef.isSensor = true;

        spriteBody.createFixture(spriteShapeDef);
    }

}
