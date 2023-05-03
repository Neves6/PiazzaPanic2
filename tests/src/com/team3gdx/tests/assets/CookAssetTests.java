package com.team3gdx.tests.assets;

import com.badlogic.gdx.Gdx;
import com.team3gdx.tests.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class CookAssetTests {

    @Test
    public void AssetsExist() {
        for (int i = 1; i <= 3; i++) {
            assertTrue(Gdx.files.internal("entities/cook_walk_" + i + ".png").exists());
            assertTrue(Gdx.files.internal("entities/cook_walk_hands_" + i + ".png").exists());
        }
    }
}
