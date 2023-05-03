package com.team3gdx.game.station;

import com.badlogic.gdx.math.Vector2;
import com.team3gdx.game.food.Ingredient;
import com.team3gdx.game.food.Ingredients;
/**
 * A station that fries an ingredient.
 *
 * @author Team3Gdx
 */
public class FryingStation extends CookingStation {

    private final static Ingredient[] ALLOWED_INGREDIENTS = {Ingredients.formedPatty};

    /**
     * Creates a new FryingStation.
     *
     * @author Team3Gdx
     * @param pos The position of the station.
     */
    public FryingStation(Vector2 pos) {
        super(pos, 1, ALLOWED_INGREDIENTS, "particles/flames.party", "audio/soundFX/frying.mp3");
    }

}
