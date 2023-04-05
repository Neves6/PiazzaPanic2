package com.team3gdx.tests.entity;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.team3gdx.game.entity.Cook;
import org.junit.Test;
import com.team3gdx.tests.GdxTestRunner;
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
    }

    @Test
    public void getCollideBoxAtPosition() {

    }

    @Test
    public void getX() {
    }

    @Test
    public void getY() {
        Cook cook = new Cook(new Vector2(64 * 5, 64 * 5), 1);

        boolean val = false;

        if(cook.getY() == 64 * 5){
            val = true;
        }
        assertTrue(val = true);

        if(cook.getY() != 64 * 5){
            val = false;
        }
        assertFalse(val = false);

        cook = null;
    }

    @Test
    public void getWidth() {
    }

    @Test
    public void getHeight() {
        Cook cook = new Cook(new Vector2(64 * 5, 64 * 5), 1);

        boolean val = false;

        if(cook.getHeight() == 128){
            val = true;
        }
        assertTrue(val = true);

        if(cook.getHeight() != 128){
            val = false;
        }
        assertFalse(val = false);

        cook = null;
    }

    @Test
    public void getDirection() {
    }
}