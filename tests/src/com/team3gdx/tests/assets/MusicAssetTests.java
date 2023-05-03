package com.team3gdx.tests.assets;

import com.badlogic.gdx.Gdx;
import com.team3gdx.tests.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class MusicAssetTests {

    @Test
    public void BackgroundMusicExists() {
        assertTrue(Gdx.files.internal("uielements/MainScreenMusic.ogg").exists());
        assertTrue(Gdx.files.internal("uielements/GameMusic.ogg").exists());
    }
}
