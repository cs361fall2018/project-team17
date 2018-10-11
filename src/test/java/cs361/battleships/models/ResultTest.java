package cs361.battleships.models;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ResultTest {

    Result test1;
    Result test2;

    @Before
    public void beforeTest(){
        Square square1 = new Square(1, 'a', false);
        Result test1 = new Result(square1);

        Ship testShip = new Ship("Destroyer", 4);
        testShip.setOccupiedSquares(4, 'c', true);
        Square square2 = new Square(4, 'c', true);
        Result test2 = new Result(square2, testShip);
    }

    @Test
    public void testGetResult(){
        System.out.println(test1.getResult());
//        assertEquals(AtackStatus.MISS, test1.getResult());
    }
}
