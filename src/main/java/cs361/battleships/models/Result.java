package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Result {

	@JsonProperty private AtackStatus result;
	@JsonProperty private Square location;
	@JsonProperty private Ship ship;

	@SuppressWarnings("unused")
	public Result() {
		result = AtackStatus.INVALID;
		location = null;
		ship = null;
	}

	public Result(Square location) {
		result = AtackStatus.MISS;
		this.location = location;
	}

	public Result(Square location, Ship ship){
		this.location = location;
		this.ship = ship;
	}

	public void setResultClass(Result fill){
		result = fill.getResult();
		location = fill.getLocation();
		ship = fill.getShip();
	}

	public AtackStatus getResult() {
		return result;
	}

	public void setResult(AtackStatus result) {
		this.result = result;
	}

	public Ship getShip() {
		return ship;
	}

	public void setShip(Ship ship) {
		this.ship = ship;
	}

	public Square getLocation() {
		return location;
	}
}
