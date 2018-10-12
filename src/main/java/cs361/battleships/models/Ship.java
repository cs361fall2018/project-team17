package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Ship {

	@JsonProperty private List<Square> occupiedSquares;
	private String kind;
	private int length;

	public Ship() {
		occupiedSquares = new ArrayList<>();
	}

	public Ship(String kind){
		this.kind = kind;
		if(kind.equals("MINESWEEPER"))
			this.length = 2;
		else if (kind.equals("DESTROYER"))
			this.length = 3;
		else if (kind.equals("BATTLESHIP"))
			this.length = 4;
		occupiedSquares = new ArrayList<Square>(length);
	}

	public Ship(String kind, int length) {
		this.kind = kind;
		this.length = length;
		occupiedSquares = new ArrayList<Square>(length);
	}

	public String getKind(){
		return kind;
	}

	public int getLength(){
		return length;
	}

	public List<Square> getOccupiedSquares() {
		return occupiedSquares;
	}

	public boolean setOccupiedSquares(int x, char y, boolean isVerticle){

		if (x < 1 || x > 10){
			return false;
		}
		if (y > 'J' || y < 'A'){
			return false;
		}

		if (isVerticle){
			Square newSquare;
			int row = x;
			for (int i = 0; i < length; i++){
				newSquare = new Square(row, y, true);
				this.occupiedSquares.add(i,newSquare);
				row++;
			}
			return true;
		}
		else {
			char col = y;
			Square newSquare;
			for (int i = 0; i < length; i++){
				newSquare = new Square(x, col, true);
				this.occupiedSquares.add(i,newSquare);
			col++;
			}
			return true;
		}

	}
}
