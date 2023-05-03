package com.team3gdx.game.food;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.team3gdx.game.entity.Entity;
import com.team3gdx.game.screen.GameScreen;
import com.team3gdx.game.screen.GameScreen.STATE;
import com.team3gdx.game.util.Power;

/**
 * Represents an ingredient.
 *
 * @author Team3Gdx
 * @author Neves6
 */
public class Ingredient extends Entity {

    /**
     * Represents internal states of ingredient.
     */
    public int slices;
    private final int idealSlices;
    private float cookedTime;
    private final float idealCookedTime;
    private final BitmapFont font;
    public Status status;

    private final float cutime = width;

    /**
     * Represents ongoing states of the ingredient.
     */
    public boolean cooking;
    public boolean slicing;
    public boolean flipped;
    public boolean mixing;
    private final boolean burn = true; // determines if cooking items can be burnt, can be changed in the future when implementing easy modes
    /**
     * Name of ingredient to get texture.
     */
    public String name;

    private static ShapeRenderer shapeRenderer;
    /**
     * flag used to determine if Instant power is in use.
     */
    private boolean use;

    /**
     * Sets the appropriate properties.
     *
     * @author Team3Gdx
     * @author Neves6
     * @param pos             The (x, y) coordinates of the ingredient.
     * @param width           The ingredient's texture width.
     * @param height          The ingredient's texture height.
     * @param name            The name of the ingredient and texture.
     * @param idealSlices     The ideal number of times to slice the ingredient.
     * @param idealCookedTime The ideal length of time to cook the ingredient.
     */
    public Ingredient(Vector2 pos, float width, float height, String name, int idealSlices, float idealCookedTime) {
        this.pos = pos;
        this.width = width;
        this.height = height;
        this.name = name;
        this.texture = new Texture("items/" + name + ".png");
        this.idealSlices = idealSlices;
        this.idealCookedTime = idealCookedTime;
        this.font = new BitmapFont(Gdx.files.internal("uielements/font.fnt"), Gdx.files.internal("uielements/font.png"),
                false);

        this.slices = 0;
        this.cookedTime = 0;
        this.status = Status.RAW;
        this.cooking = false;
        this.slicing = false;
        this.flipped = false;
        this.mixing = false;
        shapeRenderer = new ShapeRenderer();
        this.use = false;
    }

    /**
     * Creates a new instance with identical properties.
     *
     * @author Team3Gdx
     * @author Neves6
     * @param ingredient The ingredient to clone.
     */
    public Ingredient(Ingredient ingredient) {
        this.pos = ingredient.pos;
        this.width = ingredient.width;
        this.height = ingredient.height;
        this.name = ingredient.name;
        this.texture = ingredient.texture;
        this.idealSlices = ingredient.idealSlices;
        this.idealCookedTime = ingredient.idealCookedTime;
        this.cookedTime = ingredient.cookedTime;
        this.slices = ingredient.slices;
        this.flipped = ingredient.flipped;
        this.status = ingredient.status;
        this.font = ingredient.font;
    }

    /**
     * Changes the {@link this#flipped} to true if possible.
     *
     * @author Team3Gdx
     * @return A boolean representing if the ingredient was successfully flipped.
     */
    public boolean flip() {
        if (cookedTime / idealCookedTime < idealCookedTime * .65f)
            return false;
        return (flipped = true);
    }

    /**
     * Begin process of slicing ingredient and show status.
     *
     * @author Team3Gdx
     * @author Neves6
     * @param batch {@link SpriteBatch} to render texture and status.
     * @param dT    The amount of time to increment by when slicing.
     * @return A boolean representing if current slicing action is complete.
     */
    public boolean slice(SpriteBatch batch, float dT) {
        /**
         * Instantly generates the chopped ingredient, skipping the cutting process.
         */
        if (this.use) {
            slices++;
            texture = new Texture("items/" + name + "_chopped.png"); // changes texture when slicing action is complete
            return true;
        }
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());

        if (dT / width * width <= width) {
            drawStatusBar(dT / width, 0, 1);
        } else {
            slices++;
            texture = new Texture("items/" + name + "_chopped.png"); // changes texture when slicing action is complete
            return true;
        }
        batch.begin();
        font.draw(batch, String.valueOf(slices), pos.x * 64 + 64 + 8, pos.y * 64 + 64 + 16);
        batch.end();
        draw(batch);
        return false;
    }

    BitmapFont flipText = new BitmapFont();

    /**
     * Method is used by station manager to check if power is activated.
     * sets use flag to true if there is Instant power activated.
     *
     * @author Team3Gdx
     * @author Neves6
     * @return boolean True if power is activated the use flag is set to true. False otherwise
     */

    public Boolean PowerChecker() {
        //if there is a power then cooking ingredients are instantly cooked;

        if (Power.getCurrentPower() == "Instant" && idealCookedTime > 0 && !this.use) { // will only be used once since afterwards its going to be empty
            this.use = true;
            return true;
        } else if (Power.getCurrentPower() == "Instant" && idealSlices == 1 && !this.use) {
            this.use = true;
            return true;
        }
        this.use = false;
        return false;
    }

    /**
     * Begin process of cooking ingredient and show status.
     *
     * @author Team3Gdx
     * @author Neves6
     * @param dT    The amount of time to increment {@link this#cookedTime} by.
     * @param batch {@link SpriteBatch} to render texture and status.
     * @return A double representing the current {@link this#cookedTime} and 1 if succesfully cooked
     */
    public double cook(float dT, SpriteBatch batch) {
        /**
         * Instantly generates the cooked ingredient, skipping the cooking process.
         */
        if (this.use) {
            texture = new Texture("items/" + name + "_cooked.png");
            status = Status.COOKED;
            this.use = false;
            this.flipped = true;
            return 1;
        }


        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        if (!flipped && cookedTime / idealCookedTime * width > idealCookedTime * width * .65f) {
            batch.begin();
            flipText.draw(batch, "Flip [f]", pos.x, pos.y);
            batch.end();
        }
        if (cookedTime / idealCookedTime * width <= width) {
            if (GameScreen.state1 == STATE.Continue)
                cookedTime += dT;
            drawStatusBar(cookedTime / idealCookedTime, idealCookedTime * .65f, idealCookedTime * 1.35f);
            if (cookedTime / idealCookedTime * width > idealCookedTime * width * .65f) {
                texture = new Texture("items/" + name + "_cooked.png");
                status = Status.COOKED;
                this.use = false;
                return 1;
            }
        } else {
            if (this.burn) {
                status = Status.BURNED;
                texture = new Texture("items/" + name + "_burned.png");
            }
        }

        draw(batch);
        return cookedTime;
    }


    /**
     * Draw a status bar.
     *
     * @author Team3Gdx
     * @param percentage   The current progress of the status bar.
     * @param optimumLower The bottom of the optimal status to reach bar (shown by a black bar).
     * @param optimumUpper The top of the optimal status to reach bar (shown by a black bar).
     */
    private void drawStatusBar(float percentage, float optimumLower, float optimumUpper) {
        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(pos.x - width / 2, pos.y + height + height / 10, width * 2, height / 4);

        if (percentage * width < optimumLower * width)
            shapeRenderer.setColor(Color.RED);
        else if (percentage * width < optimumUpper * width)
            shapeRenderer.setColor(Color.GREEN);
        else
            shapeRenderer.setColor(Color.RED);

        shapeRenderer.rect(pos.x - width / 2, pos.y + height + height / 10, percentage * width * 2, height / 5);

        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(pos.x - width / 2 + optimumLower * width * 2, pos.y + height + height / 10, height / 10,
                2 * height / 5);
        shapeRenderer.rect(pos.x - width / 2 + optimumUpper * width * 2, pos.y + height + height / 10, height / 10,
                2 * height / 5);
        shapeRenderer.end();
    }

    /**
     * @author Team3Gdx
     * @author Neves6
     * @param o {@link Object} to compare to.
     * @return A boolean representing if the two objects are equal.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Ingredient))
            return false;

        Ingredient compareTo = (Ingredient) o;

        return compareTo.name.equals(name) && compareTo.idealSlices >= idealSlices && compareTo.status == status;
    }

}

/**
 * Enum representing the status of an ingredient.
 *
 * @author Team3Gdx
 * @author Neves6
 */
enum Status {
    RAW, COOKED, BURNED, MIXED
}
