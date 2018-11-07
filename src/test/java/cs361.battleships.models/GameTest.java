package cs361.battleships.models;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GameTest {

    @Test
    public void testAttack(){
        Game test = new Game();
        boolean mine = test.placeShip((new Ship("MINESWEEPER")), 3, 'D', true);
        boolean dest = test.placeShip((new Ship("DESTROYER")), 6, 'G', false);
        boolean batt = test.placeShip((new Ship("BATTLESHIP")), 9, 'B', false);

        assertEquals(true, test.attack(3,'H'));
        assertEquals(true, test.attack(4, 'G'));
        assertEquals(false, test.attack(4, 'G'));


    }
}
