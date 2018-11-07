package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.*;


public class SquareTest {

    @Test
    public void testCaptainsQuarters(){
        Square test = new Square(3, 'D');

        assertFalse(test.isCaptainsQuarters());

        test.setCaptainsQuarters(true);
        assertTrue(test.isCaptainsQuarters());

        test.setCaptainsQuarters(false);
        assertFalse(test.isCaptainsQuarters());

    }
}
