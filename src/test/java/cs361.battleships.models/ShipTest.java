package cs361.battleships.models;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ShipTest {

    @Test
    public void testCaptainsQuarters(){
        Ship ship1 = new Ship("MINESWEEPER");
        Ship ship2 = new Ship("DESTROYER");
        Ship ship3 = new Ship("BATTLESHIP");

        assertEquals(0, ship1.getCaptainsQuarters());
        assertEquals(1, ship2.getCaptainsQuarters());
        assertEquals(2, ship3.getCaptainsQuarters());

    }

}


