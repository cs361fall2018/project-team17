package cs361.battleships.models;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ShipTest {

    @Test
    public void testBaseConstructor() {
        Ship blankShip = new Ship();
        assertTrue(blankShip.getOccupiedSquares().size() == 0);
        assertTrue(blankShip.getKind() == null);
        assertTrue(blankShip.getCaptainsQuarters() == 0);
    }

    @Test
    public void testPlaceMinesweeperHorizontaly() {
        Ship minesweeper = new Minesweeper();
        minesweeper.place('A', 1, false);
        List<Square> occupiedSquares = minesweeper.getOccupiedSquares();
        ArrayList<Object> expected = new ArrayList<>();
        expected.add(new Square(1, 'A'));
        expected.add(new Square(1, 'B'));
        assertEquals(expected, occupiedSquares);
    }

    @Test
    public void testPlaceMinesweeperVertically() {
        Ship minesweeper = new Minesweeper();
        minesweeper.place('A', 1, true);
        List<Square> occupiedSquares = minesweeper.getOccupiedSquares();
        ArrayList<Object> expected = new ArrayList<>();
        expected.add(new Square(1, 'A'));
        expected.add(new Square(2, 'A'));
        assertEquals(expected, occupiedSquares);
    }

    @Test
    public void testPlaceDestroyerHorizontaly() {
        Ship minesweeper = new Destroyer();
        minesweeper.place('A', 1, false);
        List<Square> occupiedSquares = minesweeper.getOccupiedSquares();
        ArrayList<Object> expected = new ArrayList<>();
        expected.add(new Square(1, 'A'));
        expected.add(new Square(1, 'B'));
        expected.add(new Square(1, 'C'));
        assertEquals(expected, occupiedSquares);
    }

    @Test
    public void testPlaceDestroyerVertically() {
        Ship minesweeper = new Destroyer();
        minesweeper.place('A', 1, true);
        List<Square> occupiedSquares = minesweeper.getOccupiedSquares();
        ArrayList<Object> expected = new ArrayList<>();
        expected.add(new Square(1, 'A'));
        expected.add(new Square(2, 'A'));
        expected.add(new Square(3, 'A'));
        assertEquals(expected, occupiedSquares);
    }

    @Test
    public void testPlaceBattleshipHorizontaly() {
        Ship minesweeper = new Battleship();
        minesweeper.place('A', 1, false);
        List<Square> occupiedSquares = minesweeper.getOccupiedSquares();
        ArrayList<Object> expected = new ArrayList<>();
        expected.add(new Square(1, 'A'));
        expected.add(new Square(1, 'B'));
        expected.add(new Square(1, 'C'));
        expected.add(new Square(1, 'D'));
        assertEquals(expected, occupiedSquares);
    }

    @Test
    public void testPlaceBattleshipVertically() {
        Ship minesweeper = new Battleship();
        minesweeper.place('A', 1, true);
        List<Square> occupiedSquares = minesweeper.getOccupiedSquares();
        ArrayList<Object> expected = new ArrayList<>();
        expected.add(new Square(1, 'A'));
        expected.add(new Square(2, 'A'));
        expected.add(new Square(3, 'A'));
        expected.add(new Square(4, 'A'));
        assertEquals(expected, occupiedSquares);
    }

    @Test
    public void testShipOverlaps() {
        Ship minesweeper1 = new Minesweeper();
        minesweeper1.place('A', 1, true);

        Ship minesweeper2 = new Minesweeper();
        minesweeper2.place('A', 1, true);

        assertTrue(minesweeper1.overlaps(minesweeper2));
    }

    @Test
    public void testShipsDontOverlap() {
        Ship minesweeper1 = new Minesweeper();
        minesweeper1.place('A', 1, true);

        Ship minesweeper2 = new Minesweeper();
        minesweeper2.place('C', 2, true);

        assertFalse(minesweeper1.overlaps(minesweeper2));
    }

    @Test
    public void testIsAtLocation() {
        Ship minesweeper = new Battleship();
        minesweeper.place('A', 1, true);

        assertTrue(minesweeper.isAtLocation(new Square(1, 'A')));
        assertTrue(minesweeper.isAtLocation(new Square(2, 'A')));
    }

    @Test
    public void testHit() {
        Ship minesweeper = new Battleship();
        minesweeper.place('B', 2, true);

        Result result = minesweeper.attack(2, 'B', false);
        result.setShip(minesweeper);

        assertEquals(AtackStatus.HIT, result.getResult());
        assertEquals(minesweeper, result.getShip());
        assertEquals(new Square(2, 'B'), result.getLocation());
    }

    @Test
    public void testSink() {
        Ship minesweeper = new Minesweeper();
        minesweeper.place('A', 1, true);

        minesweeper.attack(2, 'A', false);
        Result result = minesweeper.attack(1, 'A', false);
        result.setShip(minesweeper);

        assertEquals(AtackStatus.SUNK, result.getResult());
        assertEquals(minesweeper, result.getShip());
        assertEquals(new Square(1, 'A'), result.getLocation());
    }

    @Test
    public void testOverlapsBug() {
        Ship minesweeper = new Minesweeper();
        Ship destroyer = new Destroyer();
        minesweeper.place('C', 5, false);
        destroyer.place('C', 5, false);
        assertTrue(minesweeper.overlaps(destroyer));
    }

    @Test
    public void testAttackSameSquareTwice() {
        Ship minesweeper = new Minesweeper();
        minesweeper.place('A', 2, true);
        var result = minesweeper.attack(2, 'A', false);
        assertEquals(AtackStatus.HIT, result.getResult());
        result = minesweeper.attack(2, 'A', false);
        assertEquals(AtackStatus.INVALID, result.getResult());

        Ship destroyer = new Destroyer();
        destroyer.place('D', 4, true);
        result = destroyer.attack(5, 'D', false);
        assertEquals(AtackStatus.CAPTAIN, result.getResult());
        result = destroyer.attack(5, 'D', false);
        assertEquals(AtackStatus.HIT, result.getResult());

        Ship minesweeper2 = new Minesweeper();
        minesweeper2.place('D', 4, true);
        result = minesweeper2.attack(4, 'D', false);
        assertEquals(AtackStatus.HIT, result.getResult());
        result = minesweeper2.attack(4, 'D', false);
        assertEquals(AtackStatus.INVALID, result.getResult());
    }

    @Test
    public void testEquals() {
        Ship minesweeper1 = new Minesweeper();
        minesweeper1.place('A', 1, true);
        Ship minesweeper2 = new Minesweeper();
        minesweeper2.place('A', 1, true);
        assertTrue(minesweeper1.equals(minesweeper2));
        assertEquals(minesweeper1.hashCode(), minesweeper2.hashCode());
    }

    @Test
    public void testCaptainsQuarters(){
        Ship ship1 = new Minesweeper();
        Ship ship2 = new Destroyer();
        Ship ship3 = new Battleship();

        assertEquals(0, ship1.getCaptainsQuarters());
        assertEquals(1, ship2.getCaptainsQuarters());
        assertEquals(2, ship3.getCaptainsQuarters());

    }

    @Test
    public void testToString() {
        Ship test = new Minesweeper();

        assertEquals(test.toString(), "MINESWEEPER[]");
    }

}


