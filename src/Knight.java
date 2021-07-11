import java.util.ArrayList;

public class Knight extends Piece{
    private ArrayList<Move> moves;

    public Knight(String color, int location) {
        super(color, location, 3);

        if (color.equals("white")){
            setImage(LightBlueMain.whiteKnight);
        }
        else{
            setImage(LightBlueMain.blackKnight);
        }
    }


    public String toString(){
        if (getColor().equals("white")){
            return "N at " + getLocation();
        }
        else{
            return "n at " + getLocation();
        }
    }


    public String getPieceChar(){
        if (getColor().equals("white")){
            return "N";
        }
        else{
            return "n";
        }
    }


    public void generateMoves() {
        // Gets all the potentially legal moves for a knight in the given position on the board
        // and filter out the illegal moves
        ArrayList<Move> potentialMoves = MoveGenerator.getPotentialLegalKnightMoveMap().get(this.getLocation());
        ArrayList<Move> legalKnightMoves = new ArrayList<>();

        for (Move move : potentialMoves){
            int targetSquare = move.getTargetSquare();

            // Move is legal if the candidate target square is empty or occupied by enemy piece
            if (LightBlueMain.getBoard()[targetSquare] == null){
                legalKnightMoves.add(move);
            }
            else{
                if (!LightBlueMain.getBoard()[targetSquare].getColor().equals(this.getColor())){
                    legalKnightMoves.add(move);
                }
            }
        }

        moves = legalKnightMoves;

        if (isSelected()){
            for (Move move : moves){
                LightBlueMain.legalMoveSquaresForSelectedPiece.add(move.getTargetSquare());
            }
        }
    }

    public ArrayList<Move> getMoves() {
        return moves;
    }
}