package cs361.battleships.models;

@SuppressWarnings("unused")
public class Square {

	private int row;
	private char column;
	private boolean occupied;
	private boolean hit;

	public Square(int row, char column, boolean occupied){
		hit = 0;
		this.row = r;
		this.column = c;
		this.occupied = o;
	}

	public squareHit(){
		hit = 1;
		return occupied;
	}

	public compare() {
		return row + column;
	}

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
