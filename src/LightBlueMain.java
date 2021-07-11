import processing.core.PApplet;
import processing.core.PImage;

import java.lang.*;
import java.util.ArrayList;


public class LightBlueMain extends PApplet {
    private static Piece[] boardArray;
    private Board board;


    private final int windowWidth = 800;
    private final int windowHeight = 800;
    private final int squareSize = windowWidth / 8;
    public static PImage blackKing;
    public static PImage whiteKing;
    public static PImage blackQueen;
    public static PImage whiteQueen;
    public static PImage blackRook;
    public static PImage whiteRook;
    public static PImage blackBishop;
    public static PImage whiteBishop;
    public static PImage blackKnight;
    public static PImage whiteKnight;
    public static PImage blackPawn;
    public static PImage whitePawn;
    public static PImage targetedPieceBG;
    public static PImage legalMoveBG;
    private final int[] yellow1 = {246, 245, 149};
    private final int[] blue1 = {167, 203, 202};
    private final int[] blue2 = {139, 190, 174};
    private final int[] darkGreen = {238, 237, 213};
    private final int[] offWhite  = {124, 148, 93};

    private static int selectedSquare = getNullValue();
    private int releasedSquare = getNullValue();
    private boolean pieceAlreadySelected = false;
    private boolean mouseIsHeldDown = false;
    private boolean unselectOnRelease = false;
    private static String colorToMove = "white";




    private int previousMoveStartSquare = getNullValue();
    private int previousMoveTargetSquare = getNullValue();
    private static int enPassantSquare = getNullValue();
    private static Move previousMove;
    public static boolean isMute = false;
    private static Piece capturedPiece;
    private final SoundProcessor soundProcessor = new SoundProcessor();


    public void settings(){
        size(windowWidth, windowHeight);
    }

    public void setup(){
        loadImages();
        board = new Board(this, soundProcessor);
        boardArray = board.getBoardArray();
        board.loadFromFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");
        MoveGenerator.preGenerateKnightMoves();
        MoveGenerator.PrecomputeMoveData();
    }


    public void draw(){
        // Main loop of the program
        background(64);
        drawBoard();
        board.drawPieces(windowWidth, mouseIsHeldDown, mouseX, mouseY);

    }


    public void drawBoard(){
        // Called for every frame, this method draws an empty boardArray.
        noStroke();

        for (int rank = 0; rank < 8; rank++){
            for (int file = 0; file < 8; file++){
                boolean isLightSquare = (file + rank) % 2 != 0;

                // If the current square is the selected square or the released square
                // draw the special colored square
                if (rank * 8 + file == selectedSquare){

                    fill(yellow1[0], yellow1[1], yellow1[2]);
                }

                // Highlight the previous move squares in alternating shades of blue
                else if (rank * 8 + file == previousMoveStartSquare ||
                        rank * 8 + file == previousMoveTargetSquare){
                    if (isLightSquare){
                        fill(blue1[0], blue1[1], blue1[2]);
                    }
                    else{
                        fill(blue2[0], blue2[1], blue2[2]);
                    }
                }


                // The square is not a special highlighted square, just draw the boardArray
                else{
                    if (!isLightSquare){
                        fill(darkGreen[0], darkGreen[1], darkGreen[2]);
                    }
                    else{
                        fill(offWhite[0], offWhite[1], offWhite[2]);
                    }
                }


                // Draw the square itself after color is established
                rect(file * (float) (windowWidth / 8),
                        rank * (float) (windowHeight / 8),
                        (float) windowWidth / 8, (float) windowHeight / 8);


                if (board.legalMoveSquaresForSelectedPiece.contains(rank * 8 + file))
                {
                    if (boardArray[rank * 8 + file] == null){
                        image(legalMoveBG, file * (float) (windowWidth / 8), rank * (float) (windowHeight / 8),
                                (float) windowWidth / 8, (float) windowHeight / 8);
                    }
                    else{
                        image(targetedPieceBG, file * (float) (windowWidth / 8), rank * (float) (windowHeight / 8),
                                (float) windowWidth / 8, (float) windowHeight / 8);
                    }

                }
            }
        }
    }



    public void putPiece(Piece piece){
        boardArray[piece.getLocation()] = piece;
    }


    public void removePiece(Piece piece){
        boardArray[piece.getLocation()] = null;
    }


    public void removePieceAt(int location){
        boardArray[location] = null;
    }


    public void clearBoard(){
        for (int i = 0; i < 64 ; i ++){
            boardArray[i] = null;
        }
    }



    public void loadImages(){
        // Called by setup method. This method loads all the images used

        blackKing = loadImage("BlackKing.png");
        whiteKing = loadImage("WhiteKing.png");
        blackQueen = loadImage("BlackQueen.png");
        whiteQueen = loadImage("WhiteQueen.png");
        blackRook = loadImage("BlackRook.png");
        whiteRook = loadImage("WhiteRook.png");
        blackBishop = loadImage("BlackBishop.png");
        whiteBishop = loadImage("WhiteBishop.png");
        blackKnight = loadImage("BlackKnight.png");
        whiteKnight = loadImage("WhiteKnight.png");
        blackPawn = loadImage("BlackPawn.png");
        whitePawn = loadImage("WhitePawn.png");
        targetedPieceBG = loadImage("outline.png");
    }


    public void makeMove(Move move){
        // Null check not really necessary, can consider removing later
        int startSquare = move.getStartSquare();
        int targetSquare = move.getTargetSquare();
        Piece piece = boardArray[startSquare];

        if (boardArray[targetSquare] != null) {
            capturedPiece = boardArray[targetSquare];
            System.out.println(capturedPiece);
        }
        else{
            capturedPiece = null;
        }

        soundProcessor.processSound(move);
        previousMove = move;
        piece.setSelected(false);
        piece.setLocation(targetSquare);
        boardArray[targetSquare] = piece;
        boardArray[startSquare] = null;
        previousMoveStartSquare = startSquare;
        previousMoveTargetSquare = targetSquare;
        selectedSquare = getNullValue();
        board.legalMoveSquaresForSelectedPiece.clear();


        if (piece.getColor().equals("white")){
            colorToMove = "black";
        }
        else{
            colorToMove = "white";
        }





        if (move.isSpecialMove()){
            // When a pawn moves by two spaces, mark the enPassantSquare
            String flag = move.getSpecialFlagKind();
            if (flag.equals("p2")){
                enPassantSquare = startSquare + (targetSquare - startSquare) / 2;
            }
            else if (flag.equals("ep")){
                if (piece.getColor().equals("white")){
                    capturedPiece = boardArray[enPassantSquare + 8];
                    boardArray[enPassantSquare + 8] = null;
                }
                else{
                    capturedPiece = boardArray[enPassantSquare - 8];
                    boardArray[enPassantSquare - 8] = null;
                }
                enPassantSquare = getNullValue();
            }

            else if (flag.equals("queen")){
                boardArray[targetSquare] = null;
                boardArray[targetSquare] = new Queen(piece.getColor(), targetSquare);
            }

            else if (flag.equals("rook")){
                boardArray[targetSquare] = null;
                boardArray[targetSquare] = new Rook(piece.getColor(), targetSquare);
            }
            else{
                enPassantSquare = getNullValue();
            }
        }
        else{
            enPassantSquare = getNullValue();
        }
    }

    public void unMakeMove(Move move){
        int targetSquare = move.getStartSquare();
        int startSquare = move.getTargetSquare();
        Piece piece = boardArray[startSquare];



        piece.setSelected(false);
        piece.setLocation(targetSquare);
        boardArray[targetSquare] = piece;
        boardArray[startSquare] = null;
        selectedSquare = getNullValue();
        board.legalMoveSquaresForSelectedPiece.clear();
        previousMoveStartSquare = getNullValue();
        previousMoveTargetSquare = getNullValue();
        enPassantSquare = getNullValue();
        colorToMove = piece.getColor().equals("white") ? "white" : "black";

        if (capturedPiece != null){
            boardArray[startSquare] = capturedPiece;
        }
    }


    public void mousePressed(){
        mouseIsHeldDown = true;
        processMouseClick();
    }


    public void mouseReleased(){
        mouseIsHeldDown = false;

        // Checks if the player released the mouse in bounds
        if (mouseX >= 0 && mouseY >= 0 && mouseX < windowWidth && mouseY < windowHeight){
            int file = mouseX / squareSize;
            int rank = mouseY / squareSize;
            releasedSquare = rank * 8 + file;

            // Move the piece if the player dragged the piece to a different square
            if (selectedSquare != releasedSquare && aPieceIsSelected()){
                if (board.legalMoveSquaresForSelectedPiece.contains(releasedSquare)){
                    ArrayList<Move> moves;
                    moves = boardArray[selectedSquare].getMoves();

                    for (Move move : moves){
                        if (move.getTargetSquare() == releasedSquare){
                            makeMove(move);
                            break;
                        }
                    }
                }
            }

            // If clicking on a square that is already selected, we unselect
            else if (selectedSquare == releasedSquare && unselectOnRelease){
                unselectPiece(boardArray[selectedSquare]);
                selectedSquare = getNullValue();
                releasedSquare = getNullValue();
            }
        }

        // Player release mouse out of bounds, unselect selected piece and set selected Square to null
        else {
            unselectPiece(boardArray[selectedSquare]);
            selectedSquare = getNullValue();
        }
    }


    public void keyPressed(){
        switch (key) {
            case 'p' -> {
                board.printBoard();
                System.out.println("Released square is: " + releasedSquare);
                System.out.println("Selected square is: " + selectedSquare);
                System.out.println(colorToMove + " to move");
            }
            case 'e' -> System.out.println(getEvaluation());
            case 'r' -> resetBoard();
            case 't' -> colorToMove = colorToMove.equals("white") ? "black" : "white";
            case 'v' -> verifyBoard();
            case 'm' -> {
                try {
                    MoveGenerator.generateAllMoves(colorToMove);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            case 'u' -> unMakeMove(previousMove);
        }
    }


    public void processMouseClick(){
        processHighlighting();
    }


    public void processHighlighting(){
        int file = mouseX / squareSize;
        int rank = mouseY / squareSize;
        selectedSquare = rank * 8 + file;

        // If the player clicks on an empty square, deselect all squares
        if (boardArray[selectedSquare] == null){
            selectedSquare = getNullValue();
            for (Piece pc : boardArray){
                if (pc != null){
                    if (pc.isSelected()){
                        unselectPiece(pc);
                        break;
                    }
                }
            }
        }


        // Loops through the boardArray and make the selected piece highlighted if not already highlighted
        for (Piece piece : boardArray){
            // Null check: We only allow the play to select pieces, not empty squares
            if (piece != null){
                // Case where the piece is not selected and there are no pieces selected on the boardArray
                if (!piece.isSelected() && piece.getLocation() == selectedSquare && !pieceAlreadySelected){
                    selectPiece(piece);
                }

                // Case where the piece is not selected but one piece is already selected on the boardArray
                else if (!piece.isSelected() && piece.getLocation() == selectedSquare && pieceAlreadySelected){
                    // find the original selected piece then unselect it
                    for (Piece pc : boardArray){
                        if (pc != null){
                            if (pc.isSelected()){
                                unselectPiece(pc);
                                break;
                            }
                        }
                    }
                    // Select the clicked piece
                    selectPiece(piece);
                }

                // If the piece that is clicked on is already selected before we clicked, mark the square
                // for unselecting upon the player releasing the mouse
                else if (piece.isSelected() && piece.getLocation() == selectedSquare){
                    unselectOnRelease = true;
                }
            }
        }
    }





    public double getEvaluation(){
        double eval = 0;

        for (Piece piece : boardArray){
            // Ignore the king in the evaluation for now, wait till check and checkmate implementation
            // to modify this.
            if (!(piece instanceof King) && piece != null){
                if (piece.getColor().equals("white")){
                    eval += piece.getValue();
                }
                else{
                    eval -= piece.getValue();
                }
            }
        }

        return eval;
    }






    public void unselectPiece(Piece piece){
        piece.setSelected(false);
        pieceAlreadySelected = false;
        board.legalMoveSquaresForSelectedPiece.clear();
    }

    public void selectPiece(Piece piece){
        piece.setSelected(true);
        pieceAlreadySelected = true;
        // We have to mark unselectOnRelease as false because unselecting is handled
        // during the mouseReleased event. Not marking the square to not unselect on
        // release will cause the selected piece to be immediately unselected upon mouse release
        unselectOnRelease = false;

        if (piece.getColor().equals(colorToMove)){
            piece.generateMoves();
        }

    }



    public void resetBoard(){
        // This method resets the boardArray to its initial stage
        board.loadFromFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");
        colorToMove = "white";
        selectedSquare = getNullValue();
        releasedSquare = getNullValue();
        pieceAlreadySelected = false;
        unselectOnRelease = false;
        previousMoveStartSquare = getNullValue();
        previousMoveTargetSquare = getNullValue();
        board.legalMoveSquaresForSelectedPiece.clear();
    }


    public static int getNullValue(){
        return 99;
    }


    public boolean aPieceIsSelected(){
        return selectedSquare != getNullValue();
    }


    public void verifyBoard() {
        boolean verificationSuccessful = true;

        for (int i = 0; i < 64; i++) {
            if (boardArray[i] != null) {
                if (boardArray[i].getLocation() != i) {
                    System.out.println("Board verification failed. Piece at boardArray index " + i +
                            " has location of " + boardArray[i].getLocation() + " in the piece object");
                    verificationSuccessful = false;
                }
            }
        }
        if (verificationSuccessful){
            System.out.println("Board verification successful");
        }
    }

    public static Piece[] getBoardArray(){
        return boardArray;
    }

    public String getColorToMove(){
        return colorToMove;
    }

    public static void setEnPassantSquare(int square){
        enPassantSquare = square;
    }

    public static int getEnPassantSquare(){
        return enPassantSquare;
    }


    public int getWindowWidth() {
        return windowWidth;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public int getSquareSize() {
        return squareSize;
    }

    public static void main(String[] args){
        String[] appletArgs = new String[] {"LightBlueMain"};
        PApplet.main(appletArgs);
    }
}
