import java.util.ArrayList;

public class Pawn extends Piece{
    ArrayList<Move> moves;

    public Pawn(String color, int location) {
        super(color, location, 1);

        if (color.equals("white")){
            setImage(LightBlueMain.whitePawn);
        }
        else{
            setImage(LightBlueMain.blackPawn);
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
            if (LightBlueMain.getBoardArray()[oneSquareForwardIndex] == null){
                checkPromotionMoves(pawnLocation, oneSquareForwardIndex, legalPawnMoves);


            }

            // test if the pawn can move forward two spaces
            if (rank == 6 && getColor().equals("white") || rank == 1 && getColor().equals("black")){
                if (LightBlueMain.getBoardArray()[twoSquareForwardIndex] == null && LightBlueMain.getBoardArray()[oneSquareForwardIndex] == null){
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
//        if (isSelected()){
//            for (Move move : moves){
//                LightBlueMain.legalMoveSquaresForSelectedPiece.add(move.getTargetSquare());
//            }
//        }


    }


    private void checkForCaptures(int pawnLocation, int potentialCaptureOneIndex, int potentialCaptureTwoIndex,
                                  int targetRankAfterCapture, ArrayList<Move> legalPawnMoves) {

        // Check if the pawn can capture to the right (white perspective)
        checkCaptureInOneDirection(pawnLocation, potentialCaptureOneIndex, targetRankAfterCapture, legalPawnMoves);

        // Check if the pawn can capture to the left (white perspective)
        checkCaptureInOneDirection(pawnLocation, potentialCaptureTwoIndex, targetRankAfterCapture, legalPawnMoves);
    }


    private void checkCaptureInOneDirection(int pawnLocation, int potentialCaptureIndex, int targetRankAfterCapture,
                                            ArrayList<Move> legalPawnMoves) {

        if (potentialCaptureIndex / 8 == targetRankAfterCapture &&
                LightBlueMain.getBoardArray()[potentialCaptureIndex] != null){
            if (!LightBlueMain.getBoardArray()[potentialCaptureIndex].getColor().equals(getColor())){
                checkPromotionMoves(pawnLocation, potentialCaptureIndex, legalPawnMoves);
            }
        }
    }


    private void checkPromotionMoves(int pawnLocation, int potentialCaptureIndex, ArrayList<Move> legalPawnMoves) {
        // After capturing, the pawn ends up on a promotion square, add all the promotion possibilities
        if (isPromotionSquare(potentialCaptureIndex)){
            legalPawnMoves.add(new Move(pawnLocation, potentialCaptureIndex, "queen"));
            legalPawnMoves.add(new Move(pawnLocation, potentialCaptureIndex, "rook"));
            legalPawnMoves.add(new Move(pawnLocation, potentialCaptureIndex, "knight"));
            legalPawnMoves.add(new Move(pawnLocation, potentialCaptureIndex, "bishop"));

        }
        // If not on a promotion square just add the capture, it's legality is already determined by
        // the checkCaptureInOneDirectionMethod
        else{
            legalPawnMoves.add(new Move(pawnLocation, potentialCaptureIndex));
        }
    }


    private void checkForEnPassant(int pawnLocation, int potentialCaptureOneIndex, int potentialCaptureTwoIndex,
                                   int targetRankAfterCapture, ArrayList<Move> legalPawnMoves) {

        if (potentialCaptureOneIndex / 8 == targetRankAfterCapture &&
                potentialCaptureOneIndex == LightBlueMain.getEnPassantSquare()){
            legalPawnMoves.add(new Move(pawnLocation, potentialCaptureOneIndex, "ep"));
        }

        else if (potentialCaptureTwoIndex / 8 == targetRankAfterCapture &&
                potentialCaptureTwoIndex == LightBlueMain.getEnPassantSquare()){
            legalPawnMoves.add(new Move(pawnLocation, potentialCaptureTwoIndex, "ep"));
        }
    }


    public boolean isInBounds(int index){
        return index >= 0 && index < 64;
    }

    public boolean isPromotionSquare(int index){
        return index / 8 == 0 || index / 8 == 7;
    }

    public ArrayList<Move> getMoves() {
        return moves;
    }
}