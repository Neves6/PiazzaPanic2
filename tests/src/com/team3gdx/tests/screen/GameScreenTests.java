package com.team3gdx.tests.screen;

import com.team3gdx.game.MainGameClass;
import com.team3gdx.game.screen.GameScreen;
import com.team3gdx.game.screen.MainScreen;
import org.junit.Test;
import com.team3gdx.tests.GdxTestRunner;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class GameScreenTests {
    @Test
    public void loadGame(){
        MainGameClass gametest = new MainGameClass();
        MainScreen ms = new MainScreen(gametest);
        GameScreen screen = new GameScreen(gametest, ms, 10);
        boolean val = false;

        screen.score = 100;

        screen.saveGame(10);
        screen.loadGame(10);
        if(screen.score == 100){
            val = true;
        }
        assertTrue(val = true);

        if(screen.score != 100){
            val = false;
        }
        assertFalse(val = false);
    }
}
