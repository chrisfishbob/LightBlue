import java.util.ArrayList;
import java.util.HashMap;

public class MoveGenerator {
    private HashMap<Integer, ArrayList<Move>> potentialLegalKnightMoveMap;
    private static int[] SlidingDirectionOffsets = {8, - 8, -1, 1, 7, -7, 9, -9};
    private static int[][] NumSquaresToEdge = new int[64][8];
    private ArrayList<Move> legalMoves;

    public MoveGenerator(){
        preGenerateKnightMoves();
        PrecomputeMoveData();
    }
    public ArrayList<Move> generateLegalMoves(Board board, String colorToGenerate){
        board.muteBoard();
        ArrayList<Move> candidateMoves= generateAllMoves(board, colorToGenerate);
        ArrayList<Move> legalMoves = new ArrayList<>();
        int kingLocation;
        boolean isInCheck = board.isInCheck(colorToGenerate);

        for (Move candidateMove : candidateMoves){
            boolean moveIsIllegal = false;
            board.makeMove(candidateMove);
            kingLocation = board.getKingLocation(colorToGenerate);
            String opponentColor = colorToGenerate.equals("white") ? "black" : "white";
            ArrayList<Move> opponentMoves = generateAllMoves(board, opponentColor);

            for (Move opponentMove: opponentMoves){
                if (opponentMove.getTargetSquare() == kingLocation) {

                    moveIsIllegal = true;
                    break;
                }
            }

            if (!moveIsIllegal){
                legalMoves.add(candidateMove);
            }

            board.unMakeMove(candidateMove);
        }




        if (legalMoves.size() == 0){
            if (isInCheck){
                System.out.println("CHECKMATE");
            }

            else{
                System.out.println("STALEMATE");
            }
        }

        board.unMuteBoard();
        return legalMoves;
    }


    public ArrayList<Move> generateAllMoves(Board board, String colorToGenerate){
        ArrayList<Move> allMoves = new ArrayList<>();
        Piece[] boardArray = board.getBoardArray();

        for (Piece piece : board.getBoardArray()){
            if (piece != null){
                if (piece.getColor().equals(colorToGenerate)){
                    if (piece instanceof Pawn){
                        generatePawnMoves(piece, boardArray, board.getEnPassantSquare(), allMoves);
                    }
                    else if (piece instanceof Knight){
                        generateKnightMoves(piece, boardArray, allMoves);
                    }
                    else if (piece instanceof King){
                        generateKingMoves(piece, boardArray, allMoves);
                    }
                    else if (piece instanceof Queen){
                        generateQueenMoves(piece, boardArray, allMoves);
                    }
                    else if (piece instanceof Bishop){
                        generateBishopMoves(piece, boardArray, allMoves);
                    }
                    else if (piece instanceof Rook){
                        generateRookMoves(piece,boardArray, allMoves);
                    }
                }
            }
        }

        return allMoves;
    }


    public int MoveGenerationTest(Board board, int depth){
        if (depth == 0){
            return 1;
        }

        ArrayList<Move> moves = generateLegalMoves(board, board.getColorToMove());
        board.muteBoard();
        int numPos = 0;

        for (Move move : moves){
            if (depth == 1){
                board.printBoard();
            }
            board.makeMove(move);
            numPos += MoveGenerationTest(board, depth - 1);
            board.unMakeMove(move);
        }

//        board.unMuteBoard();

        return numPos;
    }



    public void PrecomputeMoveData(){
        for (int file = 0; file < 8; file ++){
            for (int rank = 0; rank < 8; rank ++){
                int numNorth = rank;
                int numSouth = 7 - rank;
                int numWest = file;
                int numEast = 7 - file;
                int numNorthWest = Math.min(numNorth, numWest);
                int numSouthEast = Math.min(numSouth, numEast);
                int numNorthEast = Math.min(numNorth, numEast);
                int numSouthWest = Math.min(numSouth, numWest);

                int squareIndex = rank * 8 + file;

                NumSquaresToEdge[squareIndex] = new int[]{
                        numSouth,
                        numNorth,
                        numWest,
                        numEast,
                        numSouthWest,
                        numNorthEast,
                        numSouthEast,
                        numNorthWest,
                        };
            }
        }
    }


    public void preGenerateKnightMoves(){
        // Pre-generates all the potential legal knight moves during set up.
        // Moves that turn out to be illegal during run time are filtered out
        // in the generateMoves method for the Knight object.

        HashMap<Integer, ArrayList<Move>> potentialLegalKnightMovesMap = new HashMap<>();
        int targetSquare;
        int[][] knightOffsets = {{-2, -1}, {-2, 1}, {-1, -2}, {-1, 2}, {1, - 2}, {1, 2}, {2, -1}, {2, 1}};

        // Loops through all the squares and calculates all the potentially legal knight moves
        for (int boardIndex = 0; boardIndex < 64; boardIndex++){
            int rank = boardIndex / 8;
            int file = boardIndex % 8;
            ArrayList<Move> potentialLegalKnightMoves = new ArrayList<>();

            // A perfectly placed knight has eight legal moves. Loop through all of them and check
            // if they are potentially legal (does not move out of bounds)
            for (int i = 0; i < 8; i ++){
                if (isInBounds(rank + knightOffsets[i][0], file + knightOffsets[i][1])){
                    targetSquare = (rank + knightOffsets[i][0]) * 8 + (file + knightOffsets[i][1]);
                    potentialLegalKnightMoves.add(new Move(boardIndex, targetSquare));
                }
            }

            potentialLegalKnightMovesMap.put(boardIndex, potentialLegalKnightMoves);
        }

       potentialLegalKnightMoveMap = potentialLegalKnightMovesMap;
    }


    public void generateKnightMoves(Piece piece, Piece[] boardArray, ArrayList<Move> allMoves) {
        // Gets all the potentially legal moves for a knight in the given position on the board
        // and filter out the illegal moves
        ArrayList<Move> potentialMoves = potentialLegalKnightMoveMap.get(piece.getLocation());
        ArrayList<Move> legalKnightMoves = new ArrayList<>();

        for (Move move : potentialMoves){
            int targetSquare = move.getTargetSquare();

            // Move is legal if the candidate target square is empty or occupied by enemy piece
            if (boardArray[targetSquare] == null){
                legalKnightMoves.add(move);
            }
            else{
                if (!boardArray[targetSquare].getColor().equals(piece.getColor())){
                    legalKnightMoves.add(move);
                }
            }
        }

        allMoves.addAll(legalKnightMoves);

    }


    public void generateKingMoves(Piece piece, Piece[] boardArray, ArrayList<Move> allMoves){
        int startSquare = piece.getLocation();
        ArrayList<Move> legalKingMoves = new ArrayList<>();

        for (int directionIndex = 0; directionIndex < 8; directionIndex ++){

            int targetSquare = startSquare + getSlidingDirectionOffsets()[directionIndex];

            if (targetSquare >= 0 && targetSquare < 64)
            {
                // If the target square is occupied by a friendly piece, stop searching for more
                // moves in this direction
                if (boardArray[targetSquare] == null){
                    legalKingMoves.add(new Move(startSquare, targetSquare));
                }

                // If the target square is occupied by an enemy piece, we stop searching for more
                // moves in this direction (the capture itself was made legal in the statement above
                if (boardArray[targetSquare] != null){
                    if (!boardArray[targetSquare].getColor().equals(piece.getColor())){
                        legalKingMoves.add(new Move(startSquare, targetSquare));
                    }
                }
            }
        }

        allMoves.addAll(legalKingMoves);

    }


    public void generateRookMoves(Piece piece, Piece[] boardArray, ArrayList<Move> allMoves){
        int startSquare = piece.getLocation();
        ArrayList<Move> legalRookMoves = new ArrayList<>();

        for (int directionIndex = 0; directionIndex < 4; directionIndex ++){
            for (int i = 0; i < getNumSquaresToEdge()[startSquare][directionIndex]; i++){
                int targetSquare = startSquare + getSlidingDirectionOffsets()[directionIndex] * (i + 1);

                // If the target square is occupied by a friendly piece, stop searching for more
                // moves in this direction
                if (boardArray[targetSquare] != null){
                    if (boardArray[targetSquare].getColor().equals(piece.getColor())){
                        break;
                    }
                }

                // If the target square is not occupied by friendly piece, this move must be legal
                legalRookMoves.add(new Move(startSquare, targetSquare));

                // If the target square is occupied by an enemy piece, we stop searching for more
                // moves in this direction (the capture itself was made legal in the statement above
                if (boardArray[targetSquare] != null){
                    if (!boardArray[targetSquare].getColor().equals(piece.getColor())){
                        break;
                    }
                }
            }
        }

        allMoves.addAll(legalRookMoves);
    }


    public void generateBishopMoves(Piece piece, Piece[] boardArray, ArrayList<Move> allMoves){
        int startSquare = piece.getLocation();
        ArrayList<Move> legalBishopMoves = new ArrayList<>();

        for (int directionIndex = 4; directionIndex < 8; directionIndex ++){
            for (int i = 0; i < getNumSquaresToEdge()[startSquare][directionIndex]; i++){
                int targetSquare = startSquare + getSlidingDirectionOffsets()[directionIndex] * (i + 1);

                // If the target square is occupied by a friendly piece, stop searching for more
                // moves in this direction
                if (boardArray[targetSquare] != null){
                    if (boardArray[targetSquare].getColor().equals(piece.getColor())){
                        break;
                    }
                }

                // If the target square is not occupied by friendly piece, this move must be legal
                legalBishopMoves.add(new Move(startSquare, targetSquare));

                // If the target square is occupied by an enemy piece, we stop searching for more
                // moves in this direction (the capture itself was made legal in the statement above
                if (boardArray[targetSquare] != null){
                    if (!boardArray[targetSquare].getColor().equals(piece.getColor())){
                        break;
                    }
                }
            }
        }

        allMoves.addAll(legalBishopMoves);

    }

    public void generateQueenMoves(Piece piece, Piece[] boardArray, ArrayList<Move> allMoves){
        int startSquare = piece.getLocation();
        ArrayList<Move> legalQueenMoves = new ArrayList<>();

        for (int directionIndex = 0; directionIndex < 8; directionIndex ++){
            for (int i = 0; i < getNumSquaresToEdge()[startSquare][directionIndex]; i++){
                int targetSquare = startSquare + getSlidingDirectionOffsets()[directionIndex] * (i + 1);

                // If the target square is occupied by a friendly piece, stop searching for more
                // moves in this direction
                if (boardArray[targetSquare] != null){
                    if (boardArray[targetSquare].getColor().equals(piece.getColor())){
                        break;
                    }
                }

                // If the target square is not occupied by friendly piece, this move must be legal
                legalQueenMoves.add(new Move(startSquare, targetSquare));

                // If the target square is occupied by an enemy piece, we stop searching for more
                // moves in this direction (the capture itself was made legal in the statement above
                if (boardArray[targetSquare] != null){
                    if (!boardArray[targetSquare].getColor().equals(piece.getColor())){
                        break;
                    }
                }
            }
        }

        allMoves.addAll(legalQueenMoves);

    }

    public void generatePawnMoves(Piece piece, Piece[] boardArray, int enPassantSquare, ArrayList<Move> allMoves){
        int pieceColorMultiplier = piece.getColor().equals("white") ? 1 : -1;
        int pawnLocation = piece.getLocation();
        int rank = piece.getLocation() / 8;
        int oneSquareForwardIndex = pawnLocation - 8 * pieceColorMultiplier;
        int twoSquareForwardIndex = pawnLocation - 16 * pieceColorMultiplier;
        int potentialCaptureOneIndex = oneSquareForwardIndex + 1;
        int potentialCaptureTwoIndex = oneSquareForwardIndex - 1;
        int targetRankAfterCapture = oneSquareForwardIndex / 8;

        ArrayList<Move> legalPawnMoves = new ArrayList<>();

        if (isInBounds(oneSquareForwardIndex)){
            // test if the pawn can move forward one space
            if (boardArray[oneSquareForwardIndex] == null){
                checkPromotionMoves(pawnLocation, oneSquareForwardIndex, legalPawnMoves);


            }

            // test if the pawn can move forward two spaces
            if (rank == 6 && piece.getColor().equals("white") || rank == 1 && piece.getColor().equals("black")){
                if (boardArray[twoSquareForwardIndex] == null && boardArray[oneSquareForwardIndex] == null){
                    legalPawnMoves.add(new Move(pawnLocation, twoSquareForwardIndex, "p2"));
                }
            }

            // Checks if the pawn can capture sideways in both directions
            checkForCaptures(pawnLocation,
                    potentialCaptureOneIndex,
                    potentialCaptureTwoIndex,
                    targetRankAfterCapture,
                    legalPawnMoves, piece, boardArray);

            // Check if en passant in possible
            checkForEnPassant(pawnLocation,
                    potentialCaptureOneIndex,
                    potentialCaptureTwoIndex,
                    targetRankAfterCapture,
                    legalPawnMoves, enPassantSquare);
        }

        allMoves.addAll(legalPawnMoves);

    }


    private void checkForCaptures(int pawnLocation, int potentialCaptureOneIndex, int potentialCaptureTwoIndex,
                                  int targetRankAfterCapture, ArrayList<Move> legalPawnMoves, Piece piece, Piece[] boardArray) {

        // Check if the pawn can capture to the right (white perspective)
        checkCaptureInOneDirection(pawnLocation, potentialCaptureOneIndex, targetRankAfterCapture, legalPawnMoves, piece, boardArray);

        // Check if the pawn can capture to the left (white perspective)
        checkCaptureInOneDirection(pawnLocation, potentialCaptureTwoIndex, targetRankAfterCapture, legalPawnMoves, piece, boardArray);
    }


    private void checkCaptureInOneDirection(int pawnLocation, int potentialCaptureIndex, int targetRankAfterCapture,
                                            ArrayList<Move> legalPawnMoves, Piece piece, Piece[] boardArray) {

        if (potentialCaptureIndex / 8 == targetRankAfterCapture &&
                boardArray[potentialCaptureIndex] != null){
            if (!boardArray[potentialCaptureIndex].getColor().equals(piece.getColor())){
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
                                   int targetRankAfterCapture, ArrayList<Move> legalPawnMoves, int enPassantSquare) {

        if (potentialCaptureOneIndex / 8 == targetRankAfterCapture &&
                potentialCaptureOneIndex == enPassantSquare){
            legalPawnMoves.add(new Move(pawnLocation, potentialCaptureOneIndex, "ep"));
        }

        else if (potentialCaptureTwoIndex / 8 == targetRankAfterCapture &&
                potentialCaptureTwoIndex == enPassantSquare){
            legalPawnMoves.add(new Move(pawnLocation, potentialCaptureTwoIndex, "ep"));
        }
    }


    public boolean isInBounds(int rank, int file){
        return rank >= 0 && rank < 8 && file >= 0 && file < 8;
    }

    public boolean isInBounds(int index){
        return index >= 0 && index < 64;
    }

    public boolean isPromotionSquare(int index){
        return index / 8 == 0 || index / 8 == 7;
    }

    public int[][] getNumSquaresToEdge(){
        return NumSquaresToEdge;
    }

    public int[] getSlidingDirectionOffsets() {
        return SlidingDirectionOffsets;
    }

    public HashMap<Integer, ArrayList<Move>> getPotentialLegalKnightMoveMap(){
        return potentialLegalKnightMoveMap;
    }

    public ArrayList<Move> getLegalMoves() {
        return legalMoves;
    }
}
