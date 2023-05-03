package com.team3gdx.game.util;

import com.badlogic.gdx.math.Rectangle;

/**
 * Holds data for a tile that can be collided with
 *
 * @author Team3Gdx
 */
public class CollisionTile {
    int tilex;
    int tiley;
    int tileheight;
    int tilewidth;

    /**
     *
     * @author Team3Gdx
     * @param x   X position of the tile
     * @param y   Y position of the tile
     * @param wid Width of the tile
     * @param hi  Height of the tile
     */
    public CollisionTile(int x, int y, int wid, int hi) {
        tilex = x;
        tiley = y;
        tileheight = hi;
        tilewidth = wid;
    }

    /**
     * @return Rectangle covering the tile
     * @author Team3Gdx
     */
    public Rectangle returnRect() {
        return new Rectangle(tilex, tiley, tilewidth, tileheight);
    }
}
