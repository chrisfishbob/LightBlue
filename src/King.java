import java.util.ArrayList;

public class King extends Piece{
    ArrayList<Move> moves = new ArrayList<>();

    public King(String color, int location) {
        super(color, location, Double.POSITIVE_INFINITY);

        if (color.equals("white")){
            setImage(LightBlueMain.whiteKing);
        }
        else{
            setImage(LightBlueMain.blackKing);
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
                if (LightBlueMain.getBoard()[targetSquare] == null){
                    legalKingMoves.add(new Move(startSquare, targetSquare));
                }

                // If the target square is occupied by an enemy piece, we stop searching for more
                // moves in this direction (the capture itself was made legal in the statement above
                if (LightBlueMain.getBoard()[targetSquare] != null){
                    if (!LightBlueMain.getBoard()[targetSquare].getColor().equals(getColor())){
                        legalKingMoves.add(new Move(startSquare, targetSquare));
                    }
                }
            }


            
        }

        moves = legalKingMoves;

        if (isSelected()){
            for (Move move : moves){
                LightBlueMain.legalMoveSquaresForSelectedPiece.add(move.getTargetSquare());
            }
        }
    }

    public ArrayList<Move> getMoves(){
        return moves;
    }
}
