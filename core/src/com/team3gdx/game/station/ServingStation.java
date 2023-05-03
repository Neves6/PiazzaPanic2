package com.team3gdx.game.station;

import com.badlogic.gdx.math.Vector2;
import com.team3gdx.game.entity.Customer;
import com.team3gdx.game.food.*;
import com.team3gdx.game.screen.GameScreen;

import java.util.Iterator;
import java.util.Random;

/**
 * A station that serves food to customers.
 *
 * @author Team3Gdx
 * @author Neves6
 */
public class ServingStation extends Station {
    public String name = "ServingStation";
    String[] possibleOrders = new String[]{"pizza" , "jacket_potato"};
    /**
     * Configure allowed ingredient to be those on the menu.
     */
    static Ingredient[] allowedIngredients = new Ingredient[Menu.RECIPES.size() + 1];

    static {
        allowedIngredients[0] = Ingredients.cooked_pizza;
        int i = 1;
        for (Recipe recipe : Menu.RECIPES.values()) {
            allowedIngredients[i] = new Ingredient(recipe);
            i++;
        }
    }

    /**
     * Create a new serving station.
     *
     * @param pos The position of the station.
     * @author Team3Gdx
     */
    public ServingStation(Vector2 pos) {
        super(pos, 1, false, allowedIngredients, "audio/soundFX/money-collect.mp3");
    }

    /**
     * Check if there is a customer waiting, add their order to the orderCards and check if the
     * serving station contains any active orders.
     *
     * @author Team3Gdx
     * @author Neves6
     */
    public void takeCustomerOrder() {
        Customer waitingCustomer = GameScreen.cc.isCustomerAtPos(new Vector2(pos.x - 1, pos.y));
        if (waitingCustomer != null && waitingCustomer.locked) {
            GameScreen.orderCards.add(new OrderCard(possibleOrders[new Random().nextInt(possibleOrders.length)]));
            GameScreen.cc.delCustomer(waitingCustomer);
        }
    }

    /**
     * Check if the station has an order that matches the current order card and serve it.
     *
     * @author Team3Gdx
     * @author Neves6
     */
    public void serveOrder() {
        if (!slots.empty()) {
            //Stupid more verbose for-loop to prevent concurrentModification errors.
            Iterator<OrderCard> iterator;
            for (iterator = GameScreen.orderCards.iterator(); iterator.hasNext(); ) {
                OrderCard order = iterator.next();
                //For each ordercard, the station checks whether there exists an item in its slots that matches it.
                //It does this by matching the ordercard name to its relative recipe in Menu.RECIPES.
                if (!slots.isEmpty() && slots.peek().equals(Menu.RECIPES.get(order.getName().substring(0, 1).toUpperCase() + order.getName().substring(1)))) {
                    switch (order.getName()) {
                        case "salad":
                            GameScreen.addScore(150);
                            break;
                        case "burger":
                            GameScreen.addScore(200);
                            break;
                        case "jacket_potato":
                            GameScreen.addScore(100);
                            break;
                    }
                } else if (!slots.isEmpty() && slots.peek().equals(Ingredients.cooked_pizza) && order.getName() == "pizza") {
                    GameScreen.addScore(300);
                }

            }
            iterator.remove();
            slots.pop();
            GameScreen.orderJustServed = true;
            GameScreen.customersServed += 1;

        }
        }
    }

