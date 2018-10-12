package cs361.battleships.models;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private List<Ship> ships;
    private List<List<Result>> results;

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Board() {
        ships = new ArrayList<>();
        results = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
        	List<Result> row = new ArrayList<>();
        	results.add(row);
			for (int j = 0; j < 10; j++) {
				row.add(new Result(new Square(i, (char)(j+48), false)));
			}
		}
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
 	public boolean placeShip(Ship ship, int x, char y, boolean isVertical) {
        if (x < 1 || x > 9)
            return false;
        else if (y > 'J' || y < 'A')
            return false;
        else if ( x + ship.getLength() > 9  && !isVertical)
            return false;
        else if ( y + ship.getLength()  > 'J' && isVertical)
            return false;
        else {
        	boolean returnVar = false;
        	ship.setOccupiedSquares(x, y, isVertical);
        	List<Square> temp = ship.getOccupiedSquares();
        	ships.add(ship);
        	for (int i = 0; i < temp.size(); i++)
			{
				results.get(temp.get(i).getRow()).get((int)(temp.get(i).getColumn())-64).setShip(ship);
				if (results.get(temp.get(i).getRow()).get((int)(temp.get(i).getColumn())-64).getShip() == ship) {
					returnVar = true;
				}
			}
			return returnVar;
		}
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Result attack(int x, char y) {
		int ycon = y-64;
		if (ships.size() == 1) {
			if (x < 10 && ycon < 10) {
				results.get(x).get(ycon).getStatus(ships.size());
				return results.get(x).get(ycon);
			}
		}
		return new Result(new Square(x, y, false));
	}

	public List<Ship> getShips() {
		return this.ships;
	}

	public void setShips(List<Ship> ships) {
		this.ships = ships;
	}

	public List<List<Result>> getAttacks() {
		return this.results;
	}

	public void setAttacks(List<List<Result>> attacks) {
		this.results = attacks;
	}
}
