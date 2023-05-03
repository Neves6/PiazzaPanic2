package com.team3gdx.game.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Stores powers and relevant textures, positions, and used by GameScreen to render power ups.
 *
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
     * @param power
     * @param texture
     * @param x
     * @param y
     * @author Neves6
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
     * @return
     * @author Neves6
     */

    public Integer getPower() {
        return power;
    }

    /**
     * used to render the textures in the right positions
     *
     * @param batch
     * @author Neves6
     */
    public void render(SpriteBatch batch) {
        batch.draw(this.texture, this.x, this.y, 100, 50);
    }

    /**
     * used to set visibility of textures, if set to false then gamescreen.powerunit method will stop rendering
     *
     * @param visible
     * @author Neves6
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * checks for visibility by gamescreen.powerunit
     *
     * @return
     * @author Neves6
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * getter method for returning texture
     *
     * @return
     * @author Neves6
     */

    public Texture getTexture() {
        return texture;
    }

    /**
     * getter method for returning x position
     *
     * @return
     * @author Neves6
     */
    public Float getX() {
        return x;
    }

    /**
     * getter method for returning y positions
     *
     * @return
     * @author Neves6
     */
    public Float getY() {
        return y;
    }

    /**
     * @return boolean if disposed succesfully
     * @author Neves6
     */
    public boolean dispose() {
        texture.dispose();
        return true;
    }

}
