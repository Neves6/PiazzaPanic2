package com.team3gdx.game.food;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;

/**
 * Represents a recipe (combination of ingredients and states).
 *
 * @author Team3Gdx
 * @author Neves6
 */
public class Recipe extends Ingredient {

    /**
     * Name of the recipe to get texture.
     */
    public final String name;

    /**
     * Map of ingredient and instruction on what to do with it.
     */
    public Map<Ingredient, String> ingredientInstructions;

    /**
     * Whether the recipe has to be made in order (specific order of ingredients).
     */
    private final boolean shouldBeOrdered;
    /**
     * Additional instructions.
     */
    private final String initialSteps;
    private final String finalSteps;

    private final Ingredient initialIngredient;

    /**
     * How much the menu item will cost.
     */
    public float cost;

    /**
     * Sets the appropriate recipe properties.
     *
     * @author Team3Gdx
     *
     * @param initialSteps           Preface to the recipe.
     * @param initialIngredient      Ingredient to display with the initial step.
     * @param ingredientInstructions Map of ingredient and instruction on what to do
     *                               with it.
     * @param finalSteps             Postface to the recipe.
     * @param name                   Name of the recipe to find the correct texture.
     * @param shouldBeOrdered        Whether the recipe has to be made in order
     *                               (specific order of ingredients).
     * @param pos                    The (x, y) coordinates of the ingredient.
     * @param width                  The recipe's texture width.
     * @param height                 The recipe's texture height.
     * @param cost                   How much the menu item will cost.
     */
    public Recipe(String initialSteps, Ingredient initialIngredient, Map<Ingredient, String> ingredientInstructions,
                  String finalSteps, String name, boolean shouldBeOrdered, Vector2 pos, float width, float height,
                  float cost) {
        super(pos, width, height, name, 0, 0);
        this.initialSteps = initialSteps;
        this.initialIngredient = initialIngredient;
        this.ingredientInstructions = ingredientInstructions;
        this.finalSteps = finalSteps;
        this.name = name;
        this.shouldBeOrdered = shouldBeOrdered;
        this.cost = cost;
    }

    ShapeRenderer shapeRenderer = new ShapeRenderer();

    /**
     * Check if there is a recipe with the given ingredients so that it can be assembled.
     *
     * @author Team3Gdx
     * @author Neves6
     * @param givenIngredients A Stack of ingredients to compare to recipes.
     * @return A boolean to indicate if a recipe exists with the given ingredients.
     */
    public boolean matches(Stack<Ingredient> givenIngredients) {
        ArrayList<Ingredient> toCheck = new ArrayList<Ingredient>(ingredientInstructions.keySet());
        if (givenIngredients.size() != toCheck.size())
            return false;
        if (shouldBeOrdered) {
            for (int i = 0; i < toCheck.size(); i++)
                if (!toCheck.get(i).equals(givenIngredients.get(i)))
                    return false;
            return true;
        }

        for (Ingredient ingredient : givenIngredients) {
            if (contains(ingredient, toCheck)) {
                toCheck.remove(ingredient);
            }
        }
        return toCheck.isEmpty();
    }

    /**
     * Check whether the list contains a match with the given ingredient (since we
     * use {@link Ingredient#equals(Object)}).
     *
     * @author Team3Gdx
     * @param checkIngredient The ingredient to check.
     * @param ingredients     The list of ingredients to search through.
     * @return A boolean to indicate if the ingredient matches one in the list.
     */
    private boolean contains(Ingredient checkIngredient, ArrayList<Ingredient> ingredients) {
        for (Ingredient ingredient : ingredientInstructions.keySet()) {
            if (ingredient.equals(checkIngredient)) {
                return true;
            }
        }
        return false;
    }
}
