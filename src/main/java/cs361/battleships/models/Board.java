package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jdk.jshell.Snippet;

import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Board {

	@JsonProperty private List<Ship> ships;
	@JsonProperty private List<Result> attacks;
	@JsonProperty private List<Sonar> sonar;

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Board() {
		ships = new ArrayList<>();
		attacks = new ArrayList<>();
		sonar = new ArrayList<>();
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public boolean placeShip(Ship ship, int x, char y, boolean isVertical, boolean submerged) {
		if (ships.size() >= 4) {
			return false;
		}
		if (ships.stream().anyMatch(s -> s.getKind().equals(ship.getKind()))) {
			return false;
		}
		Ship placedShip;
		if (ship.getKind().equals("MINESWEEPER")){
			placedShip = new Minesweeper();
		}
		else if (ship.getKind().equals("DESTROYER")){
			placedShip = new Destroyer();
		}
		else if (ship.getKind().equals("BATTLESHIP")){
			placedShip = new Battleship();
		}
		else {
			placedShip = new Submarine(submerged);
		}
		placedShip.place(y, x, isVertical);
		int i;
		boolean blocked = false;
		if (ships.stream().anyMatch(s -> s.overlaps(placedShip)) && !(submerged)) {
			var overlap = ships.stream().filter(s -> s.overlaps(placedShip)).collect(Collectors.toList());
			for (i = 0; i < overlap.size(); i++ ){
				var overlapShip = overlap.get(i);
				if (!(overlapShip.getSubmerged())) {
					 blocked = true;
				}
			}
			if (blocked) {
				return false;
			}
		}
		if (placedShip.getOccupiedSquares().stream().anyMatch(s -> s.isOutOfBounds())) {
			return false;
		}
		ships.add(placedShip);
		return true;
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Result attack(int x, char y) {
		Result attackResult = attack(new Square(x, y));
		attacks.add(attackResult);
		return attackResult;
	}

	public Result sinkAttack(int x, char y){
		Ship tempShip = getShipAt(x, y);
		int i;
		if((getSquareAt(x, y).getHit() == 0) && !tempShip.getKind().equals("MINESWEEPER")){
		    Result attackResult = attack(new Square(x, y));
            attacks.add(attackResult);
            return attackResult;
        }

		for(i = 0; i < tempShip.getOccupiedSquares().size(); i++){
		    Result attackResult = attack(new Square(tempShip.getOccupiedSquares().get(i).getRow(), tempShip.getOccupiedSquares().get(i).getColumn()));
			attacks.add(attackResult);
			if(attackResult.getResult() == AtackStatus.SUNK || attackResult.getResult() == AtackStatus.SURRENDER){
			    return attackResult;
			}
		}
		return attack(new Square(x, y));
	}


	private Result attack(Square s) {
//		if (attacks.stream().anyMatch(r -> r.getLocation().equals(s)) && !(s.isCaptainsQuarters())){// && getShipAt(s.getRow(), s.getColumn()) != null && !s.isHit(getShipAt(s.getRow(), s.getColumn()).getKind())) {
//			var attackResult = new Result(s);
//			System.out.println(s.isCaptainsQuarters());
//			attackResult.setResult(AtackStatus.INVALID);
//			return attackResult;
//		}
		var shipsAtLocation = ships.stream().filter(ship -> ship.isAtLocation(s)).collect(Collectors.toList());
		if (shipsAtLocation.size() == 0) {
			var attackResult = new Result(s);
			return attackResult;
		}
		Ship hitShip; // = shipsAtLocation.get(0);
		if (shipsAtLocation.get(0).getKind().equals("MINESWEEPER")){
			hitShip = shipsAtLocation.get(0);
		}
		else if (shipsAtLocation.get(0).getKind().equals("DESTROYER")){
			hitShip = shipsAtLocation.get(0);
		}
		else {
			hitShip = shipsAtLocation.get(0);
		}
		var attackResult = hitShip.attack(s.getRow(), s.getColumn());
		if (attackResult.getResult() == AtackStatus.SUNK) {
			if (ships.stream().allMatch(ship -> ship.isSunk())) {
				attackResult.setResult(AtackStatus.SURRENDER);
			}
		}
		return attackResult;
	}

	public boolean sonar(int x, char y) {
		Square s = new Square(x, y);
		if(s.isOutOfBounds()) {
			return false;
		} else {
			checkSonarShip(s, true);
		}
		for(int j = 0; j < 4; j++) {
			for (int i = 1; i < 3; i++) {
				switch (j) {
					case 0:
						s = new Square( x + i, y);
						break;
					case 1:
						s = new Square(x - i, y);
						break;
					case 2:
						s = new Square(x, (char)(y + (char)i));
						break;
					case 3:
						s = new Square(x, (char)(y - (char)i));
						break;
					default:
						s = new Square(x, y);
				}
				checkSonarShip(s, false);
			}
		}
		s = new Square(x+1, (char)(y + (char)1));
		checkSonarShip(s, false);
		s = new Square(x+1, (char)(y - (char)1));
		checkSonarShip(s, false);
		s = new Square(x-1, (char)(y + (char)1));
		checkSonarShip(s, false);
		s = new Square(x-1, (char)(y - (char)1));
		checkSonarShip(s, false);

		return true;
	}

	private void checkSonarShip(Square s, boolean center) {
		if (!s.isOutOfBounds()) {
			var shipsAtLocation = ships.stream().filter(ship -> ship.isAtLocation(s)).collect(Collectors.toList());
			Sonar ping = new Sonar(s, center);
			if (shipsAtLocation.size() != 0) {
				sonar.add(ping);
			} else {
				ping.setResult(SonarStatus.HIDDEN);
				sonar.add(ping);
			}
		}
	}

	List<Ship> getShips() {
		return ships;
	}

	Square getSquareAt(int x, char y) {
		for(int i = 0; i < ships.size(); i++){
			for(int j = 0; j < ships.get(i).getOccupiedSquares().size(); j++){
				if(ships.get(i).getOccupiedSquares().get(j).getRow() == x && ships.get(i).getOccupiedSquares().get(j).getColumn() == y){
					return ships.get(i).getOccupiedSquares().get(j);
				}
			}
		}
		return null;
	}

	Ship getShipAt(int x, char y) {
		for(int i = 0; i < ships.size(); i++){
			for(int j = 0; j < ships.get(i).getOccupiedSquares().size(); j++){
				if(ships.get(i).getOccupiedSquares().get(j).getRow() == x && ships.get(i).getOccupiedSquares().get(j).getColumn() == y){
					return ships.get(i);
				}
			}
		}
		return null;
	}

	List<Sonar> getSonar() {return sonar;}
}
