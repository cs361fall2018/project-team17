package controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs361.battleships.models.Game;

public class PlacementGameAction {

    @JsonProperty private Game game;
    @JsonProperty private String shipType;
    @JsonProperty private int x;
    @JsonProperty private char y;
    @JsonProperty private boolean isVertical;
    @JsonProperty private boolean submerged;

    public Game getGame() {
        return game;
    }

    public int getActionRow() {
        return x;
    }

    public char getActionColumn() {
        return y;
    }

    public String getShipType() {
        return shipType;
    }

    public boolean isVertical() {
        return isVertical;
    }

    public boolean getSubmerged() {
        return submerged;
    }
}
