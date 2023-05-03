package com.team3gdx.tests.food;

import com.team3gdx.game.food.Ingredient;
import com.team3gdx.game.food.Ingredients;
import com.team3gdx.game.food.Recipe;
import com.team3gdx.tests.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class RecipeTests {
    @Test
    public void matches() {
        Map<Ingredient, String> SALAD_STEPS = new HashMap<Ingredient, String>();
        SALAD_STEPS.put(Ingredients.lettuceChopped, "Cut");

        Recipe salad = new Recipe("", null, SALAD_STEPS, "serve together", "salad", false, null, 32, 32, 0);
        Ingredient lettuceChopped = new Ingredient(null, 32, 32, "lettuce", 1, 0);
        Ingredient bun = new Ingredient(null, 32, 32, "bun", 1, 0);


        Stack<Ingredient> stack = new Stack<>();
        stack.add(lettuceChopped);
        assertTrue(salad.matches(stack));

        stack.add(bun);
        assertFalse(salad.matches(stack));

        salad = null;
        SALAD_STEPS = null;
        lettuceChopped = null;
        bun = null;
        stack = null;
    }
}
