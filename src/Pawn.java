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
        int potentialCaptureOneIndex = oneSquareForwardIndex + 1;
        int potentialCaptureTwoIndex = oneSquareForwardIndex - 1;
        int targetRankAfterCapture = oneSquareForwardIndex / 8;

        ArrayList<Move> legalPawnMoves = new ArrayList<>();

        if (isInBounds(oneSquareForwardIndex)){
            // test if the pawn can move forward one space
            if (Board.getBoard()[oneSquareForwardIndex] == null){
                legalPawnMoves.add(new Move(pawnLocation, oneSquareForwardIndex));
            }

            // test if the pawn can move forward two spaces
            if (rank == 6 && getColor().equals("white") || rank == 1 && getColor().equals("black")){
                if (Board.getBoard()[twoSquareForwardIndex] == null && Board.getBoard()[oneSquareForwardIndex] == null){
                    legalPawnMoves.add(new Move(pawnLocation, twoSquareForwardIndex, "p2"));
                }
            }

            // Checks if the pawn can capture sideways in both directions
            checkForCaptures(pawnLocation,
                    potentialCaptureOneIndex,
                    potentialCaptureTwoIndex,
                    targetRankAfterCapture,
                    legalPawnMoves);

            // Check if en passant in possible
            checkForEnPassant(pawnLocation,
                    potentialCaptureOneIndex,
                    potentialCaptureTwoIndex,
                    targetRankAfterCapture,
                    legalPawnMoves);
        }

        moves = legalPawnMoves;

        // Loop through the legal pawn moves and add them to the designated array in the
        // Board class so that legal moves can be displayed
        if (isSelected()){
            for (Move move : moves){
                Board.legalMoveSquaresForSelectedPiece.add(move.getTargetSquare());
            }
        }


    }


    private void checkForCaptures(int pawnLocation, int potentialCaptureOneIndex, int potentialCaptureTwoIndex,
                                  int targetRankAfterCapture, ArrayList<Move> legalPawnMoves) {

        // Check if the pawn can capture to the right (white perspective)
        if (potentialCaptureOneIndex / 8 == targetRankAfterCapture &&
                Board.getBoard()[potentialCaptureOneIndex] != null){
            if (!Board.getBoard()[potentialCaptureOneIndex].getColor().equals(getColor())){
                legalPawnMoves.add(new Move(pawnLocation, potentialCaptureOneIndex));
            }
        }

        // Check if the pawn can capture to the left (white perspective)
        if (potentialCaptureTwoIndex / 8 == targetRankAfterCapture &&
                Board.getBoard()[potentialCaptureTwoIndex] != null){
            if (!Board.getBoard()[potentialCaptureTwoIndex].getColor().equals(getColor())){
                legalPawnMoves.add(new Move(pawnLocation, potentialCaptureTwoIndex));
            }
        }
    }


    private void checkForEnPassant(int pawnLocation, int potentialCaptureOneIndex, int potentialCaptureTwoIndex,
                                   int targetRankAfterCapture, ArrayList<Move> legalPawnMoves) {

        if (potentialCaptureOneIndex / 8 == targetRankAfterCapture &&
                potentialCaptureOneIndex == Board.getEnPassantSquare()){
            legalPawnMoves.add(new Move(pawnLocation, potentialCaptureOneIndex, "ep"));
        }

        else if (potentialCaptureTwoIndex / 8 == targetRankAfterCapture &&
                potentialCaptureTwoIndex == Board.getEnPassantSquare()){
            legalPawnMoves.add(new Move(pawnLocation, potentialCaptureTwoIndex, "ep"));
        }
    }


    public boolean isInBounds(int index){
        return index >= 0 && index < 64;
    }

    public ArrayList<Move> getMoves() {
        return moves;
    }
}