package cs361.battleships.models;

import static java.lang.Boolean.FALSE;


public class Square {

	private int row;
	private char column;
	private boolean occupied;
	private int hit;

	public Square(int row, char column, boolean occupied){
		hit = 0;
		this.row = row;
		this.column = column;
		this.occupied = occupied;
	}

	public boolean checkHit(){
		hit++;
		if(hit > 1){
			return false;
		}
		return occupied;
	}

//	returns a concadinated version of the location as to more easily compare
	public String compareLocation() {
		String temp = Integer.toString(row) + String.valueOf(column);
		return temp;
	}

	//checks to make sure that the square has not been hit twice
	public boolean checkValid(){
		if(hit == 1){
			return true;
		}
		return false;
	}

	public int getHit(){
		return hit;
	}

//	public void set(Square square){
//		row = .;
//		column;
//		occupied;
//		hit;
//	}

	public char getColumn() {
		return column;
	}

	public void setColumn(char column) {
		this.column = column;
	}
	
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}
}
