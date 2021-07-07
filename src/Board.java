import processing.core.PApplet;
import processing.core.PImage;
import javax.sound.sampled.*;
import java.io.File;
import java.lang.*;
import java.util.ArrayList;


public class Board extends PApplet {
    Piece[] board;
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

    private int selectedSquare = getNullValue();
    private int releasedSquare = getNullValue();
    private boolean pieceAlreadySelected = false;
    private boolean mouseIsHeldDown = false;
    private boolean unselectOnRelease = false;
    private String colorToMove = "white";
    private final int[] yellow1 = {206, 206, 44};
    private final int[] blue1 = {177, 213, 212};
    private final int[] blue2 = {139, 190, 174};
    private final int[] darkGreen = {238, 237, 213};
    private final int[] offwhite = {124, 148, 93};
    private ArrayList<Integer> otherHighlightedSquares = new ArrayList<>();
    private int previousMoveStartSquare = getNullValue();
    private int previousMoveTargetSquare = getNullValue();

    public void settings(){
        size(windowWidth, windowHeight);
    }

    public void setup(){
        loadImages();
        board = new Piece[64];
        loadFromFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");
    }


    public void draw(){
        // Main loop of the program
        background(64);
        drawBoard();
        for (Piece piece: board){
            if (piece != null) {
                int row = piece.getLocation() / 8;
                int column = piece.getLocation() % 8;
                int squareSize = windowWidth / 8;

                // If the piece is not selected, or if the mouse is not being held down,
                // just display the image at wherever its location is on the board
                if (!piece.isSelected() || !mouseIsHeldDown)
                {
                    image(piece.getImage(), column * squareSize, row * squareSize, squareSize, squareSize);
                }

                // Display the piece, following the position of the cursor.
                else{
                    image(piece.getImage(), mouseX - (float) squareSize / 2,
                            mouseY - (float) squareSize / 2, squareSize, squareSize);
                }
            }
        }
    }


    public void drawBoard(){
        // Called for every frame, this method draws an empty board.
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

                else if (otherHighlightedSquares.contains(rank * 8 + file))
                {
                    fill(255, 0, 0, 130);
                }

                // The square is not a special highlighted square, just draw the board
                else{
                    if (!isLightSquare){
                        fill(darkGreen[0], darkGreen[1], darkGreen[2]);
                    }
                    else{
                        fill(offwhite[0], offwhite[1], offwhite[2]);
                    }
                }

                // Draw the square itself after color is established
                rect(file * (float) (windowWidth / 8),
                        rank * (float) (windowHeight / 8),
                        (float) windowWidth / 8, (float) windowHeight / 8);
            }
        }
    }


    public void loadFromFen(String fen){
        playSound("start");
        int rank = 0;
        int file = 0;

        clearBoard();
        for (char chr : fen.toCharArray()){
            if (Character.isLetter(chr)){
                Piece piece = getPieceFromCharacter(chr);
                piece.setLocation(rank * 8 + file);
                putPiece(piece);
                file ++;
            }

            else if (Character.isDigit(chr)){
                file += Character.getNumericValue(chr);
            }

            else if (chr == '/'){
                rank ++;
                file = 0;
            }
        }
    }


    public void putPiece(Piece piece){
        board[piece.getLocation()] = piece;
    }


    public void removePiece(Piece piece){
        board[piece.getLocation()] = null;
    }


    public void removePieceAt(int location){
        board[location] = null;
    }


    public void clearBoard(){
        for (int i = 0; i < 64 ; i ++){
            board[i] = null;
        }
    }


    public Piece getPieceFromCharacter(char character){
        return switch (character) {
            case 'r' -> new Rook("black", 0);
            case 'R' -> new Rook("white", 0);
            case 'b' -> new Bishop("black", 0);
            case 'B' -> new Bishop("white", 0);
            case 'q' -> new Queen("black", 0);
            case 'Q' -> new Queen("white", 0);
            case 'k' -> new King("black", 0);
            case 'K' -> new King("white", 0);
            case 'n' -> new Knight("black", 0);
            case 'N' -> new Knight("white", 0);
            case 'p' -> new Pawn("black", 0);
            case 'P' -> new Pawn("white", 0);
            default -> throw new IllegalStateException("Unexpected value: " + character);
        };
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
    }


    public void movePiece(Move move){
        // Null check not really necessary, can consider removing later
        int startSquare = move.getStartSquare();
        int targetSquare = move.getTargetSquare();
        Piece piece = board[startSquare];

        if (board[startSquare] != null){
            // If not the right color to move, don't move the piece
            if (!board[startSquare].getColor().equals(colorToMove)){
                return;
            }

            processSound(move);

            piece.setSelected(false);
            piece.setLocation(targetSquare);
            board[targetSquare] = piece;
            board[startSquare] = null;
            previousMoveStartSquare = startSquare;
            previousMoveTargetSquare = targetSquare;
            selectedSquare = getNullValue();


            if (piece.getColor().equals("white")){
                colorToMove = "black";
            }
            else{
                colorToMove = "white";
            }
        }

        else{
            System.out.println("Invalid move: start square has no piece object");
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
                Move move = new Move(selectedSquare, releasedSquare);
                movePiece(move);
            }

            // If clicking on a square that is already selected, we unselct
            else if (selectedSquare == releasedSquare && unselectOnRelease){
                unselectPiece(board[selectedSquare]);
                selectedSquare = getNullValue();
                releasedSquare = getNullValue();
            }
        }

        // Player release mouse out of bounds, unselect selected piece and set selected Square to null
        else {
            unselectPiece(board[selectedSquare]);
            selectedSquare = getNullValue();
        }
    }


    public void keyPressed(){
        switch (key) {
            case 'p' -> {
                printBoard();
                System.out.println("Released square is: " + releasedSquare);
                System.out.println("Selected square is: " + selectedSquare);
                System.out.println(colorToMove + " to move");
            }
            case 'e' -> System.out.println(getEvaluation());
            case 'r' -> resetBoard();
            case 't' -> colorToMove = colorToMove.equals("white") ? "black" : "white";
            case 'v' -> verifyBoard();
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
        if (board[selectedSquare] == null){
            selectedSquare = getNullValue();
            for (Piece pc : board){
                if (pc != null){
                    if (pc.isSelected()){
                        unselectPiece(pc);
                        break;
                    }
                }
            }
        }


        // Loops through the board and make the selected piece highlighted if not already highlighted
        for (Piece piece : board){
            // Null check: We only allow the play to select pieces, not empty squares
            if (piece != null){
                // Case where the piece is not selected and there are no pieces selected on the board
                if (!piece.isSelected() && piece.getLocation() == selectedSquare && !pieceAlreadySelected){
                    selectPiece(piece);
                }

                // Case where the piece is not selected but one piece is already selected on the board
                else if (!piece.isSelected() && piece.getLocation() == selectedSquare && pieceAlreadySelected){
                    // find the original selected piece then unselect it
                    for (Piece pc : board){
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


    public void printBoard(){
        System.out.println("-------------------------------");
        for (int i = 0 ; i < 64 ; i ++){
            if (board[i] != null){
                // Print out the piece's character if not selected, otherwise put single quotes
                // around the character.
                // ex: [k] = unselected black king, ['P'] = selected white pawn
                if (!board[i].isSelected())
                {
                    System.out.print('[' + board[i].getPieceChar() + ']' + " ");
                }
                else{
                    System.out.print("['" + board[i].getPieceChar() + "']" + " ");
                }
            }

            else{
                System.out.print('[' + " " + ']' + " ");
            }

            // Print blank line after 8 squares
            if ((i + 1) % 8 == 0){
                System.out.println();
            }
        }
        System.out.println("-------------------------------\n\n");
    }


    public double getEvaluation(){
        double eval = 0;

        for (Piece piece : board){
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


    public void processSound(Move move){
        // This function determines which sound should be played given the Move object
        if (board[move.getTargetSquare()] == null){
            playSound("move");
        }
        else{
            playSound("capture");
        }
    }


    public void playSound(String soundName){
        // This function plays the actual sound file according to the results of processSound
        try{
            File file = new File("sound/" + soundName + ".wav");
            AudioInputStream move = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(move);
            clip.start();
        }

        catch (Exception e){
            System.out.println(e.getMessage());
        }

    }


    public void unselectPiece(Piece piece){
        piece.setSelected(false);
        pieceAlreadySelected = false;
    }

    public void selectPiece(Piece piece){
        piece.setSelected(true);
        pieceAlreadySelected = true;
        // We have to mark unselectOnRelease as false because unselecting is handled
        // during the mouseReleased event. Not marking the square to not unselect on
        // release will cause the selected piece to be immediately unselected upon mouse release
        unselectOnRelease = false;
    }



    public void resetBoard(){
        // This method resets the board to its initial stage
        loadFromFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");
        colorToMove = "white";
        selectedSquare = getNullValue();
        releasedSquare = getNullValue();
        pieceAlreadySelected = false;
        unselectOnRelease = false;
        previousMoveStartSquare = getNullValue();
        previousMoveTargetSquare = getNullValue();
    }


    public int getNullValue(){
        return 99;
    }


    public boolean aPieceIsSelected(){
        return selectedSquare != getNullValue();
    }


    public void verifyBoard() {
        boolean verificationSuccessful = true;

        for (int i = 0; i < 64; i++) {
            if (board[i] != null) {
                if (board[i].getLocation() != i) {
                    System.out.println("Board verification failed. Piece at board index " + i +
                            " has location of " + board[i].getLocation() + " in the piece object");
                    verificationSuccessful = false;
                }
            }
        }
        if (verificationSuccessful){
            System.out.println("Board verification successful");
        }
    }


    public static void main(String[] args){
        String[] appletArgs = new String[] {"Board"};
        PApplet.main(appletArgs);
    }
}
