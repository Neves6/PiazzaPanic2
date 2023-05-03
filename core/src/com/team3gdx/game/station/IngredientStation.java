package com.team3gdx.game.station;

import com.badlogic.gdx.math.Vector2;
import com.team3gdx.game.food.Ingredient;

/**
 * A station that provides an ingredient.
 * @author Team3Gdx
 */
public class IngredientStation extends Station {

    /**
     * Creates a new ingredient station.
     *
     * @author Team3Gdx
     * @param pos The position of the station.
     * @param ingredient The ingredient to provide.
     */
    public IngredientStation(Vector2 pos, Ingredient ingredient) {
        super(pos, 1, true, null, null);
        slots.push(ingredient);
    }

}
