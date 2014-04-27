package uk.co.daentech.helio.controllers;

/**
 * Created by dangilbert on 27/04/2014.
 */
public class GameStateController {

    public boolean is(State state) {
        return this.state == state;
    }

    public static enum State {
        PLAYING,
        PAUSED,
        DIED,
        CUTSCENE,
        COMPLETED
    }

    private State state = State.PLAYING;

    private static GameStateController instance;

    public static GameStateController getInstance() {
        if(instance == null) {
            instance = new GameStateController();
        }
        return instance;
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return this.state;
    }
}
