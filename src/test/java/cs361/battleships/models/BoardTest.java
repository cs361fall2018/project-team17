package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
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

        //assertTrue(board.placeShip(new Ship("Destroyer", 3), 11, 'C', true));       //Valid ship placement
    }
}
