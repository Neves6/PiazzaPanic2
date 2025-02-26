package com.team3gdx.tests.util;

import com.badlogic.gdx.math.Rectangle;
import com.team3gdx.game.util.CollisionTile;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CollisionTileTests {

    CollisionTile collisionTile;

    @Test
    public void returnRect() {
        collisionTile = new CollisionTile(10, 20, 30, 40);
        assertEquals(collisionTile.returnRect(), new Rectangle(10, 20, 30, 40));
        collisionTile = null;
    }
}