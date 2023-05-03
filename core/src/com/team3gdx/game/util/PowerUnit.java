package com.team3gdx.game.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Stores powers and relevant textures, positions, and used by GameScreen to render power ups.
 * @author Neves6
 */
public class PowerUnit {
    private final Texture texture;
    //stores texture for each power and store texture position.
    private final Integer power;
    private final Float x;
    private final Float y;

    private final Float width = 100F;
    private final Float height = 80F;
    private boolean visible = true;

    /**
     * constructur for power unit
     *
     * @author Neves6
     * @param power
     * @param texture
     * @param x
     * @param y
     */
    public PowerUnit(Integer power, Texture texture, Float x, Float y) {
        this.power = power;
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.visible = true;
    }

    /**
     * getter method for returning power
     *
     * @author Neves6
     * @return
     */

    public Integer getPower() {
        return power;
    }

    /**
     * used to render the textures in the right positions
     *
     * @author Neves6
     * @param batch
     */
    public void render(SpriteBatch batch) {
        batch.draw(this.texture, this.x, this.y, 100, 50);
    }

    /**
     * used to set visibility of textures, if set to false then gamescreen.powerunit method will stop rendering
     *
     * @author Neves6
     * @param visible
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * checks for visibility by gamescreen.powerunit
     *
     * @author Neves6
     * @return
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * getter method for returning texture
     *
     * @author Neves6
     * @return
     */

    public Texture getTexture() {
        return texture;
    }

    /**
     * getter method for returning x position
     *
     * @author Neves6
     * @return
     */
    public Float getX() {
        return x;
    }

    /**
     * getter method for returning y positions
     *
     * @author Neves6
     * @return
     */
    public Float getY() {
        return y;
    }

    /**
     * @author Neves6
     * @return boolean if disposed succesfully
     */
    public boolean dispose() {
        texture.dispose();
        return true;
    }

}
