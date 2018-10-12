package cs361.battleships.models;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ResultTest {

    Result test1;
    Result test2;

    @Before
    public void beforeTest(){
        Square square1 = new Square(1, 'A', false);
        test1 = new Result( square1 );

        Destroyer testShip = new Destroyer();
        testShip.setOccupiedSquares(4, 'C', true);
        Square square2 = new Square(4, 'C', true);
        test2 = new Result(square2, testShip);
    }

    @Test
    public void testGetResult(){
//        System.out.println(test1.getResult());
//        if(AtackStatus.MISS == AtackStatus.MISS){
//            System.out.println("winner");
//        }
//        test1.getShip();
        assertEquals(AtackStatus.MISS, test1.getResult());

//        test2.getResult();
        assertEquals(AtackStatus.HIT, test2.getResult());
//        assertEquals(AtackStatus.MISS, test1.getResult());
//        assertEquals(AtackStatus.MISS, test1.getResult());
    }
}
