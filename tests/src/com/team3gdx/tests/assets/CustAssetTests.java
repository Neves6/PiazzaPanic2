package com.team3gdx.tests.assets;

import com.badlogic.gdx.Gdx;
import com.team3gdx.tests.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class CustAssetTests {

    @Test
    public void AssetsExist() {
        for (int i = 1; i <= 5; i++) {
            assertTrue(Gdx.files.internal("entities/cust" + i + "b.png").exists());
            assertTrue(Gdx.files.internal("entities/cust" + i + "f.png").exists());
            assertTrue(Gdx.files.internal("entities/cust" + i + "l.png").exists());
            assertTrue(Gdx.files.internal("entities/cust" + i + "r.png").exists());
        }
    }
}
