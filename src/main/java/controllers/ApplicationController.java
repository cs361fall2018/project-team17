package controllers;

import com.google.inject.Singleton;
import cs361.battleships.models.Game;
import cs361.battleships.models.Ship;
import cs361.battleships.models.Destroyer;
import cs361.battleships.models.Minesweeper;
import cs361.battleships.models.Battleship;
import ninja.Context;
import ninja.Result;
import ninja.Results;

@Singleton
public class ApplicationController {

    public Result index() {
        return Results.html();
    }

    public Result newGame() {
        Game g = new Game();
        return Results.json().render(g);
    }

    public Result placeShip(Context context, PlacementGameAction g) {
        Game game = g.getGame();
        Ship ship;
        if (g.getShipType() == "DESTROYER"){
            ship = new Destroyer();
        }
        else if(g.getShipType() == "MINESWEEPER"){
            ship = new Minesweeper();
        }
        else{
            ship = new Battleship();
        }
        //Ship ship = new Ship(g.getShipType());
        boolean result = game.placeShip(ship, g.getActionRow(), g.getActionColumn(), g.isVertical());
        if (result) {
            return Results.json().render(game);
        } else {
            return Results.badRequest();
        }
    }

    public Result attack(Context context, AttackGameAction g) {
        Game game = g.getGame();
        boolean result = game.attack(g.getActionRow(), g.getActionColumn());
        if (result) {
            return Results.json().render(game);
        } else {
            return Results.badRequest();
        }
    }
}
