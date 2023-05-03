package com.team3gdx.game.station;

import com.badlogic.gdx.math.Vector2;
import com.team3gdx.game.food.Ingredient;
import com.team3gdx.game.food.Ingredients;

/**
 * A station that bakes ingredients.
 *
 * @author Team3Gdx
 */
public class BakingStation extends CookingStation {

    private final static Ingredient[] ALLOWED_INGREDIENTS = {Ingredients.bun, Ingredients.cooked_bun};

    /**
     * Creates a new baking station.
     *
     * @param pos The position of the station.
     * @author Team3Gdx
     */
    public BakingStation(Vector2 pos) {
        super(pos, 4, ALLOWED_INGREDIENTS, "particles/smokes.party", "audio/soundFX/frying.mp3");
    }

    /**
     * @param ingredient The ingredient to be placed.
     * @return True if the ingredient was placed, false otherwise.
     * @author Team3Gdx
     */
    @Override
    public boolean place(Ingredient ingredient) {
        if (super.place(ingredient)) {
            ingredient.flipped = true;
            return true;
        }

        return false;
    }

}
