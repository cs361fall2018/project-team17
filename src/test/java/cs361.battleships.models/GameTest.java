package cs361.battleships.models;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GameTest {

    @Test
    public void testAttack(){
        Game test = new Game();

        assertEquals(true, test.attack(3,'H'));
        assertEquals(true, test.attack(4, 'G'));
        assertEquals(false, test.attack(4, 'G'));


    }

    @Test
    public void testPlaceShip() {
        Game test = new Game();
        assertTrue(test.placeShip(new Ship("MINESWEEPER"), 2, 'B', false));
        assertFalse(test.placeShip(new Ship("MINESWEEPER"), 1, 'A', false));
    }

    @Test
    public void testSonarAttack() {
        Game test = new Game();

        assertTrue(test.sonar(6, 'E'));
    }
}
