import java.util.ArrayList;

public class Queen extends Piece{
    ArrayList<Move> moves;

    public Queen(String color, int location) {
        super(color, location, 9);

        if (color.equals("white")){
            setImage(Board.whiteQueen);
        }
        else{
            setImage(Board.blackQueen);
        }
    }

    public String toString(){
        if (getColor().equals("white")){
            return "Q at " + getLocation();
        }
        else{
            return "q at " + getLocation();
        }
    }

    public String getPieceChar(){
        if (getColor().equals("white")){
            return "Q";
        }
        else{
            return "q";
        }
    }

    public void generateMoves(){
        int startSquare = getLocation();
        ArrayList<Move> legalQueenMoves = new ArrayList<>();

        for (int directionIndex = 0; directionIndex < 8; directionIndex ++){
            for (int i = 0; i < MoveGenerator.getNumSquaresToEdge()[startSquare][directionIndex]; i++){
                int targetSquare = startSquare + MoveGenerator.getSlidingDirectionOffsets()[directionIndex] * (i + 1);

                // If the target square is occupied by a friendly piece, stop searching for more
                // moves in this direction
                if (Board.getBoard()[targetSquare] != null){
                    if (Board.getBoard()[targetSquare].getColor().equals(getColor())){
                        break;
                    }
                }

                // If the target square is not occupied by friendly piece, this move must be legal
                legalQueenMoves.add(new Move(startSquare, targetSquare));

                // If the target square is occupied by an enemy piece, we stop searching for more
                // moves in this direction (the capture itself was made legal in the statement above
                if (Board.getBoard()[targetSquare] != null){
                    if (!Board.getBoard()[targetSquare].getColor().equals(getColor())){
                        break;
                    }
                }
            }
        }

        moves = legalQueenMoves;

        if (isSelected()){
            for (Move move : moves){
                Board.legalMoveSquaresForSelectedPiece.add(move.getTargetSquare());
            }
        }
    }

    public ArrayList<Move> getMoves(){
        return moves;
    }
}
