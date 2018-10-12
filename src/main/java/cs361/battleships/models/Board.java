package cs361.battleships.models;

import java.util.ArrayList;
import java.util.List;

public class Board {

	//Private Arrays, Ships contains the ship variables present in the Board
	//Results is a 2D array that contains a result object for every space on the board
    private List<Ship> ships;
    private List<List<Result>> results;

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Board() {
		//Initial Declarations
        ships = new ArrayList<>();
        results = new ArrayList<>();

        //Populating the results Array
        for (int i = 0; i < 10; i++) {
        	List<Result> row = new ArrayList<>();
        	results.add(row);
			for (int j = 0; j < 10; j++) {
				row.add(new Result(new Square(i, (char)(j+48), false)));
			}
		}
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
 	public boolean placeShip(Ship ship, int x, char y, boolean isVertical) {
 		//Error Checking
        if (x < 1 || x > 9)
            return false;
        else if (y > 'J' || y < 'A')
            return false;
        else if ( x + ship.getLength() > 9  && !isVertical)
            return false;
        else if ( y + ship.getLength()  > 'J' && isVertical)
            return false;
        else {
        	boolean returnVar = false;
        	//Set the location of the ship to the coordinates passed in
        	ship.setOccupiedSquares(x, y, isVertical);
        	//Create a list of the occupied squares to be used to change the result array
        	List<Square> temp = ship.getOccupiedSquares();
        	//add the ship to the ship array
        	ships.add(ship);
        	//Iterate through the occupied squares array and for each occupied square set the ship to the
			//passed in ship and then check if the ship var is where it was placed.
        	for (int i = 0; i < temp.size(); i++) {
				results.get(temp.get(i).getRow()).get((int)(temp.get(i).getColumn())-64).setShip(ship);
				if (results.get(temp.get(i).getRow()).get((int)(temp.get(i).getColumn())-64).getShip() == ship) {
					returnVar = true;
				} else
					return false;
			}
			//return statement
			return returnVar;
		}
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Result attack(int x, char y) {
		//Convert y value to an integer
		int ycon = y-64;
		//Error checking
		if (x < 10 && ycon < 10) {
			//get the result of an attack at the specific square
			results.get(x).get(ycon).getStatus(ships.size()-1);
			// check to see if a ship is destroyed, if it is remove it from the ships array
			if (results.get(x).get(ycon).getResult() == AtackStatus.SUNK ||
					results.get(x).get(ycon).getResult() == AtackStatus.SURRENDER)
			{
				ships.remove(results.get(x).get(ycon).getShip());
			}
			// return the results if it makes it past error checking
			return results.get(x).get(ycon);
		}
		//return a new result with an invalid attack if it fails the error checking
		return new Result(new Square(x, y, false));
	}

	//getter
	public List<Ship> getShips() {
		return this.ships;
	}

	//setter
	public void setShips(List<Ship> ships) {
		this.ships = ships;
	}

	//getter
	public List<List<Result>> getAttacks() {
		return this.results;
	}

	//setter
	public void setAttacks(List<List<Result>> attacks) {
		this.results = attacks;
	}
}
