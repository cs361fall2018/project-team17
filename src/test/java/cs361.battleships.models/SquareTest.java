package cs361.battleships.models;

import org.junit.Test;

import javax.validation.constraints.Null;

import static org.junit.Assert.*;


public class SquareTest {

    @Test
    public void testDefaultConstructors() {
        Square square = new Square();
        assertEquals(square.getRow(), 0);
        assertEquals(square.getColumn(), '\0');
        assertEquals(square.getHit(), 0);
        assertEquals(square.isCaptainsQuarters(), false);

        Square square2 = new Square(10, 'B', true);
        assertEquals(square2.getRow(), 10);
        assertEquals(square2.getColumn(), 'B');
        assertEquals(square2.getHit(), 0);
        assertEquals(square2.isCaptainsQuarters(), true);
    }

    @Test
    public void testIsOutOfBoundTest() {
        Square square = new Square(11, 'A');
        assertTrue(square.isOutOfBounds());

        square = new Square(1, 'Z');
        assertTrue(square.isOutOfBounds());

        square = new Square(1, 'a');
        assertTrue(square.isOutOfBounds());

        square = new Square(0, 'A');
        assertTrue(square.isOutOfBounds());
    }

    @Test
    public void testIsHit() {
        Square square = new Square(1, 'A');
        assertFalse(square.isHit("MINESWEEPER"));

        square.hit();
        assertTrue(square.isHit("MINESWEEPER"));


        Square square2 = new Square(4, 'C');
        square2.setCaptainsQuarters(true);
        assertFalse(square2.isHit("DESTROYER"));

        square2.hit();
        assertFalse(square2.isHit("DESTROYER"));


        square2.hit();
        assertTrue(square2.isHit("DESTROYER"));


        Square square3 = new Square(4, 'C');
        assertFalse(square3.isHit("DESTROYER"));

        square3.hit();
        assertTrue(square3.isHit("DESTROYER"));
    }

    @Test
    public void testEquals() {
        Square square1 = new Square(1, 'A');
        Square square2 = new Square(1, 'A');

        assertTrue(square1.equals(square2));
        assertEquals(square1.hashCode(), square2.hashCode());

        assertFalse(square1.equals(new Result()));
    }

    @Test
    public void testNotEquals() {
        Square square1 = new Square(1, 'A');
        Square square2 = new Square(2, 'A');

        assertFalse(square1.equals(square2));
        assertNotEquals(square1.hashCode(), square2.hashCode());
    }

    @Test
    public void testCaptainsQuarters(){
        Square test = new Square(3, 'D');

        assertFalse(test.isCaptainsQuarters());

        test.setCaptainsQuarters(true);
        assertTrue(test.isCaptainsQuarters());

        test.setCaptainsQuarters(false);
        assertFalse(test.isCaptainsQuarters());

    }

    @Test
    public void testToString() {
        Square test = new Square( 6, 'E');
        assertEquals(test.toString(), "(6, E)");
    }
}
