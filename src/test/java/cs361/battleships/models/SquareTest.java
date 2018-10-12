package cs361.battleships.models;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SquareTest {

    private Square test;
    private Square test2;

    @Before
    public void runBefore() {
        test = new Square(1, 'a', true);
        test2 = new Square(5, 'c', false);
    }

    @Test
    public void testCheckHit() {
        assertEquals(true, test.checkHit());

        assertFalse(test2.checkHit());

    }

    @Test
    public void testCheckValid(){
        assertEquals(false, test.checkValid());

        test2.checkHit();
        assertEquals(true, test2.checkValid());
    }

    @Test
    public void testCompareLocation(){
        assertEquals("1a", test.compareLocation());

        assertEquals("5c", test2.compareLocation());
    }

}
