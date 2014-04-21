package uk.co.daentech.helio.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import uk.co.daentech.helio.base.Entity;

/**
 * Created by dangilbert on 21/04/2014.
 */
public class CollisionController implements ContactListener{

    private static CollisionController sInstance;

    protected CollisionController() {}

    public boolean hasCollided;
    public Fixture collidedWith;

    public static CollisionController getInstance() {
        if (sInstance == null) {
            sInstance = new CollisionController();
        }
        return sInstance;
    }

    @Override
    public void beginContact(Contact contact) {
        if (!contact.getFixtureA().getUserData().equals("player")
                && !contact.getFixtureB().getUserData().equals("player")) {
            // We have collided not with the player, so ignore
            return;
        }
        //Gdx.app.log("Collision detected", "A: " + contact.getFixtureA().getUserData() + " B: " + contact.getFixtureB().getUserData());
        hasCollided = true;
        if (contact.getFixtureA().getUserData().equals("player")) {
            collidedWith = contact.getFixtureB();
        } else {
            collidedWith = contact.getFixtureA();
        }
    }

    @Override
    public void endContact(Contact contact) {
        hasCollided = false;
        collidedWith = null;
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
