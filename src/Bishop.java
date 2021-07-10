import java.util.ArrayList;

public class Bishop extends Piece{
    ArrayList<Move> moves = new ArrayList<>();

    public Bishop(String color, int location) {
        super(color, location, 3.25);

        if (color.equals("white")){
            setImage(Board.whiteBishop);
        }
        else{
            setImage(Board.blackBishop);
        }
    }

    public String toString(){
        if (getColor().equals("white")){
            return "B at " + getLocation();
        }
        else{
            return "b at " + getLocation();
        }
    }

    public String getPieceChar(){
        if (getColor().equals("white")){
            return "B";
        }
        else{
            return "b";
        }
    }

    public void generateMoves(){
        int startSquare = getLocation();
        ArrayList<Move> legalBishopMoves = new ArrayList<>();

        for (int directionIndex = 4; directionIndex < 8; directionIndex ++){
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
                legalBishopMoves.add(new Move(startSquare, targetSquare));

                // If the target square is occupied by an enemy piece, we stop searching for more
                // moves in this direction (the capture itself was made legal in the statement above
                if (Board.getBoard()[targetSquare] != null){
                    if (!Board.getBoard()[targetSquare].getColor().equals(getColor())){
                        break;
                    }
                }
            }
        }

        moves = legalBishopMoves;

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