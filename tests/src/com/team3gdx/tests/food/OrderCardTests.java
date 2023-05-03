package com.team3gdx.tests.food;

import com.team3gdx.game.food.OrderCard;
import com.team3gdx.tests.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class OrderCardTests {

    @Test
    public void getName() {
        OrderCard orderCard = new OrderCard("salad");
        assertEquals("salad", orderCard.getName());
        orderCard = null;
    }

    @Test
    public void getTexture() {
        OrderCard orderCard = new OrderCard("salad");
        assertNotNull(orderCard.getTexture());
        orderCard = null;
    }

    @Test
    public void getX() {
        OrderCard orderCard = new OrderCard("salad");
        assertEquals(50, orderCard.getX(), 0.1);
        orderCard = null;
    }

    @Test
    public void getY() {
        OrderCard orderCard = new OrderCard("salad");
        assertEquals(50, orderCard.getY(), 0.1);
        orderCard = null;
    }

    @Test
    public void getHeight() {
        OrderCard orderCard = new OrderCard("salad");
        assertEquals(115, orderCard.getHeight(), 0.1);
        orderCard = null;
    }

    @Test
    public void getWidth() {
        OrderCard orderCard = new OrderCard("salad");
        assertEquals(115, orderCard.getWidth(), 0.1);
        orderCard = null;
    }

    @Test
    public void hasExpired() {
        OrderCard orderCard = new OrderCard("salad");
        assertFalse(orderCard.hasExpired());
        orderCard = null;
    }
}
