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
        else {
            ship = new Battleship();
        }
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

    public Result sonar(Context context, SonarGameAction g) {
        Game game = g.getGame();
        boolean result = game.sonar(g.getActionRow(), g.getActionColumn());
        if (result) {
            return Results.json().render(game);
        } else {
            return Results.badRequest();
        }
    }

    public Result moveFleet(Context context, MoveFleetAction g){
        Game game = g.getGame();
        boolean result = game.move(g.getDirection());
        if(result) {
            return Results.json().render(game);
        }else{

            return Results.badRequest();
        }
    }
}
