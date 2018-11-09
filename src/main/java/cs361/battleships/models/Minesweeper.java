package cs361.battleships.models;

public class Minesweeper extends Ship {

    public Minesweeper(){
        super("MINESWEEPER", 2, 0);
    }

    public Result attack(int x, char y) {
        var attackedLocation = new Square(x, y);
        var square = getOccupiedSquares().stream().filter(s -> s.equals(attackedLocation)).findFirst();
        if (!square.isPresent()) {
            return new Result(attackedLocation);
        }
        var attackedSquare = square.get();
        if(attackedSquare.isHit(this.getKind())) {
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
}