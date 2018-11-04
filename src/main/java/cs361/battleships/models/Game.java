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

//    } else if(result.getLocation().getRow() == occupiedSquares.get(captainsQuarters).getRow() && result.getLocation().getColumn() == occupiedSquares.get(captainsQuarters).getColumn()) {
//        System.out.println("MADE IT!!!!!!!!!!!!!!!!!!!!!!!!");
//        for(int i = 0; i < size; i++){
//            var otherLocation = new Square(occupiedSquares.get(i).getRow(), occupiedSquares.get(i).getColumn());
////				occupiedSquares.get(i).getRow();
//            var otherResult = new Result(otherLocation);
//            otherResult.setShip(this);
//            otherResult.setResult(AtackStatus.HIT);
//        }
//        result.setResult(AtackStatus.SUNK);
        System.out.println(opponentsBoard.getSquareAt(x, y));
        Result playerAttack = new Result();
        if(opponentsBoard.getSquareAt(x, y) != null && opponentsBoard.getSquareAt(x, y).isCaptainsQuarters()){
//            for(int i = 0; i < opponentsBoard.getShips().size(); i++){
                playerAttack.setResultClass(opponentsBoard.sinkAttack(x, y));
//            }
        }else {
            playerAttack.setResultClass(opponentsBoard.attack(x, y));
        }
        if (playerAttack.getResult() == INVALID) {
            return false;
        }

        Result opponentAttackResult;
        do {
            // AI does random attacks, so it might attack the same spot twice
            // let it try until it gets it right
            opponentAttackResult = playersBoard.attack(randRow(), randCol());
        } while(opponentAttackResult.getResult() == INVALID);

        return true;
    }

    private char randCol() {
        int random = new Random().nextInt(10) + 1;
        return (char) ('A' + random);
    }

    private int randRow() {
        return  new Random().nextInt(10) + 2;
    }

    private boolean randVertical() {
        return new Random().nextBoolean();
    }
}
