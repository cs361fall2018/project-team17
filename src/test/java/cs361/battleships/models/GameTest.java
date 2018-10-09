package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GameTest {

    @Test
    public void testShipPlacement(){
        Game g = new Game();
        Destroyer d = new Destroyer();
        assertTrue(g.placeShip(d, 1, 'a', false));

    }
}
