package uk.co.daentech.helio.level;

import com.badlogic.gdx.Screen;

import java.util.HashMap;

import uk.co.daentech.helio.level.gameplay.BaseGameScreen;
import uk.co.daentech.helio.level.gameplay.Level1;
import uk.co.daentech.helio.level.menu.MainMenuScreen;

/**
 * Created by dangilbert on 15/04/2014.
 */
public class LevelManager {

    static HashMap<Levels, Screen> levels = new HashMap<Levels, Screen>();

    public static Screen loadLevel(Levels level) {

        Screen screen = levels.get(level);

        if (screen != null) return screen;

        switch (level) {
            case MAIN_MENU:
                screen = new MainMenuScreen();
                break;
            case GAME:
                screen = new BaseGameScreen();
                break;
            case LEVEL1:
                screen = new Level1();
        }

        levels.put(level, screen);

        return screen;
    }
}
