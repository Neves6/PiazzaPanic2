package com.team3gdx.game.station;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.team3gdx.game.food.Ingredient;
import com.team3gdx.game.food.Menu;
import com.team3gdx.game.food.Recipe;
import com.team3gdx.game.screen.GameScreen;
import com.team3gdx.game.util.Power;

import java.util.Random;

/**
 * PrepStation is a station that can transform ingredients into other ingredients or recipes.
 *
 * @author Team3Gdx
 * @author Neves6
 */
public class PrepStation extends Station {

    public float progress = 0;
    SpriteBatch batch;

    private final Random rand = new Random();

    public PrepStation(Vector2 pos) {
        super(pos, 5, false, null, null);
    }

    /**
     * Check if the current ingredients are part of a recipe or an ingredient can be
     * formed to another begin progress on creating it.
     *
     * @return A boolean representing whether the transformation happens.
     * @author Team3Gdx
     * @author Neves6
     */
    public boolean slotsToRecipe() {
        for (Recipe recipe : Menu.RECIPES.values()) {
            if (recipe.matches(slots)) {
                if (progress == 1) {
                    progress = 0;
                    slots.clear();
                    generatePower(150);
                    slots.add(recipe);
                }
                return true;
            }

        }

        if (ingredientMatch(slots.peek()) != null) {
            if (progress == 1) {
                progress = 0;
                generatePower(300);
                //	testPower(100,5);
                slots.add(ingredientMatch(slots.pop()));
            }
            return true;
        }

        GameScreen.cook.locked = false;
        slots.peek().slicing = false;

        return false;
    }

    /**
     * generates a power randomly
     *
     * @author Neves6
     */
    private void generatePower(int chance) {
        int randomNumber = rand.nextInt(chance) + 1; // generate a random number between 1-chance and if its below 100 then we use the tasks
        if (randomNumber <= 10) { //10% chance of clearing order
            System.out.println("Generated random number " + randomNumber);
            Power.addPower(3, batch); // add 3 to PowerStack
        } else if (randomNumber <= 40) { //30% of speed
            System.out.println("Generated random number " + randomNumber);
            Power.addPower(2, batch); // add 2 to PowerStack
        } else if (randomNumber <= 70) { //30% of instant cooking
            System.out.println("Generated random number " + randomNumber);
            Power.addPower(1, batch); // add 1 to PowerStack
        } else if (randomNumber <= 90) { //20% chance of extra points
            Power.addPower(4, batch);
        } else if (randomNumber <= 100) { //10% chance of regaining reputation
            Power.addPower(5, batch);
        }
    }

    /**
     * testing purposes
     *
     * @author Neves6
     */
    private void testPower(int chance, int power) {
        int randomNumber = rand.nextInt(100) + 1; // generate a random number between 1-chance and if its below 100 then we use the tasks
        if (randomNumber < 100) {
            Power.addPower(power, batch);
        }
    }

    /**
     * Lock currently interacting cook to station.
     *
     * @return A boolean indicating if the cook was locked.
     * @author Team3Gdx
     */
    public boolean lockCook() {
        if (!slots.isEmpty() && slotsToRecipe()) {
            if (lockedCook == null) {
                GameScreen.cook.locked = true;
                lockedCook = GameScreen.cook;
            } else {
                lockedCook.locked = true;
            }
            return true;
        }
        if (lockedCook != null) {
            lockedCook.locked = false;
            lockedCook = null;
            progress = 0;
        }

        return false;
    }

    private static final ShapeRenderer shapeRenderer = new ShapeRenderer();

    /**
     * Update and display the progress bar.
     *
     * @param batch
     * @param delta The amount to update the progress bar by.
     * @author Team3Gdx
     */
    public void updateProgress(SpriteBatch batch, float delta) {
        if (progress < 1)
            progress += delta;
        else {
            progress = 1;
            slotsToRecipe();
        }
        drawStatusBar(batch);
    }

    /**
     * @param batch The SpriteBatch to draw the progress bar on.
     * @author Team3Gdx
     */
    private void drawStatusBar(SpriteBatch batch) {
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(pos.x * 64, pos.y * 64 + 64 + 64 / 10, 64, 64 / 8);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(pos.x * 64, pos.y * 64 + 64 + 64 / 10, progress * 64, 64 / 10);
        shapeRenderer.end();

    }

    /**
     * Check whether the ingredient can be formed into another.
     *
     * @param toMatch The ingredient to transform.
     * @return The ingredient that is formed.
     * @author Team3Gdx
     * @author Neves6
     */
    private Ingredient ingredientMatch(Ingredient toMatch) {
        for (Ingredient ingredient : Menu.INGREDIENT_PREP.keySet()) {
            if (ingredient.equals(toMatch)) {
                Ingredient matchedIngredient = new Ingredient(Menu.INGREDIENT_PREP.get(ingredient));
                return matchedIngredient;
            }
        }

        return null;
    }

}
