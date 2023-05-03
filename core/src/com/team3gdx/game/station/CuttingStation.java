package com.team3gdx.game.station;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.team3gdx.game.food.Ingredient;
import com.team3gdx.game.food.Ingredients;
import com.team3gdx.game.screen.GameScreen;
import com.team3gdx.game.util.Power;

/**
 * A station that slices ingredients.
 *
 * @author Team3Gdx
 * @author Neves6
 */
public class CuttingStation extends Station {
    private final Boolean temp = false;
    private final static Ingredient[] ALLOWED_INGREDIENTS = {Ingredients.lettuce, Ingredients.tomato,
            Ingredients.onion};

    public float currentCutTime;

    public CuttingStation(Vector2 pos) {
        super(pos, 1, false, ALLOWED_INGREDIENTS, "audio/soundFX/chopping.mp3");

    }

    /**
     * Slices the ingredient in slot if cook is interacting.
     *
     * @param batch
     * @param dT
     * @author Team3Gdx
     * @author Neves6
     */
    public boolean interact(SpriteBatch batch, float dT) {
        currentCutTime += dT;
        /**
         * Instantly adds cooked ingredient to held items.
         */
        if (slots.peek().slice(batch, currentCutTime) && Power.getCurrentPower() == "Instant") {
            lockedCook.heldItems.add(slots.pop());
            currentCutTime = 0;
            return true;
        } else if (slots.peek().slice(batch, currentCutTime)) {
            currentCutTime = 0;
            return true;
        }
        return false;
    }


    /**
     * Lock interacting cook at station.
     *
     * @return A boolean indicating if the cook was successfully locked.
     * @author Team3Gdx
     */
    public boolean lockCook() {
        if (!slots.isEmpty()) {
            if (lockedCook == null) {
                GameScreen.cook.locked = true;
                lockedCook = GameScreen.cook;
            } else {
                lockedCook.locked = true;
            }
            slots.peek().slicing = true;

            return true;
        }
        if (lockedCook != null) {
            lockedCook.locked = false;
            lockedCook = null;
            currentCutTime = 0;
        }

        return false;
    }

}
