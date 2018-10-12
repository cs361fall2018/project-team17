package cs361.battleships.models;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ResultTest {

    Result test1;
    Result test2;
    Result test3;
    Result test4;

    Result lastResult;
    Result lastResult2;

    @Before
    public void beforeTest(){
        Square square1 = new Square(1, 'A', false);
        test1 = new Result( square1 );

        Destroyer testShip = new Destroyer();
        testShip.setOccupiedSquares(4, 'C', true);
        Square square2 = new Square(4, 'C', true);
        test2 = new Result(square2, testShip);

        Square square3 = new Square(5, 'C', true);
        test3 = new Result(square3, testShip);

        Square square4 = new Square(6, 'C', true);
        test4 = new Result(square4, testShip);

        Minesweeper lastShip = new Minesweeper();
        lastShip.setOccupiedSquares(2, 'A', true);
        Square square5 = new Square(2, 'A', true);
        lastResult = new Result(square5, lastShip);

        Square square6 = new Square(3, 'A', true);
        lastResult2 = new Result(square6, lastShip);

    }

    @Test
    public void testGetStatus(){
        assertEquals(AtackStatus.MISS, test1.getStatus(3));

        assertEquals(AtackStatus.HIT, test2.getStatus(3));
        assertEquals(AtackStatus.INVALID, test2.getStatus(3));

        test3.getStatus(3);

        assertEquals(AtackStatus.SUNK, test4.getStatus(3));

        lastResult.getStatus(1);
        assertEquals(AtackStatus.SURRENDER, lastResult2.getStatus(1));
    }
}
