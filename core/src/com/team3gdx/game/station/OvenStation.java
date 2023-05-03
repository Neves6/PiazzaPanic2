package com.team3gdx.game.station;

import com.badlogic.gdx.math.Vector2;
import com.team3gdx.game.food.Ingredient;
import com.team3gdx.game.food.Ingredients;

/**
 * A station that cooks ingredients.
 *
 * @author Neves6
 */
public class OvenStation extends CookingStation {
    private final static Ingredient[] ALLOWED_INGREDIENTS = {Ingredients.cooked_pizza,Ingredients.cooked_pizza2, Ingredients.burnt_pizza, Ingredients.raw_pizza, Ingredients.potato, Ingredients.potato_cooked, Ingredients.potato_burned};

    /**
     * @param pos The position of the station.
     * @author Neves6
     */
    public OvenStation(Vector2 pos) {
        super(pos, 1, ALLOWED_INGREDIENTS, "particles/smokes.party", "audio/soundFX/frying.mp3");
    }

    /**
     * @param ingredient The ingredient to be placed.
     * @return True if the ingredient was placed, false otherwise.
     * @author Neves6
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