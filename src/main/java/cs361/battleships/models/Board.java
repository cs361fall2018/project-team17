package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Board {

	//Private Arrays, Ships contains the ship variables present in the Board
	//Results is a array that contains a result object for every attack on the board
	@JsonProperty private List<Ship> ships;
	@JsonProperty private List<Result> attacks;
	@JsonProperty private int counter;

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Board() {
		//Initial Declarations

		this.ships = new ArrayList<>();
		this.attacks = new ArrayList<>();
		this.counter = ships.size();
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public boolean placeShip(Ship ship, int x, char y, boolean isVertical) {
		//Error Checking
		int totxlength = x + ship.getLength();
		int totylength = y + ship.getLength();
		if (x < 1 || x > 10)
			return false;
		else if (y > 'J' || y < 'A')
			return false;
		else if ( totxlength-1 >= 10  && isVertical)
			return false;
		else if ( totylength-1 >= 'J' && !isVertical)
			return false;
		else {
			for (int i = 0; i < ships.size(); i++) {
				for (int j = 0; j < ships.get(i).getOccupiedSquares().size(); j++) {
					if (ships.get(i).getOccupiedSquares().get(j).getRow() == x &&
							ships.get(i).getOccupiedSquares().get(j).getColumn() == y) {
						return false;
					}
				}
			}
			//Set the attacks of the ship to the coordinates passed in
			ship.setOccupiedSquares(x, y, isVertical);
			//Create a list of the occupied squares to be used to change the result array
			List<Square> temp = ship.getOccupiedSquares();
			//add the ship to the ship array
			this.ships.add(ship);
			counter++;
			return true;
		}
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Result attack(int x, char y) {
		//Convert y value to an integer
		int ycon = y-64;
		//Error checking
		if (x <= 10 && x >= 1 && ycon <= 10 && y>= 1) {
			for (int i = 0; i < ships.size(); i++)
			{
				for (int j = 0; j < ships.get(i).getOccupiedSquares().size(); j++)
				{
					if (ships.get(i).getOccupiedSquares().get(j).getRow() == x &&
							ships.get(i).getOccupiedSquares().get(j).getColumn() == y) {
						Result newAtt = new Result(new Square(x, y, true), ships.get(i));
						newAtt.getStatus(counter);
						if(newAtt.getResult() == AtackStatus.SUNK) {
							counter--;
						}
						if (counter == 0 && newAtt.getResult() == AtackStatus.SUNK)
							newAtt.setResult(AtackStatus.SURRENDER);
						attacks.add(newAtt);
						return newAtt;
					}
				}
			}
			Result newAttk = new Result(new Square(x, y, false));
			AtackStatus TMP = newAttk.getStatus(ships.size());
			if (newAttk.getResult() == AtackStatus.INVALID)
				return newAttk;
			attacks.add(newAttk);
			return newAttk;
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
	public List<Result> getAttacks() {
		return this.attacks;
	}

	//setter
	public void setAttacks(List<Result> attacks) {
		this.attacks = attacks;
	}
}