package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static cs361.battleships.models.AtackStatus.*;

public class Game {

    @JsonProperty private Board playersBoard = new Board();
    @JsonProperty private Board opponentsBoard = new Board();

    /*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
    public boolean placeShip(Ship ship, int x, char y, boolean isVertical) {
        boolean successful = playersBoard.placeShip(ship, x, y, isVertical);
        if (!successful)
            return false;

        boolean opponentPlacedSuccessfully;
        do {
            // AI places random ships, so it might try and place overlapping ships
            // let it try until it gets it right
            opponentPlacedSuccessfully = opponentsBoard.placeShip(ship, randRow(), randCol(), randVertical());
        } while (!opponentPlacedSuccessfully);

        return true;
    }

    /*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
    public boolean attack(int x, char  y) {
//        Result playerAttack = opponentsBoard.attack(x, y);
//        if (playerAttack.getResult() == AtackStatus.INVALID) {
//            return false;
//        }
//
//        Result opponentAttackResult;
//        do {
//            // AI does random attacks, so it might attack the same spot twice
//            // let it try until it gets it right
//            opponentAttackResult = playersBoard.attack(randRow(), randCol());
//        } while(opponentAttackResult.getResult() != AtackStatus.INVALID);
//
        return true;
    }

    /*
     * Description: create a random character from A-J by using the Random class and using .nextInt(max - min + 1) + min;
     * return: random integer from 1-10
     */
    private char randCol() {
        Random random = new Random();
        return (char)(random.nextInt(10) + 65);
    }

    /*
     * Description: create a random integer from 1-10 by using the Random class and using .nextInt(max - min + 1) + min;
     * return: random integer from 1-10
     */
    private int randRow() {
        Random random = new Random();
        return random.nextInt(10) + 1;
    }

    /*
     * Description: create a random boolean tat is true or false by using the Random class and using .nextBoolean();
     * return: random boolean that is true or false
     */
    private boolean randVertical() {
        Random random = new Random();
        return random.nextBoolean();
    }
}
