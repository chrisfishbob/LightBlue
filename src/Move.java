public class Move {
    private final int startSquare;
    private final int targetSquare;

    public Move(int startSquare, int targetSquare){
        this.startSquare = startSquare;
        this.targetSquare = targetSquare;
    }

    public int getStartSquare() {
        return startSquare;
    }

    public int getTargetSquare(){
        return targetSquare;
    }
}
