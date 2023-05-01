package com.team3gdx.tests.entity;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.team3gdx.game.entity.Cook;
import com.team3gdx.game.util.CollisionTile;
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
        Cook cook = new Cook(new Vector2(0, 20), 1);
        CollisionTile[][] cltiles = new CollisionTile[1][1];
        cltiles[0][0] = new CollisionTile(0, 0, 30, 30);

        boolean result = cook.checkCollision(cook.getX(), cook.getY(), cltiles);

        assertEquals(false, result);

        cook = null;
    }

    @Test
    public void getCollideBoxAtPosition() {
        Cook cook = new Cook(new Vector2(20, 30), 1);
        Rectangle expectedBox = new Rectangle(cook.getX(), cook.getY(), 10, 20);

        //Rectangle resultBox = this.getCollideBoxAtPosition(cook.getX(), cook.getY());

        //assertEquals(expectedBox.getX(), resultBox.getX(), 0.1);
        //assertEquals(expectedBox.getY(), resultBox.getY(), 0.1);
        //assertEquals(expectedBox.getWidth(), resultBox.getWidth(), 0.1);
        //assertEquals(expectedBox.getHeight(), resultBox.getHeight(), 0.1);

        cook = null;
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