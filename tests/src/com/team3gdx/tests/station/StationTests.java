package com.team3gdx.tests.station;

import com.team3gdx.game.food.Ingredient;
import com.team3gdx.game.food.Ingredients;
import com.team3gdx.game.station.Station;
import com.team3gdx.tests.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class StationTests {
    @Test
    public void place() {
        Ingredients onion = new Ingredients();
        Ingredient[] ALLOWED_INGREDIENTS = {Ingredients.onion};
        Station station = new Station(null, 1, true, ALLOWED_INGREDIENTS, "audio/soundFX/chopping.mp3");

        assertTrue(station.place(Ingredients.onion));
        assertFalse(station.place(Ingredients.cheese));

        station = null;
        onion = null;
        ALLOWED_INGREDIENTS = null;
    }

    @Test
    public void isAllowed() {
        Ingredients onion = new Ingredients();
        Ingredient[] ALLOWED_INGREDIENTS = {Ingredients.onion};
        Station station = new Station(null, 1, true, ALLOWED_INGREDIENTS, "audio/soundFX/chopping.mp3");

        assertTrue(station.isAllowed(Ingredients.onion));
        assertFalse(station.isAllowed(Ingredients.onion));

        station = null;
        onion = null;
        ALLOWED_INGREDIENTS = null;
    }

    @Test
    public void take() {
        Ingredients onion = new Ingredients();
        Ingredient[] ALLOWED_INGREDIENTS = {Ingredients.onion};
        Station station = new Station(null, 1, true, ALLOWED_INGREDIENTS, "audio/soundFX/chopping.mp3");

        assertNull(station.take());

        station = null;
        onion = null;
        ALLOWED_INGREDIENTS = null;
    }
}
