package cs361.battleships.models;

import java.util.ArrayList;
import java.util.List;

public class Board {

    @jsonProperty private List<Ship> ships;
    @jsonProperty private List<list<Result> results;

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Board() {
        ships = new ArrayList<>();
        results = new ArrayList<List<>>();
        for (int i = 0; i < 10; i++) {
        	List<Square> row = new ArrayList<Result>();
			for (int j = 0; j < 10; j++) {
				row.add(new Result(new Square(i, j, false)));
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
        	boolean returnVar;
        	returnVar = ship.setOccupiedSquares(x, y, isVertical);
        	List<Square> temp = ship.getOccupiedSquares();
        	for (int i = 0; i < temp.size(); i++)
			{
				results.get(temp.getRow()).get(temp.getColumn()).setShip(ship);
			}
			return returnVar;
		}
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Result attack(int x, char y) {
		int ycon = y-48;
		results.get(x).get(ycon).getResult(ships.size());
		return results.get(x).get(ycon);
	}

	public List<Ship> getShips() {
		return ships;
	}

	public void setShips(List<Ship> ships) {
		//TODO implement
	}

	public List<Result> getAttacks() {
		return results;
	}

	public void setAttacks(List<Result> attacks) {
		//TODO implement
	}
}
