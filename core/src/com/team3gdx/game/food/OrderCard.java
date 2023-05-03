package com.team3gdx.game.food;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;

/**
 * Represents an order card.
 *
 * @author Neves6
 */
public class OrderCard {

    public String name;
    private final String textureFilePath;
    private final Texture texture;
    private final long creationTime;
    private final float maxWaitTime;
    private final HashMap<String, Integer> dishWaitTimes = new HashMap<>() {{
        put("salad", 50000);
        put("burger", 75000);
        put("pizza", 100000);
        put("jacket_potato", 40000);
    }};
    private final float orderCardWidth;
    private final float orderCardHeight;
    private final float orderCardPosX;
    private final float orderCardPosY;

    /**
     * OrderCard constructor
     *
     * @author Neves6
     * @param name - name of the dish represented in the order card
     */
    public OrderCard(String name) {
        this.name = name;
        this.textureFilePath = "uielements/queued" + name.substring(0, 1).toUpperCase() + name.substring(1) + ".png";
        this.texture = new Texture(Gdx.files.internal(textureFilePath));
        this.creationTime = System.currentTimeMillis();
        this.maxWaitTime = dishWaitTimes.get(name);
        this.orderCardHeight = 115;
        this.orderCardWidth = 115;
        this.orderCardPosX = 50;
        this.orderCardPosY = 50;
    }

    /**
     * @author Neves6
     * @return name of the order card
     */
    public String getName() {
        return this.name;
    }

    /**
     * @author Neves6
     * @return texture for the order card
     */
    public Texture getTexture() {
        return this.texture;
    }

    /**
     * @author Neves6
     * @return x position of the order card
     */
    public float getX() {
        return this.orderCardPosX;
    }

    /**
     * @author Neves6
     * @return y position of the order card
     */
    public float getY() {
        return this.orderCardPosY;
    }

    /**
     * @author Neves6
     * @return height of the order card
     */
    public float getHeight() {
        return this.orderCardHeight;
    }

    /**
     * @author Neves6
     * @return width of the order card
     */
    public float getWidth() {
        return this.orderCardWidth;
    }

    /**
     * @author Neves6
     * @return true if the order card has expired, false otherwise
     */
    public Boolean hasExpired() {
        return System.currentTimeMillis() - creationTime >= maxWaitTime;
    }
}
