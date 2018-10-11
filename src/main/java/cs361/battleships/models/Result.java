package cs361.battleships.models;

public class Result {

	//Private
	private Square location;
	private Ship CurrentShip;
	private int ShipCount;

	//Constructors
	Result(Square square) {
		location = square;
		currentShip = new Ship(null);
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
			for(int i = 0; i < currentShip.getLength(); i++){
				Square shipLocation = currentShip.getOccupiedSquares();
				if(location.compareLocation() = shipLocation.compareLocation()){
					if(!shipLocation.checkHit()){
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
			return AtackStatus.MISS;
		}
		return null;
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
		return return location;
	}

	public void setLocation(Square square) {
		location = square;
	}
}
