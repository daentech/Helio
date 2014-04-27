package uk.co.daentech.helio.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.World;

import uk.co.daentech.helio.base.Entity;
import uk.co.daentech.helio.controllers.CollisionController;
import uk.co.daentech.helio.controllers.GameStateController;
import uk.co.daentech.helio.controllers.InputHandler;

import static com.badlogic.gdx.graphics.Texture.TextureFilter.Linear;

/**
 * Created by dangilbert on 15/04/2014.
 */
public class Helicopter extends Entity {

    private static float anglePerSecond = 50.0f;
    private int direction = 1;

    private static int MAX_HEALTH = 5;
    private static int INITIAL_HEALTH = 3;
    private int health = 3;


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
        this.rotation -= direction * anglePerSecond * deltaTime;
        this.position = this.position.mulAdd(InputHandler.getInstance().dragVector().limit(2f), -0.05f);
    }

    //region Collision

    public void collide() {
        if (CollisionController.getInstance().collidedWith("finish")) {
            // Show level complete
            GameStateController.getInstance().setState(GameStateController.State.COMPLETED);
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
        // Vector between collision points
        //Gdx.app.log("Collision normal vector", CollisionController.getInstance().bestAction.toString());
        this.position.add(CollisionController.getInstance().bestAction);
        this.rotation += direction * anglePerSecond * 0.1;
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

    //region Health Management

    public void decreaseHealth() {
        health--;

        if (health == 0) {
            // Send an event to say we have died
            GameStateController.getInstance().setState(GameStateController.State.DIED);
        }
    }

    public void increaseHealth(int delta) {
        health += delta;

        if(health > MAX_HEALTH ) health = MAX_HEALTH;
    }

    //endregion
}
