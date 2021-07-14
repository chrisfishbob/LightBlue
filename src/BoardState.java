public class BoardState {
    private Piece[] boardArray;
    private int prevPreviousMoveStartSquare;
    private int prevPreviousMoveTargetSquare;
    private int prevSelectedSquare;
    private int prevEnPassantSquare;
    private String prevColorToMove;

    public BoardState(Piece[] boardArray,
                      int prevPreviousMoveStartSquare,
                      int prevPreviousMoveTargetSquare,
                      int prevSelectedSquare,
                      int prevEnPassantSquare,
                      String prevColorToMove) {

        this.boardArray = boardArray;
        this.prevPreviousMoveStartSquare = prevPreviousMoveStartSquare;
        this.prevPreviousMoveTargetSquare = prevPreviousMoveTargetSquare;
        this.prevSelectedSquare = prevSelectedSquare;
        this.prevEnPassantSquare = prevEnPassantSquare;
        this.prevColorToMove = prevColorToMove;
    }

    public Piece[] getBoardArray() {
        return boardArray;
    }

    public int getPrevPreviousMoveStartSquare() {
        return prevPreviousMoveStartSquare;
    }

    public int getPrevPreviousMoveTargetSquare() {
        return prevPreviousMoveTargetSquare;
    }

    public int getPrevSelectedSquare() {
        return prevSelectedSquare;
    }

    public int getPrevEnPassantSquare() {
        return prevEnPassantSquare;
    }

    public String getPrevColorToMove() {
        return prevColorToMove;
    }
}
