package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ShipTest {

    @Test
    public void testShipConstructor(){
        Destroyer c1 = new Destroyer();
        assertEquals(c1.getKind(), "Destroyer");
    }

    @Test
    public void testSetSquares(){
        Destroyer c1 = new Destroyer();
        c1.setOccupiedSquares(1, 'a', true);
        assertEquals(c1.getOccupiedSquares().get(0).getColumn(),'a');
    }
}
