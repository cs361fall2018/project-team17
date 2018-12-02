package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

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
	public Result attack(int x, char y, boolean spaceLaser) {
		Ship hitShip1 = null;
		Ship hitShip2 = null;
		Square s = new Square(x, y);

		var shipsAtLocation = ships.stream().filter(ship -> ship.isAtLocation(s)).collect(Collectors.toList());
		if (shipsAtLocation.size() == 0) {
			var attackResult = new Result(s);
			if(spaceLaser && attackResult.getResult() == AtackStatus.MISS) { attackResult.setResult(AtackStatus.MISSLASER); }
			attacks.add(attackResult);
			return attackResult;
		}
		if(shipsAtLocation.size() == 2) {
			if(shipsAtLocation.get(0).getSubmerged()) {
				hitShip2 = shipsAtLocation.get(0);
				hitShip1 = shipsAtLocation.get(1);
			} else {
				hitShip1 = shipsAtLocation.get(0);
				hitShip2 = shipsAtLocation.get(1);
			}
		} else if (shipsAtLocation.get(0).getSubmerged() && !spaceLaser) {
			var attackResult = new Result(s);
			attacks.add(attackResult);
			return attackResult;
		}
		else { hitShip1 = shipsAtLocation.get(0); }

		if(!spaceLaser) {
			return attackOrSink(s, hitShip1, spaceLaser);
		} else {
			if(hitShip2 != null) {
				Result attk1 = attackOrSink(s, hitShip1, spaceLaser);
				Result attk2 = attackOrSink(s, hitShip2, spaceLaser);
				if(attk1.getResult() == AtackStatus.INVALID && attk2.getResult() != AtackStatus.INVALID) {
					return attk2;
				} else {
					return attk1;
				}
			} else {
				return attackOrSink(s, hitShip1, spaceLaser);
			}
		}
	}

	private Result attackOrSink(Square s, Ship ship, boolean spaceLaser) {
		var square = ship.getOccupiedSquares().stream().filter(sq -> sq.equals(s)).findFirst();
		if(square.get() != null && square.get().isCaptainsQuarters()) {
			return sinkAttack(s.getRow(), s.getColumn(), ship, spaceLaser);
		} else {
			Result attackResult = attack(s, ship, spaceLaser);
			attacks.add(attackResult);
			return attackResult;
		}
	}

	private Result sinkAttack(int x, char y, Ship ship, boolean spaceLaser){
		int i;
		if((ship.getOccupiedSquares().get(ship.captainsQuarters).getHit() == 0) && !ship.getKind().equals("MINESWEEPER")){
		    Result attackResult = attack(new Square(x, y), ship, spaceLaser);
            attacks.add(attackResult);
            return attackResult;
        }

		for(i = 0; i < ship.getOccupiedSquares().size(); i++){
		    Result attackResult = attack(new Square(ship.getOccupiedSquares().get(i).getRow(), ship.getOccupiedSquares().get(i).getColumn()), ship, spaceLaser);
		    if(attackResult.getResult() != AtackStatus.INVALID)
				attacks.add(attackResult);
			if(attackResult.getResult() == AtackStatus.SUNK || attackResult.getResult() == AtackStatus.SURRENDER || attackResult.getResult() == AtackStatus.SUNKLASER){
			    return attackResult;
			}
		}
		return attack(new Square(x, y), ship, spaceLaser);
	}

	public Result checkDoubleMiss(int x, char y, boolean spaceLaser) {
		Square s = new Square(x, y);
		var missAttakcs = attacks.stream().filter(r -> r.getResult().equals(AtackStatus.MISS)).collect(Collectors.toList());
		if(!spaceLaser) {
			for (int i = 0; i < missAttakcs.size(); i++) {
				if (missAttakcs.get(i).getLocation().getColumn() == s.getColumn() && missAttakcs.get(i).getLocation().getRow() == s.getRow()) {
					return new Result();
				}
			}
		} else {
			missAttakcs = attacks.stream().filter(r -> r.getResult().equals(AtackStatus.MISSLASER)).collect(Collectors.toList());
			for (int i = 0; i < missAttakcs.size(); i++) {
				if (missAttakcs.get(i).getLocation().getColumn() == s.getColumn() && missAttakcs.get(i).getLocation().getRow() == s.getRow()) {
					return new Result();
				}
			}
		}return new Result(s);
	}


	private Result attack(Square s, Ship ship, boolean spaceLaser) {

		var attackResult = ship.attack(s.getRow(), s.getColumn(), spaceLaser);
		if (attackResult.getResult() == AtackStatus.SUNK || attackResult.getResult() == AtackStatus.SUNKLASER) {
			if (ships.stream().allMatch(shipss -> shipss.isSunk())) {
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

	void moveFleet(char direction){
		int count = 0;
        boolean result = true;
		for(int i = 0; i < ships.size(); i++){
			count = 0;
            boolean thisShip = false;
            for(int j = 0; j < ships.get(i).getOccupiedSquares().size(); j++) {
                if (overLaps(ships.get(i).ifMoved(direction, j), ships.get(i))) {
                    count++;
                }
            }

			if(!ships.get(i).moveShip(direction)){
			    result = false;
			    thisShip = true;
            }
			if(count > 1 && !result && !thisShip){//ships.get(i).getOccupiedSquares().size()) {
				if(direction == 'N'){
					ships.get(i).moveShip('S');
				}else if(direction == 'E'){
					ships.get(i).moveShip('W');
				}else if(direction == 'S'){
					ships.get(i).moveShip('N');
				}else{
					ships.get(i).moveShip('E');
				}
			}
		}


	}

	public boolean overLaps(Square square, Ship ship) {
		int count = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < ships.get(i).getOccupiedSquares().size(); j++) {
				if (ships.get(i).getOccupiedSquares().get(j).equals(square)) {
					count++;
				}
			}
		}
		if(count > 0){//ship.getOccupiedSquares().size()){
			return true;
		}
		return false;
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
