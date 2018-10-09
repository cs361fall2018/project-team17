package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Ship {

	@JsonProperty private List<Square> occupiedSquares;
	private String kind;
	private int length;

	public Ship(String kind, int length) {
		this.kind = kind;
		this.length = length;
		occupiedSquares = new ArrayList<Square>(length);
	}

	public String getKind(){
		return kind;
	}

	public List<Square> getOccupiedSquares() {
		return occupiedSquares;
	}

	public void setOccupiedSquares(int x, char y, boolean isVerticle){
		if (isVerticle == true){
			Square newSquare;
			int row = x;
			for (int i = 0; i < length; i++){
				newSquare = new Square(row, y);
				occupiedSquares.add(i,newSquare);
				row++;
			}
		}
		else {
			char col = y;
			Square newSquare;
			for (int i = 0; i < length; i++){
				newSquare = new Square(x, col);
				occupiedSquares.add(i,newSquare);
			col++;
			}

		}




	}
}
