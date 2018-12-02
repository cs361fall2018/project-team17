package cs361.battleships.models;

public class Submarine extends Ship {

    public Submarine(boolean submerged){
        super("SUBMARINE", 5, 3, submerged);
    }

    @Override
    public void place(char col, int row, boolean isVertical) {

        for (int i=0; i<size-1; i++) {
            if (isVertical) {
                occupiedSquares.add(new Square(row+i, col));
            } else {
                occupiedSquares.add(new Square(row, (char) (col + i)));
            }
        }
        if(isVertical){
            occupiedSquares.add(new Square(row+2, (char) (col+1)));
        } else {
            occupiedSquares.add(new Square(row-1, (char) (col+2)));
        }

        occupiedSquares.get(captainsQuarters).setCaptainsQuarters(true);
    }
}
