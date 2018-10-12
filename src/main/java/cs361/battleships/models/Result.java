package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.PrimitiveIterator;

public class Result {

	//@JsonIgnoreProperties(ignoreUnknown = true)
	//Private
	@JsonProperty private Square location;
	@JsonProperty private Ship ship;
	private AtackStatus currentStatus;

	//Constructors

	public Result(){

	}

	public Result(Square square) {

		location = new Square(square.getRow(), square.getColumn(), false);
		ship = new Ship("None", 0);
		currentStatus = AtackStatus.INVALID;
	}

	public Result(Square square, Ship ship) {
		location = square;//new Square(square.getRow(), square.getColumn(), true);
		this.ship = ship;
		currentStatus = AtackStatus.INVALID;
	}

	//Public
	public AtackStatus getStatus(int shipCount) {
		//checks if the spot has been hit and is occupied if so it continues
		if(location.checkHit()){
			int check_sunk = 0;//keeps track of weather or not the ship sinks
			//goes through the ship locations to see which one was hit
			for(int i = 0; i < ship.getLength(); i++){
				//finds which part of the ship was hit
				if(location.compareLocation().equals(ship.getOccupiedSquares().get(i).compareLocation())){
					ship.getOccupiedSquares().get(i).checkHit();

				}
				//checks which spots have been hits to see if the ship sinks
				if(ship.getOccupiedSquares().get(i).getHit() > 0){
					check_sunk++;
				}
			}
			//to see if the ship sunk or was simply hit
			if(check_sunk == ship.getLength()){
					currentStatus = AtackStatus.SUNK;
					return currentStatus;
			}else {
				currentStatus = AtackStatus.HIT;
				return currentStatus;
			}
		}
		//checks to see if it is a miss or the spot has been hit twice
		if(location.checkValid()) {
			currentStatus = AtackStatus.MISS;
			return currentStatus;
		}
		currentStatus = AtackStatus.INVALID;
		return currentStatus;
	}

	public AtackStatus getResult(){
		return currentStatus;
	}

	public void setResult(AtackStatus status){
		currentStatus = status;
	}

	public Ship getShip() {
		return ship;
	}

	public void setShip(Ship ship) {
		this.ship = ship;
		location.setOccupied(true);
	}

	public Square getLocation() {
		return location;
	}

	public void setLocation(Square square) {
		location = square;
	}
}
