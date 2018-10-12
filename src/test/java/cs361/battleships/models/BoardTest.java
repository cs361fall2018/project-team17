package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;


public class BoardTest {

    @Test
    public void testInvalidPlacement() {
        Board board = new Board();
        assertFalse(board.placeShip(new Ship("Minesweeper", 2), 11, 'C', true));    //X outside of bounds
        assertFalse(board.placeShip(new Ship("Destroyer", 3), 3, 'K', true));    //Y outside of bounds
        assertFalse(board.placeShip(new Ship("Battleship", 4), 11, 'Z', true));    //X and Y outside of bounds
        assertFalse(board.placeShip(new Ship("Destroyer", 3), 7, 'A', false));      //X length overflows outside the board
        assertFalse(board.placeShip(new Ship("Battleship", 4), 2, 'H', true));     //Y length overflows outside the board
    }

    @Test
    public void testPlaceShip(){
        Board board = new Board();
        assertTrue(board.placeShip(new Ship("Destroyer", 3), 6, 'C', true));       //Valid ship placement
    }

    @Test
    public void testAttack(){
        Board board1 = new Board();
        board1.placeShip(new Ship("Destroyer", 3),6, 'E', true);
        board1.placeShip(new Ship("Minesweeper", 2), 2, 'A', false);
        assertSame(board1.attack(6, 'E').getResult(), AtackStatus.HIT);
        assertSame(board1.attack(11, 'A').getResult(), AtackStatus.INVALID);
        assertSame(board1.attack(1, 'A').getResult(), AtackStatus.MISS);
        assertSame(board1.attack(7, 'E').getResult(), AtackStatus.HIT);
        assertSame(board1.attack(8, 'E').getResult(), AtackStatus.SUNK);
        assertSame(board1.attack(9, 'E').getResult(), AtackStatus.MISS);
        assertSame(board1.attack(2, 'A').getResult(), AtackStatus.HIT);
        assertSame(board1.attack(2, 'B').getResult(), AtackStatus.SURRENDER);
    }
}
