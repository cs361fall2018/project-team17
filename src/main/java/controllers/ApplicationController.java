package controllers;

import com.google.inject.Singleton;
import cs361.battleships.models.*;
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
        if (g.getShipType().equals("MINESWEEPER")){
            ship = new Minesweeper();
        }
        else if (g.getShipType().equals("DESTROYER")){
            ship = new Destroyer();
        }
        else if (g.getShipType().equals("BATTLESHIP")){
            ship = new Battleship();
        }
        else {
            ship = new Submarine(g.getSubmerged());
        }
        boolean result = game.placeShip(ship, g.getActionRow(), g.getActionColumn(), g.isVertical(), g.getSubmerged());
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

    public Result sonar(Context context, SonarGameAction g) {
        Game game = g.getGame();
        boolean result = game.sonar(g.getActionRow(), g.getActionColumn());
        if (result) {
            return Results.json().render(game);
        } else {
            return Results.badRequest();
        }
    }
}
