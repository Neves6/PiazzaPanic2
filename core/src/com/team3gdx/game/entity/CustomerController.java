package com.team3gdx.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;
import com.team3gdx.game.screen.GameScreen;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class CustomerController {
    public int lockout;
    public ArrayList<ArrayList<Integer>> customerCells;
    public Customer[] customers;
    public Customer[] leavingcustomers;
    public TiledMap gameMap;
    public int top;
    public int bottom;
    public int xCoordinate;

    public CustomerController(TiledMap map) {
        this.gameMap = map;
        customers = new Customer[GameScreen.CUSTOMER_SPAWNCAP];
        leavingcustomers = new Customer[GameScreen.CUSTOMER_SPAWNCAP];
        computeCustomerZone(gameMap);
        lockout = 0;
    }

    /**
     * Check whether the customer zone is correct in the tile map, and construct
     * data structures for it
     *
     * @param gameMap - The game tilemap
     */
    private void computeCustomerZone(TiledMap gameMap) {
        TiledMapTileLayer botlayer = (TiledMapTileLayer) gameMap.getLayers().get(0);
        customerCells = new ArrayList<>();
        int mapheight = botlayer.getHeight();
        int mapwidth = botlayer.getWidth();
        for (int y = 0; y < mapheight; y++) {
            for (int x = 0; x < mapwidth; x++) {
                Cell cel1 = botlayer.getCell(x, y);
                if (cel1 != null) {
                    TiledMapTile til1 = cel1.getTile();
                    MapProperties mp1 = til1.getProperties();
                    Object value = mp1.get("customer_zone");
                    boolean isCustomerZone = value != null && (boolean) value;
                    if (isCustomerZone) {
                        ArrayList<Integer> e = new ArrayList<>();
                        e.add(x);
                        e.add(y);
                        customerCells.add(e);
                    }
                }
            }
        }
        // ^Scan tilemap for Customer zone
        // tile
        if (customerCells.size() == 0) {
            throw new IllegalArgumentException("No customer zone was included in the tile map");
        }
        // ^If no Customer zone tiles exist throw
        // exception
        Integer[] xvalues = new Integer[customerCells.size()];
        Integer[] yvalues = new Integer[customerCells.size()];
        int ctr = 0;
        for (ArrayList<Integer> xypair : customerCells) {
            xvalues[ctr] = xypair.get(0);
            yvalues[ctr] = xypair.get(1);
            ctr++;
        }
        // ^Split x y pairs into 2 separate
        // arrays
        Set<Integer> uniquexvalues = new HashSet<>(Arrays.asList(xvalues));
        if (uniquexvalues.size() != 2) {
            throw new IllegalArgumentException("Customer zone must be a 2 wide rectangle leading to bottom of map");
        }
        // ^Throw exception if more than 2 unique x values exist - the rectangle is not
        // 2 wide
        ArrayList<Integer> yvalueslist = new ArrayList<>(Arrays.asList(yvalues));
        if (!yvalueslist.contains(0)) {
            throw new IllegalArgumentException("Customer zone must extend to the bottom of the map");
        }
        // ^Throw exception if the customer zone tile list does not contain tiles at the
        // bottom index
        int ymax = Collections.max(yvalueslist);
        Integer[] uniquexvaluesarray = uniquexvalues.toArray(new Integer[]{});
        for (Integer unx : uniquexvaluesarray) {
            for (int i = ymax; i > 0; i--) {
                ArrayList<Integer> templist = new ArrayList<>();
                templist.add(unx);
                templist.add(i);
                if (!customerCells.contains(templist)) {
                    throw new IllegalArgumentException("Customer zone must be a filled rectangle");
                }
            }
        }
        // ^Throw exception if the customer zone is not a filled rectangle. It does this
        // by looking from the maximum
        // y value downwards to check if a tile exists all the way down to zero, on both
        // columns.
        this.top = ymax;
        this.bottom = 0;
        this.xCoordinate = xvalues[0]; // We can do this because the search scans left to right, 0th value will be left
    }

    /**
     * Update the customer controller
     */
    public void spawnCustomer() {
        for (int i = 0; i < this.customers.length; i++) {
            if (customers[i] == null) {
                customers[i] = new Customer(this.xCoordinate, this.bottom, this.top - i, ThreadLocalRandom.current().nextInt(1, 5 + 1));
                customers[i].arrived();
                break;
            }
        }
    }

    /**
     * Deletes a customer from the customer list
     *
     * @param num - index of customer to delete
     */
    public void delCustomer(int num) {
        if (this.customers[num].locked) {
            this.leavingcustomers[num] = this.customers[num];
            this.leavingcustomers[num].setTargetsquare(-1);
            this.customers[num] = null;
        }
    }

    /**
     * Deletes a customer from the customer list
     *
     * @param customer - customer to delete
     */
    public void delCustomer(Customer customer) {
        for (int i = 0; i < this.customers.length; i++) {
            if (customers[i] == customer) {
                delCustomer(i);
                return;
            }
        }
    }

    /**
     * Draw top of customers
     *
     * @param b1 - spritebatch to render with
     */
    public void drawCustTop(Batch b1) {
        for (Customer c : this.customers) {
            if (c != null) {
                c.renderCustomersBot(b1);
            }
        }
        for (Customer c : this.leavingcustomers) {
            if (c != null) {
                c.renderCustomersBot(b1);
            }
        }
        for (Customer c : this.customers) {
            if (c != null) {
                c.renderCustomersTop(b1);
            }
        }
        for (Customer c : this.leavingcustomers) {
            if (c != null) {
                c.renderCustomersTop(b1);
            }
        }
    }

    /**
     * Update customers
     */
    public void updateCustomers() {
        for (Customer c : this.customers) {
            if (c != null) {
                c.stepTarget();
            }
        }
        int ctr = 0;
        for (Customer c : this.leavingcustomers) {
            if (c != null) {
                c.stepTarget();
                if (c.readyfordeletion) {
                    this.leavingcustomers[ctr] = null;
                }
            }
            ctr++;
        }
    }

    /**
     * Check if any of the customers is at one position
     *
     * @param pos - vector position
     * @return null if no customers are at that position, return the customer that
     * is at that position
     */
    public Customer isCustomerAtPos(Vector2 pos) {
        for (Customer customer : customers)
            if (customer != null && Math.ceil(customer.posx / 64f) == pos.x && Math.ceil(customer.posy / 64f) == pos.y
                    && customer.locked) {
                return customer;
            }
        return null;
    }

    /**
     * Reinitialise the customer array
     */
    public void reInitCustArr() {
        customers = new Customer[GameScreen.CUSTOMER_SPAWNCAP];
        leavingcustomers = new Customer[GameScreen.CUSTOMER_SPAWNCAP];
    }

    public void saveCC(int slotNo) {
        Preferences slot = Gdx.app.getPreferences("cc" + slotNo);
        slot.putInteger("top", top);
        slot.putInteger("bottom", bottom);
        slot.putInteger("xCoordinate", xCoordinate);
        slot.putInteger("customerCount", (int) Arrays.stream(customers).filter(e -> e != null).count());
        slot.putInteger("leavingcustomerCount", (int) Arrays.stream(leavingcustomers).filter(e -> e != null).count());
        for (int i = 0; i < (int) Arrays.stream(customers).filter(e -> e != null).count(); i++) {
            slot.putInteger("customers" + i, customers[i].targetsquare);
            slot.putInteger("posx" + i, customers[i].posx);
            slot.putInteger("posy" + i, customers[i].posy);
            slot.putInteger("startposx" + i, customers[i].startposx);
            slot.putInteger("targetpixel" + i, customers[i].targetpixel);
            slot.putBoolean("locked" + i, customers[i].locked);
            slot.putBoolean("readyfordeletion" + i, customers[i].readyfordeletion);
            slot.putLong("arrivalTime" + i, customers[i].arrivalTime);
            slot.putLong("maxWaitTime" + i, Customer.maxWaitTime);
        }
        for (int i = 0; i < (int) Arrays.stream(leavingcustomers).filter(e -> e != null).count(); i++) {
            slot.putInteger("leavingcustomers" + i, leavingcustomers[i].targetsquare);
            slot.putInteger("leavingposx" + i, leavingcustomers[i].posx);
            slot.putInteger("leavingposy" + i, leavingcustomers[i].posy);
            slot.putInteger("leavingstartposx" + i, leavingcustomers[i].startposx);
            slot.putInteger("leavingtargetpixel" + i, leavingcustomers[i].targetpixel);
            slot.putBoolean("leavinglocked" + i, leavingcustomers[i].locked);
            slot.putBoolean("leavingreadyfordeletion" + i, leavingcustomers[i].readyfordeletion);
            slot.putLong("leavingarrivalTime" + i, leavingcustomers[i].arrivalTime);
            slot.putLong("leavingmaxWaitTime" + i, Customer.maxWaitTime);
        }
        slot.flush();
    }

    public void loadCC(int slotNo) {
        Preferences slot = Gdx.app.getPreferences("cc" + slotNo);
        customers = new Customer[GameScreen.CUSTOMER_SPAWNCAP];
        leavingcustomers = new Customer[GameScreen.CUSTOMER_SPAWNCAP];
        top = slot.getInteger("top");
        bottom = slot.getInteger("bottom");
        xCoordinate = slot.getInteger("xCoordinate");
        int customerCount = slot.getInteger("customerCount");
        int leavingcustomerCount = slot.getInteger("leavingcustomerCount");
        for (int i = 0; i < customerCount; i++) {
            customers[i] = new Customer(this.xCoordinate, this.bottom, this.top - i, ThreadLocalRandom.current().nextInt(1, 5 + 1));
            customers[i].targetsquare = slot.getInteger("customers" + i);
            customers[i].posx = slot.getInteger("posx" + i);
            customers[i].posy = slot.getInteger("posy" + i);
            customers[i].startposx = slot.getInteger("startposx" + i);
            customers[i].targetpixel = slot.getInteger("targetpixel" + i);
            customers[i].locked = slot.getBoolean("locked" + i);
            customers[i].readyfordeletion = slot.getBoolean("readyfordeletion" + i);
            customers[i].arrivalTime = slot.getLong("arrivalTime" + i);
            Customer.maxWaitTime = slot.getLong("maxWaitTime" + i);
        }
        for (int i = 0; i < leavingcustomerCount; i++) {
            leavingcustomers[i] = new Customer(this.xCoordinate, this.bottom, this.top - i, ThreadLocalRandom.current().nextInt(1, 5 + 1));
            leavingcustomers[i].targetsquare = slot.getInteger("leavingcustomers" + i);
            leavingcustomers[i].posx = slot.getInteger("leavingposx" + i);
            leavingcustomers[i].posy = slot.getInteger("leavingposy" + i);
            leavingcustomers[i].startposx = slot.getInteger("leavingstartposx" + i);
            leavingcustomers[i].targetpixel = slot.getInteger("leavingtargetpixel" + i);
            leavingcustomers[i].locked = slot.getBoolean("leavinglocked" + i);
            leavingcustomers[i].readyfordeletion = slot.getBoolean("leavingreadyfordeletion" + i);
            leavingcustomers[i].arrivalTime = slot.getLong("leavingarrivalTime" + i);
            Customer.maxWaitTime = slot.getLong("leavingmaxWaitTime" + i);
        }
    }
}
