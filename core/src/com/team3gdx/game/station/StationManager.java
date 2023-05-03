package com.team3gdx.game.station;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.team3gdx.game.food.Ingredient;
import com.team3gdx.game.food.Ingredients;
import com.team3gdx.game.screen.GameScreen;
import com.team3gdx.game.util.Power;

import java.util.HashMap;
import java.util.Map;

/**
 * Deals with all the stations and cook interactions. To create a new station,
 * extend from {@link Station}, check tile in
 * {@link this#checkInteractedTile(String, Vector2)}, and update station's
 * ingredients in {@link this#handleStations(SpriteBatch)} if necessary.
 * @author Team3Gdx
 * @author Neves6
 */
public class StationManager {
    private static final Map<String, Texture> textures = new HashMap<>();

    static {
        textures.put("Baking", new Texture(Gdx.files.internal("map/art_map/art_images/emptygrill.png")));
        textures.put("Oven", new Texture(Gdx.files.internal("map/art_map/art_images/ovenbotcln.png")));
        textures.put("Frying", new Texture(Gdx.files.internal("map/art_map/art_images/ovtopcln.png")));
        textures.put("Chopping", new Texture(Gdx.files.internal("map/art_map/art_images/chopping.png")));
    }

    /**
     * A Map representing every station and its (x, y) coordinates.
     */
    public static Map<Vector2, Station> stations;
    public Power power;
    SpriteBatch batch;

    private final Boolean temp = false;

    public StationManager() {
        stations = new HashMap<Vector2, Station>();
    }

    /**
     * Checks every station for ingredients and updates them accordingly.
     *
     * @author Team3Gdx
     * @author Neves6
     * @param batch - SpriteBatch to render ingredient textures.
     */
    public void handleStations(SpriteBatch batch) {
        this.batch = batch;
        for (Station station : stations.values()) {
            if (!station.slots.empty() && !station.infinite) {
                for (int i = 0; i < station.slots.size(); i++) {
                    // Handle each ingredient in slot.
                    Ingredient currentIngredient = station.slots.get(i);
                    if (station instanceof PrepStation) {
                        currentIngredient.pos = new Vector2(station.pos.x * 64 + 16, i * 8 + station.pos.y * 64);
                        if (station.lockedCook != null)
                            ((PrepStation) station).updateProgress(batch, .01f);
                    } else {
                        currentIngredient.pos = new Vector2(station.pos.x * 64 + ((i * 32) % 64),
                                Math.floorDiv((i * 32), 64) * 32 + station.pos.y * 64);
                    }

                    if (station instanceof CuttingStation && currentIngredient.slicing) {
                        ((CuttingStation) station).interact(batch, .1f);
                        currentIngredient.PowerChecker();
                        station.interactSound();
                    }

                    if (currentIngredient.cooking && station instanceof CookingStation) {
                        ((CookingStation) station).drawParticles(batch, i);
                        currentIngredient.PowerChecker();

                        double temp = currentIngredient.cook(.0005f, batch);
                        station.interactSound();
                        /**
                         * instantly generates ingredient and adds it to held items stack.
                         */
                        if (temp == 1 && Power.getCurrentPower() == "Instant") {
                            if (!station.slots.empty() && !GameScreen.cook.full()) {
                                if (station.slots.peek().flipped) {
                                    GameScreen.cook.pickUpItem(station.take());
                                }
                            }
                        }
                    } else {
                        currentIngredient.draw(batch);
                    }
                }
            }
        }
    }

    /**
     * Check the currently looked at tile for a station.
     *
     * @author Team3Gdx
     * @author Neves6
     * @param type The station type.
     * @param pos  The position of the tile.
     */
    public void checkInteractedTile(String type, Vector2 pos) {
        switch (type) {
            case "Buns":
                takeIngredientStation(pos, Ingredients.bun);
                break;
            case "Patties":
                takeIngredientStation(pos, Ingredients.unformedPatty);
                break;
            case "Lettuces":
                takeIngredientStation(pos, Ingredients.lettuce);
                break;
            case "Tomatoes":
                takeIngredientStation(pos, Ingredients.tomato);
                break;
            case "Onions":
                takeIngredientStation(pos, Ingredients.onion);
                break;
            case "Flour":
                takeIngredientStation(pos, Ingredients.flour);
                break;
            case "Cheese":
                takeIngredientStation(pos, Ingredients.cheese);
                break;
            case "Potato":
                takeIngredientStation(pos, Ingredients.potato);
                break;

            case "Frying":
                checkStationExists(pos, new FryingStation(pos));
                ((CookingStation) stations.get(pos)).checkCookingStation(batch);
                ((CookingStation) stations.get(pos)).lockCook();
                break;
            case "Prep":
                if (!stations.containsKey(pos)) {
                    stations.put(pos, new PrepStation(pos));
                }
                placeIngredientStation(pos);
                PrepStation station = ((PrepStation) stations.get(pos));
                station.lockCook();

                break;
            case "Chopping":
                if (!stations.containsKey(pos)) {
                    stations.put(pos, new CuttingStation(pos));
                }
                placeIngredientStation(pos);
                CuttingStation cutStation = ((CuttingStation) stations.get(pos));
                cutStation.lockCook();
                break;
            case "Baking":
                checkStationExists(pos, new BakingStation(pos));
                ((CookingStation) stations.get(pos)).checkCookingStation(batch);
                ((CookingStation) stations.get(pos)).lockCook();
                break;
            case "Service":
                checkStationExists(pos, new ServingStation(pos));
                ((ServingStation) stations.get(pos)).takeCustomerOrder();
                ((ServingStation) stations.get(pos)).serveOrder();
                placeIngredientStation(pos);
                break;
            case "Oven":
                checkStationExists(pos, new OvenStation(pos));
                ((CookingStation) stations.get(pos)).checkCookingStation(batch);
                break;
            case "locked_oven":

                batch.begin();
                (new BitmapFont()).draw(batch, "Unlock station [e]", pos.x * 64, pos.y * 64);
                batch.end();
                if (GameScreen.control.drop) {
                    this.unlockStation(pos, "Oven");
                }
                break;
            case "locked_frying":
                batch.begin();
                (new BitmapFont()).draw(batch, "Unlock station [e]", pos.x * 64, pos.y * 64);
                batch.end();
                if (GameScreen.control.drop) {
                    this.unlockStation(pos, "Frying");
                }
                break;


            case "shop":
                if (GameScreen.cooks.length < 3) {
                    batch.begin();
                    (new BitmapFont()).draw(batch, "Hire chef 500 points [e]", pos.x * 64, pos.y * 64);
                    batch.end();
                    if (GameScreen.control.drop) {
                        GameScreen.addnewchef();
                    }
                } else {
                    batch.begin();
                    (new BitmapFont()).draw(batch, "Chef already hired", pos.x * 64, pos.y * 64);
                    batch.end();
                }
                break;
            case "locked_pan":
                batch.begin();
                (new BitmapFont()).draw(batch, "Unlock station [e]", pos.x * 64, pos.y * 64);
                batch.end();
                if (GameScreen.control.drop) {
                    this.unlockStation(pos, "Baking");
                }
                break;
            case "locked_chopping":
                batch.begin();
                (new BitmapFont()).draw(batch, "Unlock station [e]", pos.x * 64, pos.y * 64);
                batch.end();
                if (GameScreen.control.drop) {
                    this.unlockStation(pos, "Chopping");
                }
                break;
            case "Bin":
                if (!GameScreen.cook.heldItems.empty()) {
                    batch.begin();
                    (new BitmapFont()).draw(batch, "Drop [e]", pos.x * 64, pos.y * 64);
                    batch.end();
                    if (GameScreen.control.drop) {
                        GameScreen.cook.dropItem();
                    }
                }
                break;
            case "Table_Surface":
                placeIngredientStation(pos);
                break;

        }

    }

    /**
     * Method for changing locked stations to unlocked stations
     *
     * @author Neves6
     * @param pos Vector2 type position of locked station
     */
    public void unlockStation(Vector2 pos, String name) {
        if (GameScreen.score + GameScreen.accumulatedScore >= 10) {
            GameScreen.subScore(10);
            TiledMapTileLayer.Cell cell = GameScreen.returnCell(pos);
            TextureRegion newTexture = new TextureRegion(textures.get(name));
            TiledMapTile newTile = new StaticTiledMapTile(newTexture);
            newTile.getProperties().put("Station", name);
            cell.setTile(newTile);
        }

    }

    /**
     * Check if the given station exists at the given position.
     *
     * @author Team3Gdx
     * @param pos     Position to look for.
     * @param station The station to check for.
     * @return A boolean indicating if the station exists at that position.
     */
    private boolean checkStationExists(Vector2 pos, Station station) {
        if (!stations.containsKey(pos)) {
            stations.put(pos, station);
            return false;
        }

        return true;
    }

    /**
     * Place ingredient on top of cook's stack on station at given position.
     *
     * @author Team3Gdx
     * @param pos The position to lookup the station.
     */
    private void placeIngredientStation(Vector2 pos) {
        checkStationExists(pos, new Station(pos, 4, false, null, null));
        stations.get(pos).drawTakeText(batch);
        stations.get(pos).drawDropText(batch);
        if (GameScreen.control.interact) {
            if (!stations.get(pos).slots.empty() && !GameScreen.cook.full()) {
                GameScreen.cook.pickUpItem(stations.get(pos).take());
                return;
            }
        }

        if (GameScreen.cook.heldItems.empty())
            return;
        if (GameScreen.control.drop)
            if (stations.get(pos).place(GameScreen.cook.heldItems.peek()))
                GameScreen.cook.dropItem();
    }

    /**
     * Take an item from the ingredient station
     *
     * @author Team3Gdx
     * @param pos        The position of the station.
     * @param ingredient The ingredient that the station holds.
     */
    private void takeIngredientStation(Vector2 pos, Ingredient ingredient) {
        checkStationExists(pos, new IngredientStation(pos, ingredient));
        stations.get(pos).drawTakeText(batch);
        if (GameScreen.control.interact) {
            GameScreen.cook.pickUpItem(stations.get(pos).take());
        }

    }

}
