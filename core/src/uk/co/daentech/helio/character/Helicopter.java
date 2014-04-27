package uk.co.daentech.helio.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import uk.co.daentech.helio.base.Entity;
import uk.co.daentech.helio.controllers.CollisionController;
import uk.co.daentech.helio.controllers.GameStateController;
import uk.co.daentech.helio.controllers.InputHandler;

import static com.badlogic.gdx.graphics.Texture.TextureFilter.Linear;
import static uk.co.daentech.helio.controllers.GameStateController.State.COMPLETED;
import static uk.co.daentech.helio.controllers.GameStateController.State.DIED;

/**
 * Created by dangilbert on 15/04/2014.
 */
public class Helicopter extends Entity {

    private static float anglePerSecond = 50.0f;
    private int direction = 1;

    private static int MAX_HEALTH = 5;
    private static int INITIAL_HEALTH = 3;
    private int health = 3;

    private Vector2 target;


    public Helicopter() {
        super(10, -10);

        texture = new Texture(Gdx.files.internal("texture/player.png"));
        sprite = new Sprite(texture);
        sprite.setScale(0.05f);

        texture.setFilter(Linear, Linear);
    }

    @Override
    public void reset() {
        super.reset();
        health = INITIAL_HEALTH;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        switch (GameStateController.getInstance().getState()) {
            case PLAYING:
                this.rotation -= direction * anglePerSecond * deltaTime;
                this.position = this.position.mulAdd(InputHandler.getInstance().dragVector().limit(2f), -0.05f);
                break;
            case COMPLETED:
                this.rotation -= direction * anglePerSecond * deltaTime * 10;
                Gdx.app.log("Character position", this.position.toString());
                Gdx.app.log("Target position", target.toString());
                this.position.lerp(target, 0.1f);
                break;
        }

    }

    //region Collision

    public void collide() {
        if (CollisionController.getInstance().collidedWith("finish")) {
            // Show level complete
            GameStateController.getInstance().setState(COMPLETED);
            return;
        }

        // If we have collided with a wall
        if(CollisionController.getInstance().collidedWith("wall")) {
            decreaseHealth();
            bounceBack();
        }

        // if we have collided with a pickup
        if(CollisionController.getInstance().collidedWith("pickup")) {

        }
        // if we have collided with a spring
        if (CollisionController.getInstance().collidedWith("spring")) {
            flipDirection();
        }

    }

    public void bounceBack() {
        // If no collision, return
        if (CollisionController.getInstance().collidedWith == null) return;
        //TODO bounce away from the wall

        // TODO bounce in the correct direction
        this.rotation += direction * anglePerSecond * 0.5;
    }

    public void flipDirection() {
        direction *= -1;
    }

    //endregion

    @Override
    public void initBodyDef(World world) {
        userDataString = "player";
        super.initBodyDef(world);

    }

    public void setTarget(Vector2 target) {
        this.target = target;
    }

    //region Health Management

    public void decreaseHealth() {
        //health--;

        if (health == 0) {
            // Send an event to say we have died
            GameStateController.getInstance().setState(DIED);
        }
    }

    public void increaseHealth(int delta) {
        health += delta;

        if(health > MAX_HEALTH ) health = MAX_HEALTH;
    }

    //endregion
}
