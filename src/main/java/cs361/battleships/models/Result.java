package cs361.battleships.models;
package cs361.battleship.models.Square

public class Result {

	//Private
	private Square location; //row colm occupied hit
 	private Ship currentShip;
	private int shipCount;

	//Constructors
	Result(Square square) {
		location = square;
		currentShip = new Ship(NULL);
		shipCount = 3;
	}
	Result(Square square, Ship ship){
		System.out.println("Constructor Called");
		location = square;
		currentShip = ship;
		shipCount = 3;
	}

	//public
	public AtackStatus getResult() {
		location.setHit(1);
		if ( location.getOccupied() ) {
			int check_sunk = 0;
			for(int i = 0; i < currentShip.getLength(); i++){
				Square shipLocation = currentShip.getOccupiedSquares();
				if(location.getColumn() == shipLocation[i].getColm() && location.getRow() == shipLocation[i].getRow()){
					if(shipLocation.getOccupied() == 1){
						check_sunk++;
					}
				}
			}
			if(check_sunk == currentShip.getLength()){
				System.out.println("Sunk Boi");
				shipCount--;
				if(shipCount == 0){
					return AtackStatus.SURRENDER;
				}else {
					return AtackStatus.SUNK;
				}
			}else{
				return AtackStatus.HIT;
			}
		}
		return AtackStatus.MISS;
	}

	public void setResult(AtackStatus result) {
//		currentStatus = result;
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
