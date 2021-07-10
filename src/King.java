import java.util.ArrayList;

public class King extends Piece{
    ArrayList<Move> moves = new ArrayList<>();

    public King(String color, int location) {
        super(color, location, Double.POSITIVE_INFINITY);

        if (color.equals("white")){
            setImage(Board.whiteKing);
        }
        else{
            setImage(Board.blackKing);
        }
    }

    public String toString(){
        if (getColor().equals("white")){
            return "K at " + getLocation();
        }
        else{
            return "k at " + getLocation();
        }
    }

    public String getPieceChar(){
        if (getColor().equals("white")){
            return "K";
        }
        else{
            return "k";
        }
    }

    public void generateMoves(){
        int startSquare = getLocation();
        ArrayList<Move> legalKingMoves = new ArrayList<>();

        for (int directionIndex = 0; directionIndex < 8; directionIndex ++){

            int targetSquare = startSquare + MoveGenerator.getSlidingDirectionOffsets()[directionIndex];

            if (targetSquare >= 0 && targetSquare < 64)
            {
                // If the target square is occupied by a friendly piece, stop searching for more
                // moves in this direction
                if (Board.getBoard()[targetSquare] == null){
                    legalKingMoves.add(new Move(startSquare, targetSquare));
                }

                // If the target square is occupied by an enemy piece, we stop searching for more
                // moves in this direction (the capture itself was made legal in the statement above
                if (Board.getBoard()[targetSquare] != null){
                    if (!Board.getBoard()[targetSquare].getColor().equals(getColor())){
                        legalKingMoves.add(new Move(startSquare, targetSquare));
                    }
                }
            }


            
        }

        moves = legalKingMoves;

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
