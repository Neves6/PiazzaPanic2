package com.team3gdx.tests.station;

import com.team3gdx.game.food.Ingredients;
import com.team3gdx.game.station.OvenStation;
import org.junit.Test;
import com.team3gdx.tests.GdxTestRunner;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class OvenStationTests {
    @Test
    public void place(){
        Ingredients i = new Ingredients();
        OvenStation baking = new OvenStation(null);

        assertTrue(baking.place(Ingredients.raw_pizza));
        assertFalse(baking.place(Ingredients.cheese));

        i = null;
        baking = null;
    }
}
