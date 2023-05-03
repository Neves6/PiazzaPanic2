package com.team3gdx.tests.station;

import com.team3gdx.game.food.Ingredient;
import com.team3gdx.game.food.Ingredients;
import com.team3gdx.game.station.CookingStation;
import org.junit.Test;
import com.team3gdx.tests.GdxTestRunner;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class CookingStationTests {
    @Test
    public void lockCook(){
        Ingredients i = new Ingredients();
        Ingredient[] ALLOWED_INGREDIENTS = {Ingredients.bun, Ingredients.cooked_bun};
        CookingStation cooking = new CookingStation(null,1,ALLOWED_INGREDIENTS, "particles/smokes.party", "audio/soundFX/frying.mp3");

        assertTrue(cooking.lockCook());

        cooking = null;
        i = null;
    }
}
