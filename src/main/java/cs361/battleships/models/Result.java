package cs361.battleships.models;

import java.util.List;

public class Result {

	//Private
	private Square location;
	private Ship currentShip;

	//Constructors
	Result(Square square) {

		location = new Square(square.getRow(), square.getColumn(), false);
		currentShip = new Ship("None", 0);
	}

	Result(Square square, Ship ship) {
		location = square;//new Square(square.getRow(), square.getColumn(), true);
		currentShip = ship;
	}

	//Public
	public AtackStatus getResult(int shipCount) {
		//checks if the spot has been hit and is occupied if so it continues
		if(location.checkHit()){
			int check_sunk = 0;//keeps track of weather or not the ship sinks
			//goes through the ship locations to see which one was hit
			for(int i = 0; i < currentShip.getLength(); i++){
				//checks the current location with the locations of the ship
				if(location.compareLocation() == currentShip.getOccupiedSquares().get(i).compareLocation()){
					//checks which spots have been hits to see if the ship sinks
					if(!currentShip.getOccupiedSquares().get(i).checkHit()){
						check_sunk++;
					}
				}
			}
			//to see if the ship sunk or was simply hit
			if(check_sunk == currentShip.getLength()){
				shipCount--;//drops the amount of ships this actual count will
							// have to be manipulated outside this function this
							//is just to keep track if the game is over or not
				if(shipCount == 0){
					return AtackStatus.SURRENDER; // game over
				}else {
					return AtackStatus.SUNK;
				}
			}else {
				return AtackStatus.HIT;
			}
		}
		//checks to see if it is a miss or the spot has been hit twice
		if(location.checkValid()) {
			return AtackStatus.MISS;
		}
		return AtackStatus.INVALID;
	}

	public Ship getShip() {
		return currentShip;
	}

	public void setShip(Ship ship) {
		currentShip = ship;
	}

	public Square getLocation() {
		return location;
	}

	public void setLocation(Square square) {
		location = square;
	}
}
