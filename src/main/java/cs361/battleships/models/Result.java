package cs361.battleships.models;

import java.util.List;

public class Result {

	//Private
	private Square location;
	private Ship currentShip;
	private int shipCount;

	//Constructors
	Result(Square square) {
		location = square;
		currentShip = new Ship("None");
		shipCount = 3;
	}

	Result(Square square, Ship ship) {
		location = square;
		currentShip = ship;
		shipCount = 3;
	}

	//Public
	public AtackStatus getResult() {
		if(location.checkHit()){
			int check_sunk = 0;
			List<Square> shipLocation = currentShip.getOccupiedSquares();
			for(int i = 0; i < currentShip.getLength(); i++){
				if(location.compareLocation() == shipLocation.get(i).compareLocation()){
					if(!shipLocation.get(i).checkHit()){
						check_sunk++;
					}
				}
			}
			if(check_sunk == currentShip.getLength()){
				shipCount--;
				if(shipCount == 0){
					return AtackStatus.SURRENDER;
				}else {
					return AtackStatus.SUNK;
				}
			}else {
				return AtackStatus.HIT;
			}
		}
		return AtackStatus.MISS;
//		return AtackStatus.INVALID;
	}

	public void setResult(AtackStatus result) {
		//TODO implement
	}

	public Ship getShip() {
		//TODO implement
		return currentShip;
	}

	public void setShip(Ship ship) {
		currentShip = ship;
	}

	public Square getLocation() {
		//TODO implement
		return location;
	}

	public void setLocation(Square square) {
		location = square;
	}
}
