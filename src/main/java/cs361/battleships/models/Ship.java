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

	@JsonProperty protected String kind;
	@JsonProperty protected List<Square> occupiedSquares;
	@JsonProperty protected int size;
	@JsonProperty protected int captainsQuarters;
	@JsonProperty protected boolean submerged;

	public Ship() {
		occupiedSquares = new ArrayList<>();
	}
	

	public Ship(String kind, int size, int captainsQuarters, boolean submerged){
		this();
		this.kind = kind;
		this.size = size;
		this.captainsQuarters = captainsQuarters;
		this.submerged = submerged;
	}

	public List<Square> getOccupiedSquares() {
		return occupiedSquares;
	}

	public void place(char col, int row, boolean isVertical) {
		for (int i=0; i<size; i++) {
			if (isVertical) {
				occupiedSquares.add(new Square(row+i, col));
			} else {
				occupiedSquares.add(new Square(row, (char) (col + i)));
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

	public Result attack(int x, char y, boolean spaceLaser) {
		var attackedLocation = new Square(x, y);
		var square = getOccupiedSquares().stream().filter(s -> s.equals(attackedLocation)).findFirst();
		if (!square.isPresent()) {
			return new Result(attackedLocation);
		}
		var attackedSquare = square.get();
		if ((attackedSquare.getHit() == 0) && attackedSquare.isCaptainsQuarters() && !(kind.equals("MINESWEEPER"))) {
		    attackedSquare.hit();
            var result = new Result(attackedLocation);
            if(spaceLaser) {
                result.setResult(AtackStatus.CAPTAINLASER);
            } else {
                result.setResult(AtackStatus.CAPTAIN);
            }
			return result;
		}else if(attackedSquare.isHit(kind)){
			var result = new Result(attackedLocation);
			result.setResult(AtackStatus.INVALID);
			return result;
		}
		attackedSquare.hit();
		var result = new Result(attackedLocation);
		if (isSunk()) {
		    if(spaceLaser) {
                result.setResult(AtackStatus.SUNKLASER);
            } else {
                result.setResult(AtackStatus.SUNK);
            }
        }else {
			if(spaceLaser) {
				result.setResult(AtackStatus.HITLASER);
			} else {
				result.setResult(AtackStatus.HIT);
			}
		}
		return result;
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

	public boolean getSubmerged(){
		return submerged;
	}
}
