package cs361.battleships.models;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;


public class BoardTest {

    @Test
    public void testInvalidPlacement() {
        Board board = new Board();
        assertFalse(board.placeShip(new Ship("MINESWEEPER", 2), 11, 'C', true));    //X outside of bounds
        assertFalse(board.placeShip(new Ship("DESTROYER", 3), 3, 'K', true));    //Y outside of bounds
        assertFalse(board.placeShip(new Ship("BATTLESHIP", 4), 11, 'Z', true));    //X and Y outside of bounds
        assertFalse(board.placeShip(new Ship("DESTROYER", 3), 8, 'A', false));      //X length overflows outside the board
        assertFalse(board.placeShip(new Ship("BATTLESHIP", 4), 2, 'H', true));     //Y length overflows outside the board
    }

    @Test
    public void testPlaceShip(){
        Board board = new Board();
        assertTrue(board.placeShip(new Ship("DESTROYER", 3), 6, 'C', true));       //Valid ship placement
        assertTrue(board.placeShip(new Ship("MINESWEEPER", 2), 8, 'J', false));          //Valid ship placement
    }

    @Test
    public void testAttack(){
        Board board1 = new Board();
        board1.placeShip(new Ship("DESTROYER", 3),6, 'E', true);
        assertSame(board1.attack(6, 'E').getResult(), AtackStatus.HIT);
        assertSame(board1.attack(11, 'A').getResult(), AtackStatus.INVALID);
        assertSame(board1.attack(1, 'A').getResult(), AtackStatus.MISS);
        assertSame(board1.attack(6, 'F').getResult(), AtackStatus.HIT);
        assertSame(board1.attack(6, 'G').getResult(), AtackStatus.SURRENDER);
        assertSame(board1.attack(9, 'E').getResult(), AtackStatus.MISS);
    }
}
