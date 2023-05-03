package com.team3gdx.game.food;

import com.badlogic.gdx.math.Vector2;

/**
 * All available ingredient.
 *
 * @author Team3Gdx
 * @author Neves6
 */
public class Ingredients {

    // Meats.
    public static Ingredient unformedPatty = new Ingredient(null, 32, 32, "unformed_patty", 0, .5f);
    public static Ingredient formedPatty = new Ingredient(null, 32, 32, "patty", 0, .5f);
    public static Ingredient cookedPatty = new Ingredient(null, 32, 32, "patty", 0, .5f);

    // Cooked meats.
    static {
        cookedPatty.status = Status.COOKED;
        cookedPatty.flipped = true;
    }

    public static Ingredient burnedPatty = new Ingredient(null, 32, 32, "patty", 0, .5f);

    static {
        burnedPatty.status = Status.BURNED;
        burnedPatty.flipped = true;
    }

    // Vegetables.
    public static Ingredient lettuce = new Ingredient(null, 32, 32, "lettuce", 1, 0);
    public static Ingredient tomato = new Ingredient(null, 32, 32, "tomato", 1, 0);
    public static Ingredient onion = new Ingredient(null, 32, 32, "onion", 1, 0);

    // Chopped vegetables.
    public static Ingredient lettuceChopped = new Ingredient(null, 32, 32, "lettuce", 1, 0);

    static {
        lettuceChopped.slices = 1;
    }

    public static Ingredient tomatoChopped = new Ingredient(null, 32, 32, "tomato", 1, 0);

    static {
        tomatoChopped.slices = 1;
    }

    public static Ingredient onionChopped = new Ingredient(null, 32, 32, "onion", 1, 0);

    static {
        onionChopped.slices = 1;
    }

    // Breads.
    public static Ingredient bun = new Ingredient(new Vector2(0, 0), 32, 32, "burger_bun", 0, .5f);

    // Toasted breads.
    public static Ingredient cooked_bun = new Ingredient(new Vector2(0, 0), 32, 32, "burger_bun", 0, .5f);

    static {
        cooked_bun.status = Status.COOKED;
        cooked_bun.flipped = true;
    }

    public static Ingredient cooked_pizza = new Ingredient(new Vector2(0, 0), 32, 32, "pizza", 0, .5f);

    static {
        cooked_pizza.status = Status.COOKED;
    }

    public static Ingredient raw_pizza = new Ingredient(new Vector2(0, 0), 32, 32, "pizza", 0, .5f);
    public static Ingredient burnt_pizza = new Ingredient(new Vector2(0, 0), 32, 32, "pizza_burned", 0, .5f);

    static {
        cooked_pizza.status = Status.BURNED;
    }

    public static Ingredient flour = new Ingredient(null, 32, 32, "flour", 0, 0);
    public static Ingredient dough = new Ingredient(null, 32, 32, "dough", 0, 0);

    static {
        dough.status = Status.MIXED;
    }

    //Dairy
    public static Ingredient cheese = new Ingredient(null, 32, 32, "cheese", 0, 0);
    public static Ingredient potato = new Ingredient(null, 32, 32, "potato", 0, .5f);
    public static Ingredient potato_cooked = new Ingredient(null, 32, 32, "potato", 0, .5f);

    static {
        potato_cooked.status = Status.COOKED;
    }

    public static Ingredient potato_burned = new Ingredient(null, 32, 32, "potato", 0, .5f);

    static {
        potato_burned.status = Status.BURNED;
    }

}

