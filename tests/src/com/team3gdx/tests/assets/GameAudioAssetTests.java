package com.team3gdx.tests.assets;

import com.badlogic.gdx.Gdx;
import com.team3gdx.tests.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class GameAudioAssetTests {

    @Test
    public void BackgroundMusicExists() {
        assertTrue(Gdx.files.internal("audio/soundFX/cash-register-opening.mp3").exists());
        assertTrue(Gdx.files.internal("audio/soundFX/chopping.mp3").exists());
        assertTrue(Gdx.files.internal("audio/soundFX/frying.mp3").exists());
        assertTrue(Gdx.files.internal("audio/soundFX/money-collect.mp3").exists());
        assertTrue(Gdx.files.internal("audio/soundFX/timer-bell-ring.mp3").exists());
    }
}
