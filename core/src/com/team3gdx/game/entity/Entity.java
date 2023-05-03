package com.team3gdx.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Entity
 *
 * @author Team3Gdx
 * @author Neves6
 */
public class Entity {

    public Vector2 pos;

    public Texture texture;

    public float width;
    public float height;

    public float speed;

    float dirX = 0;
    float dirY = 0;

    /**
     * @param batch SpriteBatch on which to draw the entity
     *
     * @author Team3Gdx
     */
    public void draw(SpriteBatch batch) {
        batch.begin();
        batch.draw(texture, pos.x, pos.y, width, height);
        batch.end();
    }

}
