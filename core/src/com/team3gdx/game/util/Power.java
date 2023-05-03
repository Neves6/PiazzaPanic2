package com.team3gdx.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;
import com.team3gdx.game.entity.Cook;
import com.team3gdx.game.screen.GameScreen;

import java.util.Stack;

import static com.team3gdx.game.screen.GameScreen.orderCards;

/**
 * Power class is used to handle the power ups and their effects
 *
 * @author Neves6
 */
public class Power {
    public static int use;
    //in charge of handling different states of power ups
    private static String currentPower = "None";
    private static Integer speedmultiplier = 2;
    private static final Stack<PowerUnit> powerStack = new Stack<>();
    private static int max = 5;

    /**
     * @author Neves6
     */
    private Power() {
    }

    /**
     * @author Neves6
     */
    private static void init() {
    }

    /**
     * Changes the speed of the cooks
     *
     * @return boolean
     * @author Neves6
     */

    public static boolean Speed() {
        // we are now using speed
        PowerUnit temp = powerStack.peek();
        powerStack.pop(); //now its being used
        cookspeed(Float.valueOf(speedmultiplier));
        use = 1; //stops continous usage
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                //Reset stats and current power after timer ends
                cookspeed(1 / Float.valueOf(speedmultiplier)); //resets speed by dividing multiplier
                speedmultiplier = 2;
                //System.out.println("Speed should be set invisible now" + currentPower);
                temp.setVisible(false);
                temp.dispose();
                currentPower = "None";
                use = 1;

            }
        }, 15);
        return true;

    }

    /**
     * Method to change speed depending on multiplier supplied
     *
     * @param Multiplier
     * @return boolean
     * @author Neves6
     */
    private static boolean cookspeed(Float Multiplier) {
        for (Cook cook : GameScreen.cooks) {
            cook.setSpeed(Multiplier);
        }
        return true;
    }


    /**
     * The set a timer for using the instant generation power up. The main implementation done with the ingredient class
     * its just for setting flag and disposing of the ui and updating power stack.
     *
     * @return
     * @author Neves6
     */
    public static boolean recipe_complete() {
        PowerUnit temp = powerStack.peek();
        powerStack.pop(); //now its being used
        use = 1; //stops continous usage
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                // Reset stats and current power after timer ends
                //System.out.println("Instant should be set invisible now" + currentPower);
                temp.setVisible(false);
                temp.dispose();
                currentPower = "None";
                use = 1;
            }
        }, 15);
        return true;
    } //instant generation , just handles the timer the rest is handled by ingredient poweruse check method

    /**
     * this method removes the orders on queue, it sets up the ui and gets rid of jacket potato
     *
     * @return
     * @author Neves6
     */
    public static boolean wipe() {
        PowerUnit temp = powerStack.peek();
        orderCards.clear();
        powerStack.pop();
        temp.setVisible(false);
        temp.dispose();
        return true;
    }        //wipes out current orders

    /**
     * gets current power
     *
     * @return power
     * @author Neves6
     */
    public static String getCurrentPower() {
        return currentPower;
    }

    /**
     * getter method returns the powerunit in case its needed
     *
     * @return powerstack
     * @author Neves6
     */
    public static PowerUnit getCurrentUnit() {
        return powerStack.pop();
    }

    /**
     * set flags and checks for powers and uses the top of the powerstack to set current power and call the relevant methods
     *
     * @return boolean true if power is used , false otherwise
     * @author Neves6
     */
    public static boolean usePower() {
        if (!isPowerEmpty()) {
            if (powerStack.peek().getPower() == 1) {
                currentPower = "Speed";
                Power.Speed();
                use = 0;
                return true;
            } else if (powerStack.peek().getPower() == 2) {
                currentPower = "Instant"; // removes   order from place if there is an order there
                Power.recipe_complete();
                use = 0;
                return true;
            } else if (powerStack.peek().getPower() == 3) {
                currentPower = "Wipe"; // wipes out an order
                Power.wipe();
                use = 0;
                return true;
            } else if (powerStack.peek().getPower() == 4) {
                currentPower = "Points";
                Power.addPoints();
                use = 0;
            } else if (powerStack.peek().getPower() == 5) {
                currentPower = "Reputation";
                Power.addRep();
                use = 0;
            }
        }
        return false;
    }

    /**
     * Adds points to the score
     *
     * @author Neves6
     */
    private static void addPoints() {
        PowerUnit temp = powerStack.peek();
        GameScreen.addScore(250);
        powerStack.pop();
        temp.setVisible(false);
        temp.dispose();
    }

    /**
     * Adds a reputation point
     *
     * @author Neves6
     */
    private static void addRep() {
        PowerUnit temp = powerStack.peek();
        GameScreen.addRep();
        powerStack.pop();
        temp.setVisible(false);
        temp.dispose();
    }

    /**
     * used to add power into stack, and sets up the ui generation
     *
     * @param pow
     * @param batch
     * @author Neves6
     */
    public static void addPower(Integer pow, SpriteBatch batch) {
        Float y = 820F;
        if (!isPowerFull() && isPowerEmpty()) {
            //add texture to it
            Texture powerTexture = new Texture(Gdx.files.internal("uielements/power" + pow + ".png"));
            //new power unit with alligning
            Float x = 200F;
            PowerUnit powerUnit = new PowerUnit(pow, powerTexture, x, y);
            powerUnit.setVisible(true);
            powerStack.push(powerUnit); // push onto stack

        } else if (!isPowerFull() && !isPowerEmpty()) {
            Float x = powerStack.peek().getX() + 100F;
            Texture powerTexture = new Texture(Gdx.files.internal("uielements/power" + pow + ".png"));
            PowerUnit powerUnit = new PowerUnit(pow, powerTexture, x, y);
            powerUnit.setVisible(true);
            powerStack.push(powerUnit); // push onto stack
            //render powerup
        }
    }

    /**
     * getter method for whole power stack
     *
     * @return powerStack
     * @author Neves6
     */
    public static Stack<PowerUnit> getPowerStack() {
        return powerStack;
    }

    /**
     * checks if there is powers in stack
     *
     * @return
     * @author Neves6
     */

    public static boolean isPowerEmpty() {
        return powerStack.isEmpty();
    }


    /**
     * used to check if maximum amount of power is used
     *
     * @return boolean indicating if stack is full or not
     * @author Neves6
     */

    public static boolean isPowerFull() {
        return powerStack.size() == max;
    }

    /**
     * clears powers
     *
     * @author Neves6
     */
    public static void resetPower() {
        powerStack.clear();
        currentPower = "None";
        use = 0;
    }

    /**
     * saves power to a slot
     *
     * @param slotNo
     * @author Neves6
     */
    public static void savePower(int slotNo) {
        Preferences slot = Gdx.app.getPreferences("power" + slotNo);
        slot.putInteger("use", use);
        slot.putString("currentPower", currentPower);
        slot.putInteger("powerStackSize", powerStack.size());
        int i = 0;
        for (PowerUnit powerUnit : powerStack) {
            slot.putInteger("powerStack" + i, powerUnit.getPower());
            i++;
        }
        slot.putInteger("max", max);
        slot.putInteger("speedmultiplier", speedmultiplier);
        slot.flush();
    }

    /**
     * loads power from a slot
     *
     * @param slotNo
     * @author Neves6
     */
    public static void loadPower(int slotNo) {
        Preferences slot = Gdx.app.getPreferences("power" + slotNo);
        use = slot.getInteger("use");
        currentPower = slot.getString("currentPower");
        int powerStackSize = slot.getInteger("powerStackSize");
        for (int i = 0; i < powerStackSize; i++) {
            int power = slot.getInteger("powerStack" + i);
            addPower(power, null);
        }
        max = slot.getInteger("max");
        speedmultiplier = slot.getInteger("speedmultiplier");
    }
}

