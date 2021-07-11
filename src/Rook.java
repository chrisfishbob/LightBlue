import java.util.ArrayList;

public class Rook extends Piece{
    ArrayList<Move> moves = new ArrayList<>();

    public Rook(String color, int location) {
        super(color, location, 5);

        if (color.equals("white")){
            setImage(LightBlueMain.whiteRook);
        }
        else{
            setImage(LightBlueMain.blackRook);
        }
    }

    public String toString(){
        if (getColor().equals("white")){
            return "R at " + getLocation();
        }
        else{
            return "r at " + getLocation();
        }
    }

    public String getPieceChar(){
        if (getColor().equals("white")){
            return "R";
        }
        else{
            return "r";
        }
    }

    public void generateMoves(){
        int startSquare = getLocation();
        ArrayList<Move> legalRookMoves = new ArrayList<>();

        for (int directionIndex = 0; directionIndex < 4; directionIndex ++){
            for (int i = 0; i < MoveGenerator.getNumSquaresToEdge()[startSquare][directionIndex]; i++){
                int targetSquare = startSquare + MoveGenerator.getSlidingDirectionOffsets()[directionIndex] * (i + 1);

                // If the target square is occupied by a friendly piece, stop searching for more
                // moves in this direction
                if (LightBlueMain.getBoardArray()[targetSquare] != null){
                    if (LightBlueMain.getBoardArray()[targetSquare].getColor().equals(getColor())){
                        break;
                    }
                }

                // If the target square is not occupied by friendly piece, this move must be legal
                legalRookMoves.add(new Move(startSquare, targetSquare));

                // If the target square is occupied by an enemy piece, we stop searching for more
                // moves in this direction (the capture itself was made legal in the statement above
                if (LightBlueMain.getBoardArray()[targetSquare] != null){
                    if (!LightBlueMain.getBoardArray()[targetSquare].getColor().equals(getColor())){
                        break;
                    }
                }
            }
        }

        moves = legalRookMoves;

//        if (isSelected()){
//            for (Move move : moves){
//                LightBlueMain.legalMoveSquaresForSelectedPiece.add(move.getTargetSquare());
//            }
//        }
    }

    public ArrayList<Move> getMoves(){
        return moves;
    }



}