package cs361.battleships.models;

import com.sun.jna.platform.win32.OaIdl;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BoardTest {

    @Test
    public void sinkAttackTest(){
        Board test = new Board();
        Ship mine = new Ship("MINESWEEPER");
        test.placeShip(mine, 3, 'D', true);

        Ship mine2 = new Ship("DESTROYER");
        test.placeShip(mine2, 6, 'G', false);


        Result sink = test.sinkAttack(3, 'D');
        assertEquals(AtackStatus.SUNK, sink.getResult());

        Result sink2 = test.sinkAttack(6, 'H');
        assertEquals(AtackStatus.SURRENDER, sink2.getResult());
    }

    @Test
    public void testGetSquareAt(){
        Board test = new Board();
        Ship mine = new Ship("MINESWEEPER");
        test.placeShip(mine, 3, 'D', true);

        Square tempSquare = test.getSquareAt(3, 'D');
        assertEquals(false, tempSquare.isHit());
        assertEquals(true, tempSquare.isCaptainsQuarters());
        assertEquals(3, tempSquare.getRow());
        assertEquals('D', tempSquare.getColumn());

        test.sinkAttack(3, 'D');
        tempSquare = test.getSquareAt(4, 'D');

        assertEquals(true, tempSquare.isHit());
        assertEquals(false, tempSquare.isCaptainsQuarters());
        assertEquals(4, tempSquare.getRow());
        assertEquals('D', tempSquare.getColumn());

    }

    @Test
    public void testGetShipAt(){
        Board test = new Board();
        Ship mine = new Ship("MINESWEEPER");
        test.placeShip(mine, 3, 'D', true);

        Ship tempShip = test.getShipAt(3, 'D');
        assertEquals("MINESWEEPER", tempShip.getKind());
        assertEquals(true, tempShip.isAtLocation(new Square(4, 'D')));
    }


}
