import java.util.ArrayList;

public class Pawn extends Piece{
    ArrayList<Move> moves;

    public Pawn(String color, int location) {
        super(color, location, 1);

        if (color.equals("white")){
            setImage(Board.whitePawn);
        }
        else{
            setImage(Board.blackPawn);
        }
    }


    public String toString(){
        if (getColor().equals("white")){
            return "P at " + getLocation();
        }
        else{
            return "p at " + getLocation();
        }
    }


    public String getPieceChar(){
        if (getColor().equals("white")){
            return "P";
        }
        else{
            return "p";
        }
    }


    public void generateMoves(){
        int pieceColorMultiplier = getColor().equals("white") ? 1 : -1;
        int pawnLocation = getLocation();
        int rank = getLocation() / 8;
        int oneSquareForwardIndex = pawnLocation - 8 * pieceColorMultiplier;
        int twoSquareForwardIndex = pawnLocation - 16 * pieceColorMultiplier;
        ArrayList<Move> legalPawnMoves = new ArrayList<>();

        if (oneSquareForwardIndex >= 0 && oneSquareForwardIndex < 64){
            if (Board.getBoard()[oneSquareForwardIndex] == null){
                Board.legalMoveSquaresForSelectedPiece.add(oneSquareForwardIndex);
                legalPawnMoves.add(new Move(pawnLocation, oneSquareForwardIndex));
            }

            if (rank == 6 && getColor().equals("white") || rank == 1 && getColor().equals("black")){
                if (Board.getBoard()[twoSquareForwardIndex] == null && Board.getBoard()[oneSquareForwardIndex] == null){
                    Board.legalMoveSquaresForSelectedPiece.add(twoSquareForwardIndex);
                    legalPawnMoves.add(new Move(pawnLocation, twoSquareForwardIndex, "p2"));
                }
            }
        }

        moves = legalPawnMoves;
    }

    public ArrayList<Move> getMoves() {
        return moves;
    }
}