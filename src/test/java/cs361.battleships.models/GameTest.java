package cs361.battleships.models;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GameTest {

    @Test
    public void testAttack(){
        Game test = new Game();

        assertEquals(true, test.attack(3,'H', false));
        assertEquals(true, test.attack(4, 'G', false));


    }

    @Test
    public void testPlaceShip() {
        Game test = new Game();
        assertTrue(test.placeShip(new Minesweeper(), 2, 'B', false, false));
        assertFalse(test.placeShip(new Minesweeper(), 1, 'A', false, false));
        assertEquals(test.placeShip( new Submarine(true), 5, 'E', false, true), true);
    }

    @Test
    public void testSonarAttack() {
        Game test = new Game();

        assertTrue(test.sonar(6, 'E'));
    }


}
