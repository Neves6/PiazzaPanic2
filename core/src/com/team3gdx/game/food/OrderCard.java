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
     * @param name - name of the dish represented in the order card
     * @author Neves6
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
     * @return name of the order card
     * @author Neves6
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return texture for the order card
     * @author Neves6
     */
    public Texture getTexture() {
        return this.texture;
    }

    /**
     * @return x position of the order card
     * @author Neves6
     */
    public float getX() {
        return this.orderCardPosX;
    }

    /**
     * @return y position of the order card
     * @author Neves6
     */
    public float getY() {
        return this.orderCardPosY;
    }

    /**
     * @return height of the order card
     * @author Neves6
     */
    public float getHeight() {
        return this.orderCardHeight;
    }

    /**
     * @return width of the order card
     * @author Neves6
     */
    public float getWidth() {
        return this.orderCardWidth;
    }

    /**
     * @return true if the order card has expired, false otherwise
     * @author Neves6
     */
    public Boolean hasExpired() {
        return System.currentTimeMillis() - creationTime >= maxWaitTime;
    }
}
