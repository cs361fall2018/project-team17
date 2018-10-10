package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ShipTest {

    @Test
    public void testShipConstructor(){
        Destroyer c1 = new Destroyer();
        assertEquals(c1.getKind(), "Destroyer");
    }

    @Test
    public void testSetOccupiedSquares(){
        Destroyer d1 = new Destroyer();
        assertTrue(d1.setOccupiedSquares(1, 'A', true));
        for (int i = 0; i < d1.getLength(); i++){
            assertEquals(d1.getOccupiedSquares().get(i).getColumn(),'A' );
            assertEquals(d1.getOccupiedSquares().get(i).getRow(), i+1 );
        }
        assertTrue(d1.setOccupiedSquares(1, 'A', false));
        for (int i = 0; i < d1.getLength(); i++){
            assertEquals(d1.getOccupiedSquares().get(i).getColumn(),'A'+i );
            assertEquals(d1.getOccupiedSquares().get(i).getRow(), 1 );
        }

        assertFalse(d1.setOccupiedSquares(1, 'a', false));
        assertFalse(d1.setOccupiedSquares(1, '@', false));
        assertFalse(d1.setOccupiedSquares(1, 'K', false));
        assertFalse(d1.setOccupiedSquares(25, 'A', false));
        assertFalse(d1.setOccupiedSquares(0, 'A', false));
    }
}
