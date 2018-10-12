package cs361.battleships.models;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ResultTest {

    Result test1;
    Result test2;
    Result test3;
    Result test4;

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
    }

    @Test
    public void testGetResult(){
//        System.out.println(test1.getResult());
//        if(AtackStatus.MISS == AtackStatus.MISS){
//            System.out.println("winner");
//        }
//        test1.getShip();
        assertEquals(AtackStatus.MISS, test1.getResult(3));

//        test2.getResult();
        assertEquals(AtackStatus.HIT, test2.getResult(3));
        assertEquals(AtackStatus.INVALID, test2.getResult(3));

    }
}
