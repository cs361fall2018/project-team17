package cs361.battleships.models;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class GameTest {

    @Test
    public void GameTest() {
        Game g1 = new Game();
        Ship cruiser = new Cruiser();
        assertTrue(g1.placeShip(cruiser, 5, 'A', true));

    }
}
