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
    public boolean placeShip(Ship ship, int x, char y, boolean isVertical, boolean submerged) {
        boolean successful = playersBoard.placeShip(ship, x, y, isVertical, submerged);
        if (!successful)
            return false;

        boolean opponentPlacedSuccessfully;
        do {
            // AI places random ships, so it might try and place overlapping ships
            // let it try until it gets it right
            if (ship.kind.equals("SUBMARINE")){
                opponentPlacedSuccessfully = opponentsBoard.placeShip(ship, randRow(), randCol(), randVertical(), randSubmerged());
            }else {
                opponentPlacedSuccessfully = opponentsBoard.placeShip(ship, randRow(), randCol(), randVertical(), false);
            }
        } while (!opponentPlacedSuccessfully);

        return true;
    }

    /*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
    public boolean attack(int x, char  y, boolean spaceLaser) {
        Result playerAttack = new Result();
        playerAttack.setResultClass(opponentsBoard.checkDoubleMiss(x, y, spaceLaser));
        if(playerAttack.getResult() != INVALID) {
            if (opponentsBoard.getSquareAt(x, y) != null && opponentsBoard.getSquareAt(x, y).isCaptainsQuarters()) {
                if (!(opponentsBoard.getShipAt(x, y).getSubmerged()) && !spaceLaser) {
                    playerAttack.setResultClass(opponentsBoard.sinkAttack(x, y, spaceLaser));
                } else if (spaceLaser) {
                    playerAttack.setResultClass(opponentsBoard.sinkAttack(x, y, spaceLaser));
                } else {
                    playerAttack.setResultClass(opponentsBoard.attack(x, y, spaceLaser));
                }
            } else {
                playerAttack.setResultClass(opponentsBoard.attack(x, y, spaceLaser));
            }
        }
        if (playerAttack.getResult() == INVALID) {
            return false;
        }

        Result opponentAttackResult = new Result();
        do {
            // AI does random attacks, so it might attack the same spot twice
            // let it try until it gets it right
            int xRand = randRow();
            char yRand = randCol();
            opponentAttackResult.setResultClass(playersBoard.checkDoubleMiss(xRand, yRand, spaceLaser));
            if(opponentAttackResult.getResult() != INVALID) {
                if (playersBoard.getSquareAt(xRand, yRand) != null && playersBoard.getSquareAt(xRand, yRand).isCaptainsQuarters()) {
                    if (!(playersBoard.getShipAt(xRand, yRand).getSubmerged()) && !spaceLaser) {
                        opponentAttackResult.setResultClass(playersBoard.sinkAttack(xRand, yRand, spaceLaser));
                    } else if (spaceLaser) {
                        opponentAttackResult.setResultClass(playersBoard.sinkAttack(xRand, yRand, spaceLaser));
                    } else {
                        opponentAttackResult.setResultClass(playersBoard.attack(xRand, yRand, spaceLaser));
                    }
                } else {
                    opponentAttackResult.setResultClass(playersBoard.attack(xRand, yRand, spaceLaser));
                }
            }

        } while(opponentAttackResult.getResult() == INVALID);

        return true;
    }

    public boolean sonar(int x, char y) {
        return opponentsBoard.sonar(x, y);
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

    private boolean randSubmerged() {
        return new Random().nextBoolean();
    }
}
