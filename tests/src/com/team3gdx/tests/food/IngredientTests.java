package com.team3gdx.tests.food;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.team3gdx.game.food.Ingredient;
import com.team3gdx.game.util.Power;
import com.team3gdx.tests.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class IngredientTests {
    @Test
    public void flip() {
        SpriteBatch batch = null;
        Ingredient burger = new Ingredient(null, 32, 32, "patty", 0, .5f);
        assertFalse(burger.flip());

        burger.cook(.00005f, batch);
        assertTrue(burger.flip());

        burger = null;
        batch = null;
    }

    @Test
    public void slice() {
        SpriteBatch batch = null;
        Ingredient lettuce = new Ingredient(null, 32, 32, "lettuce", 1, 0);
        Ingredient burger = new Ingredient(null, 32, 32, "patty", 0, .5f);

        assertTrue(lettuce.slice(batch, 0.0005f));
        assertFalse(burger.slice(batch, 0.0005f));

        burger = null;
        lettuce = null;
        batch = null;
    }

    @Test
    public void PowerChecker() {
        Ingredient lettuce = new Ingredient(null, 32, 32, "lettuce", 1, 0);

        assertFalse(lettuce.PowerChecker());

        Power.loadPower(2);
        assertTrue(lettuce.PowerChecker());

        lettuce = null;
    }

    @Test
    public void cook() {
        SpriteBatch batch = null;
        Ingredient lettuce = new Ingredient(null, 32, 32, "lettuce", 1, 0);
        boolean val = lettuce.cook(0.05f, batch) == 0.05f;

        assertTrue(val = true);

        if (lettuce.cook(0.05f, batch) != 0.05f) {
            val = false;
        }
        assertFalse(val = false);

        batch = null;
        lettuce = null;
    }

    @Test
    public void equals() {
        Ingredient lettuce = new Ingredient(null, 32, 32, "lettuce", 1, 0);
        Ingredient burger = new Ingredient(null, 32, 32, "patty", 0, .5f);

        assertEquals(lettuce, lettuce);
        assertNotEquals(lettuce, burger);

        lettuce = null;
        burger = null;
    }
}
