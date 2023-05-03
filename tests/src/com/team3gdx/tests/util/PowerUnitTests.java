package com.team3gdx.tests.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.team3gdx.game.util.PowerUnit;
import org.junit.Test;
import com.team3gdx.tests.GdxTestRunner;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class PowerUnitTests {
    @Test
    public void getPower(){
        PowerUnit powerunit = new PowerUnit(1, new Texture(Gdx.files.internal("uielements/power1.png")), 0.5f,0.5f);
        assertEquals(1, powerunit.getPower(), 0);

        powerunit = null;
    }

    @Test
    public void isVisible(){
        PowerUnit powerunit = new PowerUnit(1, new Texture(Gdx.files.internal("uielements/power1.png")), 0.5f,0.5f);
        assertTrue(powerunit.isVisible());

        powerunit = null;
    }

    @Test
    public void getTexture(){
        PowerUnit powerunit = new PowerUnit(1, new Texture(Gdx.files.internal("uielements/power1.png")), 0.5f,0.5f);
        assertNotNull(powerunit.getTexture());

        powerunit = null;
    }

    @Test
    public void getX(){
        PowerUnit powerunit = new PowerUnit(1, new Texture(Gdx.files.internal("uielements/power1.png")), 0.5f,0.5f);
        assertEquals(0.5f, powerunit.getX(), 0);

        powerunit = null;
    }

    @Test
    public void getY(){
        PowerUnit powerunit = new PowerUnit(1, new Texture(Gdx.files.internal("uielements/power1.png")), 0.5f,0.5f);
        assertEquals(0.5f, powerunit.getY(), 0);

        powerunit = null;
    }

    @Test
    public void dispose(){
        PowerUnit powerunit = new PowerUnit(1, new Texture(Gdx.files.internal("uielements/power1.png")), 0.5f,0.5f);
        assertTrue(powerunit.dispose());

        powerunit = null;
    }
}
