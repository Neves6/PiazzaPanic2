package com.team3gdx.tests.station;

import com.team3gdx.game.food.Ingredients;
import com.team3gdx.game.station.BakingStation;
import com.team3gdx.tests.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class BakingStationTests {
    @Test
    public void place() {
        Ingredients bun = new Ingredients();
        BakingStation baking = new BakingStation(null);

        assertTrue(baking.place(Ingredients.bun));
        assertFalse(baking.place(Ingredients.cheese));

        bun = null;
        baking = null;
    }
}
