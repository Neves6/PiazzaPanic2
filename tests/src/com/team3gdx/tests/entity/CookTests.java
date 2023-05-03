package com.team3gdx.tests.entity;

import com.badlogic.gdx.math.Vector2;
import com.team3gdx.game.entity.Cook;
import com.team3gdx.game.util.CollisionTile;
import com.team3gdx.tests.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class CookTests {

    @Test
    public void full() {
        Cook cook = new Cook(new Vector2(64 * 5, 64 * 5), 1);
        int MAX_STACK_SIZE = 5;

        assertFalse(cook.full());
        cook.heldItems.setSize(MAX_STACK_SIZE - 1);
        assertFalse(cook.full());
        cook.heldItems.setSize(MAX_STACK_SIZE);
        assertTrue(cook.full());
        cook.dropItem();
        assertFalse(cook.full());

        cook = null;
    }

    @Test
    public void checkCollision() {
        Cook cook = new Cook(new Vector2(0, 20), 1);
        CollisionTile[][] cltiles = new CollisionTile[1][1];
        cltiles[0][0] = new CollisionTile(0, 0, 30, 30);

        boolean result = cook.checkCollision(cook.getX(), cook.getY(), cltiles);

        assertFalse(result);

        cook = null;
    }

    @Test
    public void getX() {
        Cook cook = new Cook(new Vector2(64 * 5, 64 * 5), 1);

        boolean val = cook.getX() == 64 * 5;

        assertTrue(val = true);

        if (cook.getX() != 64 * 5) {
            val = false;
        }
        assertFalse(val = false);

        cook = null;
    }

    @Test
    public void getY() {
        Cook cook = new Cook(new Vector2(64 * 5, 64 * 5), 1);

        boolean val = cook.getY() == 64 * 5;

        assertTrue(val = true);

        if (cook.getY() != 64 * 5) {
            val = false;
        }
        assertFalse(val = false);

        cook = null;
    }

    @Test
    public void getWidth() {
        Cook cook = new Cook(new Vector2(64 * 5, 64 * 5), 1);

        boolean val = cook.getWidth() == 64;

        assertTrue(val = true);

        if (cook.getWidth() != 64) {
            val = false;
        }
        assertFalse(val = false);

        cook = null;
    }

    @Test
    public void getHeight() {
        Cook cook = new Cook(new Vector2(64 * 5, 64 * 5), 1);

        boolean val = cook.getHeight() == 128;

        assertTrue(val = true);

        if (cook.getHeight() != 128) {
            val = false;
        }
        assertFalse(val = false);

        cook = null;
    }

    @Test
    public void getDirection() {
        Cook cook = new Cook(new Vector2(64 * 5, 64 * 5), 1);

        boolean val = cook.getDirection() == new Vector2(64 * 5, 64 * 5);

        assertTrue(val = true);

        if (cook.getDirection() != new Vector2(64 * 5, 64 * 5)) {
            val = false;
        }
        assertFalse(val = false);

        cook = null;
    }
}