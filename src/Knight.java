import java.util.ArrayList;

public class Knight extends Piece{

    public Knight(String color, int location) {
        super(color, location, 3);

        if (color.equals("white")){
            setImage(Board.whiteKnight);
        }
        else{
            setImage(Board.blackKnight);
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


    public ArrayList<Move> generateMoves() {
        // Gets all the potentially legal moves for a knight in the given position on the board
        // and filter out the illegal moves
        ArrayList<Move> potentialMoves = MoveGenerator.getPotentialLegalKnightMoveMap().get(this.getLocation());
        ArrayList<Move> legalKnightMoves = new ArrayList<>();

        for (Move move : potentialMoves){
            int targetSquare = move.getTargetSquare();

            if (Board.getBoard()[targetSquare] == null){
                Board.legalMoveSquares.add(targetSquare);
                legalKnightMoves.add(move);
            }
            else{
                if (!Board.getBoard()[targetSquare].getColor().equals(this.getColor())){
                    Board.legalMoveSquares.add(targetSquare);
                    legalKnightMoves.add(move);
                }
            }
        }

        System.out.println(legalKnightMoves.size());
        return legalKnightMoves;
    }
}