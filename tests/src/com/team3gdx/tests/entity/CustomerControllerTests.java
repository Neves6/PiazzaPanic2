package com.team3gdx.tests.entity;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.team3gdx.game.entity.CustomerController;
import com.team3gdx.game.screen.GameScreen;
import com.team3gdx.tests.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class CustomerControllerTests {

    @Test
    public void saveCC(){
        TiledMap map = new TmxMapLoader().load("map/art_map/customertest.tmx");
        CustomerController customerController = new CustomerController(map);
        customerController.saveCC(0);
        customerController = null;
    }

    @Test
    public void loadCC(){
        TiledMap map = new TmxMapLoader().load("map/art_map/customertest.tmx");
        CustomerController customerController = new CustomerController(map);
        customerController.loadCC(0);
        customerController = null;
    }

    @Test
    public void saveReloadEquivalency(){
        TiledMap map = new TmxMapLoader().load("map/art_map/customertest.tmx");
        CustomerController customerController1 = new CustomerController(map);
        CustomerController customerController2 = new CustomerController(map);
        customerController1.saveCC(0);
        customerController2.loadCC(0);
        assertEquals(customerController1.lockout, customerController2.lockout);
        assertEquals(customerController1.customers.length, customerController2.customers.length);
        assertEquals(customerController1.leavingcustomers.length, customerController2.leavingcustomers.length);
        assertEquals(customerController1.gameMap, customerController2.gameMap);
        assertEquals(customerController1.top, customerController2.top);
        assertEquals(customerController1.bottom, customerController2.bottom);
        assertEquals(customerController1.xCoordinate, customerController2.xCoordinate);
        customerController1 = null;
        customerController2 = null;
    }
}
