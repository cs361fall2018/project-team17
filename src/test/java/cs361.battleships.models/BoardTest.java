package cs361.battleships.models;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

public class BoardTest {

    private Board board;

    @Before
    public void setUp() {
        board = new Board();
    }

    @Test
    public void testInvalidPlacement() {
        assertFalse(board.placeShip(new Ship("MINESWEEPER"), 11, 'C', true));
    }

    @Test
    public void testPlaceMinesweeper() {
        assertTrue(board.placeShip(new Ship("MINESWEEPER"), 2, 'B', true));
    }

    @Test
    public void testAttackEmptySquare() {
        board.placeShip(new Ship("MINESWEEPER"), 2, 'B', true);
        Result result = board.attack(2, 'E');
        assertEquals(AtackStatus.MISS, result.getResult());
    }

    @Test
    public void testAttackShip() {
        Ship minesweeper = new Ship("MINESWEEPER");
        board.placeShip(minesweeper, 3, 'C', true);
        minesweeper = board.getShips().get(0);
        Result result = board.attack(3, 'C');
        assertEquals(AtackStatus.HIT, result.getResult());
    }

    @Test
    public void testAttackSameSquareMultipleTimes() {
        Ship minesweeper = new Ship("MINESWEEPER");
        board.placeShip(minesweeper, 1, 'A', true);
        board.attack(1, 'A');
        Result result = board.attack(1, 'A');
        assertEquals(AtackStatus.INVALID, result.getResult());
    }

    @Test
    public void testAttackSameEmptySquareMultipleTimes() {
        Result initialResult = board.attack(1, 'A');
        assertEquals(AtackStatus.MISS, initialResult.getResult());
        Result result = board.attack(1, 'A');
        assertEquals(AtackStatus.INVALID, result.getResult());
    }

    @Test
    public void testSurrender() {
        board.placeShip(new Ship("MINESWEEPER"), 2, 'B', true);
        board.attack(2, 'B');
        var result = board.attack(3, 'B');
        assertEquals(AtackStatus.SURRENDER, result.getResult());
    }

    @Test
    public void testPlaceMultipleShipsOfSameType() {
        assertTrue(board.placeShip(new Ship("MINESWEEPER"), 2, 'B', true));
        assertFalse(board.placeShip(new Ship("MINESWEEPER"), 5, 'D', true));

    }

    @Test
    public void testCantPlaceMoreThan3Ships() {
        assertTrue(board.placeShip(new Ship("MINESWEEPER"), 2, 'B', true));
        assertTrue(board.placeShip(new Ship("BATTLESHIP"), 5, 'D', true));
        assertTrue(board.placeShip(new Ship("DESTROYER"), 6, 'E', false));
        assertFalse(board.placeShip(new Ship(""), 8, 'B', false));

    }

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
