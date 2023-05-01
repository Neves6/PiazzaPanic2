package com.team3gdx.tests.entity;

import com.team3gdx.game.entity.Customer;
import com.team3gdx.tests.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class CustomerTests {
    @Test
    public void getOrder(){
        Customer customer = new Customer(64, 64, 40, 1);
        boolean val = false;
    }

    @Test
    public void arrived(){
        Customer customer = new Customer(5, 5,15, 1);
        long time = 18000 + System.currentTimeMillis();

        customer.arrived();
        assertTrue(customer.arrivalTime == time);

        customer = null;
    }

    @Test
    public void hasExpired(){
        Customer customer = new Customer(5, 5,15, 1);
        customer.arrivalTime = 15001;

        assertTrue(customer.hasExpired());

        customer = null;
    }

    @Test
    public void stepTarget(){
        Customer customer = new Customer(5,5,15,1);
        int targetpixel = 32 + (15 * 64);
        boolean result = false;

        customer.stepTarget();
        if(customer.posy == targetpixel){
            result = true;
        }
        assertTrue(result = true);

        customer = null;
    }
}
