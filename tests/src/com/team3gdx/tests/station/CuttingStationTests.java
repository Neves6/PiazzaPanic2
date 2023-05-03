package com.team3gdx.tests.station;

import com.team3gdx.game.station.CuttingStation;
import org.junit.Test;
import com.team3gdx.tests.GdxTestRunner;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class CuttingStationTests {
    @Test
    public void lockCook(){
        CuttingStation cutting = new CuttingStation(null);

        assertTrue(cutting.lockCook());

        cutting = null;
    }
}
