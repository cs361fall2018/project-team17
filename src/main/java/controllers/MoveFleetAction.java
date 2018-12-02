package controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs361.battleships.models.Game;

public class MoveFleetAction {
    @JsonProperty private Game game;
    @JsonProperty private char direction;

    public Game getGame(){
        return game;
    }

    public char getDirection() {
        return direction;
    }
}
