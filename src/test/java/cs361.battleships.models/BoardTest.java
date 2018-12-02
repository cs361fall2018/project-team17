package cs361.battleships.models;

import org.junit.Test;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BoardTest {

    private Board board;

    @Before
    public void setUp() {
        board = new Board();
    }

    @Test
    public void testInvalidPlacement() {
        assertFalse(board.placeShip(new Minesweeper(), 11, 'C', true, false));
    }

    @Test
    public void testPlaceMinesweeper() {
        assertTrue(board.placeShip(new Minesweeper(), 2, 'B', true, false));
    }

    @Test
    public void testAttackEmptySquare() {
        board.placeShip(new Minesweeper(), 2, 'B', true, false);
        Result result = board.attack(2, 'E', false);
        assertEquals(AtackStatus.MISS, result.getResult());
    }

    @Test
    public void testAttackShip() {
        Ship minesweeper = new Minesweeper();
        board.placeShip(minesweeper, 3, 'C', true, false);
        minesweeper = board.getShips().get(0);
        Result result = board.attack(3, 'C', false);
        result.setShip(minesweeper);
        assertEquals(AtackStatus.SURRENDER, result.getResult()); //Since no other ships were placed, we have destroyed the only one
        assertEquals(minesweeper, result.getShip());
    }

    @Test
    public void testAttackSameSquareMultipleTimes() {
        Ship minesweeper = new Minesweeper();
        board.placeShip(minesweeper, 1, 'A', true, false);
        board.attack(1, 'A', false);
        Result result = board.attack(1, 'A', false);
        assertEquals(AtackStatus.MISS, result.getResult());
    }

    @Test
    public void testAttackSameEmptySquareMultipleTimes() {
        Result initialResult = board.attack(1, 'A', false);
        assertEquals(AtackStatus.MISS, initialResult.getResult());
        Result result = board.attack(1, 'A', false);
        assertEquals(AtackStatus.MISS, result.getResult());
    }

    @Test
    public void testPlaceMultipleShipsOfSameType() {
        assertTrue(board.placeShip(new Minesweeper(), 2, 'B', true, false));
        assertFalse(board.placeShip(new Minesweeper(), 5, 'D', true, false));

    }

    @Test
    public void testCantPlaceMoreThan4Ships() {
        assertTrue(board.placeShip(new Minesweeper(), 2, 'B', true, false));
        assertTrue(board.placeShip(new Battleship(), 5, 'D', true, false));
        assertTrue(board.placeShip(new Destroyer(), 6, 'E', false, false));
        assertTrue(board.placeShip(new Submarine(true), 6, 'E', false, true));
        assertFalse(board.placeShip(new Ship(), 8, 'B', false, false));

    }

    @Test
    public void testCantPlaceShipsOnTopOfEachOther() {
        assertTrue(board.placeShip(new Minesweeper(), 2, 'B', true, false));
        assertFalse(board.placeShip(new Minesweeper(), 2, 'B', true, false));
    }

    @Test
    public void sinkAttackTest(){
        Board test = new Board();
        Ship mine = new Minesweeper();
        test.placeShip(mine, 3, 'D', true, false);

        Ship mine2 = new Destroyer();
        test.placeShip(mine2, 6, 'G', false, false);


        Result sink = test.attack(3, 'D',false);
        assertEquals(AtackStatus.SUNK, sink.getResult());

        Result sink2 = test.attack(6, 'H',false);
        assertEquals(AtackStatus.CAPTAIN, sink2.getResult());

        Result sink3 = test.attack(6, 'H',false);
        assertEquals(AtackStatus.SURRENDER, sink3.getResult());
    }

    @Test
    public void testGetSquareAt(){
        Board test = new Board();
        Ship mine = new Minesweeper();
        test.placeShip(mine, 3, 'D', true, false);

        Square tempSquare = test.getSquareAt(3, 'D');
        assertEquals(false, tempSquare.isHit(mine.getKind()));
        assertEquals(true, tempSquare.isCaptainsQuarters());
        assertEquals(3, tempSquare.getRow());
        assertEquals('D', tempSquare.getColumn());

        test.attack(3, 'D', false);
        tempSquare = test.getSquareAt(4, 'D');

        assertEquals(true, tempSquare.isHit(mine.getKind()));
        assertEquals(false, tempSquare.isCaptainsQuarters());
        assertEquals(4, tempSquare.getRow());
        assertEquals('D', tempSquare.getColumn());

    }

    @Test
    public void testGetShipAt(){
        Board test = new Board();
        Ship mine = new Minesweeper();
        test.placeShip(mine, 3, 'D', true, false);

        Ship tempShip = test.getShipAt(3, 'D');
        assertEquals("MINESWEEPER", tempShip.getKind());
        assertEquals(true, tempShip.isAtLocation(new Square(4, 'D')));
    }

    @Test
    public void testSonarRange() {
        Board test = new Board();
        assertFalse(test.sonar(12, 'B'));
        assertFalse(test.sonar(-1, 'B'));
        assertFalse(test.sonar(5, 'L'));
        assertFalse(test.sonar(5, 'A'));
    }

    // tests valid sonar placement with no pings outside of board.
    @Test
    public void testValidSonarPlacement() {
        Board test = new Board();

        test.sonar(5, 'E');
        List <Sonar> sonarResult = test.getSonar();
        assertTrue(sonarResult.get(0).getLocation().equals(new Square(5, 'E')));
        assertTrue(sonarResult.get(1).getLocation().equals(new Square(6, 'E')));
        assertTrue(sonarResult.get(2).getLocation().equals(new Square(7, 'E')));
        assertTrue(sonarResult.get(3).getLocation().equals(new Square(4, 'E')));
        assertTrue(sonarResult.get(4).getLocation().equals(new Square(3, 'E')));
        assertTrue(sonarResult.get(5).getLocation().equals(new Square(5, 'F')));
        assertTrue(sonarResult.get(6).getLocation().equals(new Square(5, 'G')));
        assertTrue(sonarResult.get(7).getLocation().equals(new Square(5, 'D')));
        assertTrue(sonarResult.get(8).getLocation().equals(new Square(5, 'C')));
        assertTrue(sonarResult.get(9).getLocation().equals(new Square(6, 'F')));
        assertTrue(sonarResult.get(10).getLocation().equals(new Square(6, 'D')));
        assertTrue(sonarResult.get(11).getLocation().equals(new Square(4, 'F')));
        assertTrue(sonarResult.get(12).getLocation().equals(new Square(4, 'D')));

    }

    //tests if the placement of sonar at 2 B or 11 K adds any pings that go outside of the board.
    @Test
    public void testCornerSonarPlacement() {
        Board test = new Board();

        test.sonar(2, 'B');
        List <Sonar> sonarResult = test.getSonar();
        assertTrue(sonarResult.get(0).getLocation().equals(new Square(2, 'B')));
        assertTrue(sonarResult.get(1).getLocation().equals(new Square(3, 'B')));
        assertTrue(sonarResult.get(2).getLocation().equals(new Square(4, 'B')));
        assertTrue(sonarResult.get(3).getLocation().equals(new Square(2, 'C')));
        assertTrue(sonarResult.get(4).getLocation().equals(new Square(2, 'D')));
        assertTrue(sonarResult.get(5).getLocation().equals(new Square(3, 'C')));

        test.sonar(11, 'K');
        assertTrue(sonarResult.get(6).getLocation().equals(new Square(11, 'K')));
        assertTrue(sonarResult.get(7).getLocation().equals(new Square(10, 'K')));
        assertTrue(sonarResult.get(8).getLocation().equals(new Square(9, 'K')));
        assertTrue(sonarResult.get(9).getLocation().equals(new Square(11, 'J')));
        assertTrue(sonarResult.get(10).getLocation().equals(new Square(11, 'I')));
        assertTrue(sonarResult.get(11).getLocation().equals(new Square(10, 'J')));
    }

    @Test
    public void testSonarFindsShip () {
        Board test = new Board();

        assertTrue(test.placeShip(new Minesweeper(), 2, 'B', true, false));

        test.sonar(2, 'B');
        List<Sonar> sonarResult = test.getSonar();
        assertEquals(sonarResult.get(0).getResult(), SonarStatus.VISIBLE);
    }

    @Test
    public void testMoveFleet(){
        Board test = new Board();
        test.placeShip(new Minesweeper(), 3, 'C', true);
        test.placeShip(new Destroyer(), 5, 'C', true);
        test.placeShip(new Battleship(), 8, 'C', true);

        assertEquals(3, test.getShips().get(0).getOccupiedSquares().get(0).getRow());
        assertEquals('C', test.getShips().get(0).getOccupiedSquares().get(0).getColumn());

        test.moveFleet('N');
        assertEquals(2, test.getShips().get(0).getOccupiedSquares().get(0).getRow());
        assertEquals('C', test.getShips().get(0).getOccupiedSquares().get(0).getColumn());

        test.moveFleet('E');
        assertEquals(2, test.getShips().get(0).getOccupiedSquares().get(0).getRow());
        assertEquals('D', test.getShips().get(0).getOccupiedSquares().get(0).getColumn());

        test.moveFleet('S');
        assertEquals(3, test.getShips().get(0).getOccupiedSquares().get(0).getRow());
        assertEquals('D', test.getShips().get(0).getOccupiedSquares().get(0).getColumn());

        test.moveFleet('W');
        assertEquals(3, test.getShips().get(0).getOccupiedSquares().get(0).getRow());
        assertEquals('C', test.getShips().get(0).getOccupiedSquares().get(0).getColumn());

    }
}
