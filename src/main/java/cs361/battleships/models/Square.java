package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("unused")
public class Square {

	@JsonProperty private int row;
	@JsonProperty private char column;
	@JsonProperty private int hit = 0;
	@JsonProperty private boolean captainsQuarters = false;

	public Square() {}

	public Square(int row, char column) {
		this.row = row;
		this.column = column;
	}

	public Square(int row, char column, boolean captainsQuarters){
		this.row = row;
		this.column = column;
		this.captainsQuarters = captainsQuarters;
	}

	public char getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}


	@Override
	public boolean equals(Object other) {
		if (other instanceof Square) {
			return ((Square) other).row == this.row && ((Square) other).column == this.column;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return 31 * row + column;
	}

	@JsonIgnore
	public boolean isOutOfBounds() {
		return row > 11 || row < 2 || column > 'K' || column < 'B';
	}

	public boolean isHit(String kind) {
		if((captainsQuarters && hit == 2)){
			return true;
		}else if((!captainsQuarters && hit == 1) || (kind.equals("MINESWEEPER") && hit == 1)){
			return true;
		}else{
			return false;
		}
	}

	public int getHit() {
	    return hit;
    }

	public void hit() {
		hit = hit + 1;
	}

	@Override
	public String toString() {
		return "(" + row + ", " + column + ')';
	}

	public void setCaptainsQuarters(boolean captainsQuarters){
		this.captainsQuarters = captainsQuarters;
	}

	public boolean isCaptainsQuarters() {
		return captainsQuarters;
	}

    public void move(char direction){
		if(direction == 'N'){
            if(row > 2){
                row--;
            }
		}else if(direction == 'E'){
            if(column < 'J'){
                column++;
            }
		}else if(direction == 'S'){
            if(row < 10){
                row++;
            }
		}else if(direction == 'W'){
            if(column > 'A'){
                column--;
            }
		}
    }
}
