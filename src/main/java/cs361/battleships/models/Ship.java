package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Sets;
import com.mchange.v1.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class Ship {

	@JsonProperty private String kind;
	@JsonProperty private List<Square> occupiedSquares;
	@JsonProperty private int size;
	@JsonProperty private int captainsQuarters;

	public Ship() {
		occupiedSquares = new ArrayList<>();
	}
	

	public Ship(String kind, int size, int captainsQuarters){
		this();
		this.kind = kind;
		this.size = size;
		this.captainsQuarters = captainsQuarters;
	}

	public List<Square> getOccupiedSquares() {
		return occupiedSquares;
	}

	public void place(char col, int row, boolean isVertical) {
		for (int i=0; i<size; i++) {
			if (isVertical) {
				occupiedSquares.add(new Square(true, row+i, col));
			} else {
				occupiedSquares.add(new Square(true, row, (char) (col + i)));
			}
		}

		occupiedSquares.get(captainsQuarters).setCaptainsQuarters(true);

	}

	public boolean overlaps(Ship other) {
		Set<Square> thisSquares = Set.copyOf(getOccupiedSquares());
		Set<Square> otherSquares = Set.copyOf(other.getOccupiedSquares());
		Sets.SetView<Square> intersection = Sets.intersection(thisSquares, otherSquares);
		return intersection.size() != 0;
	}

	public boolean isAtLocation(Square location) {
		return getOccupiedSquares().stream().anyMatch(s -> s.equals(location));
	}

	public String getKind() {
		return kind;
	}

	public Result attack(int x, char y) {
		var attackedLocation = new Square(x, y);
		var square = getOccupiedSquares().stream().filter(s -> s.equals(attackedLocation)).findFirst();
		if (!square.isPresent()) {
			return new Result(attackedLocation);
		}
		var attackedSquare = square.get();
		if ((attackedSquare.getHit() == 0) && attackedSquare.isCaptainsQuarters() && !(kind.equals("MINESWEEPER"))) {
		    attackedSquare.hit();
            var result = new Result(attackedLocation);
			result.setResult(AtackStatus.CAPTAIN);
			return result;
		}else if(attackedSquare.isHit(kind)){
			var result = new Result(attackedLocation);
			result.setResult(AtackStatus.INVALID);
			return result;
		}
		attackedSquare.hit();
		var result = new Result(attackedLocation);
		if (isSunk()) {
		    result.setResult(AtackStatus.SUNK);
        }else {
 			result.setResult(AtackStatus.HIT);
		}
		return result;
	}

	public boolean moveShip(char direction){
		if(direction == 'N' || direction == 'W'){
			for(int i = 0; i < occupiedSquares.size(); i++){
				if(!occupiedSquares.get(i).move(direction)){
					return false;
				}
			}
		}else{
			for (int i = occupiedSquares.size(); i > 0; i--) {
				if (!occupiedSquares.get(i-1).move(direction)) {
					return false;
				}
			}
		}
		return true;
    }

    public Square ifMoved(char direction, int idx){
			Square temp = new Square(occupiedSquares.get(idx).getRow(), occupiedSquares.get(0).getColumn());
			temp.move(direction);
			return temp;
	}


    @JsonIgnore
	public boolean isSunk() {
		return getOccupiedSquares().stream().allMatch(s -> s.isHit(kind));
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Ship)) {
			return false;
		}
		var otherShip = (Ship) other;

		return this.kind.equals(otherShip.kind)
				&& this.size == otherShip.size
				&& this.occupiedSquares.equals(otherShip.occupiedSquares);
	}

	@Override
	public int hashCode() {
		return 33 * kind.hashCode() + 23 * size + 17 * occupiedSquares.hashCode();
	}

	@Override
	public String toString() {
		return kind + occupiedSquares.toString();
	}

	public int getCaptainsQuarters(){
		return captainsQuarters;
	}
}
