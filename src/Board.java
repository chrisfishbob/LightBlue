import processing.core.PImage;

import java.util.ArrayList;

public class Board {
    private Piece[] boardArray;
    private LightBlueMain main;
    private SoundProcessor soundProcessor;
    private int nullValue = -1;
    private int previousMoveStartSquare = nullValue;
    private int previousMoveTargetSquare = nullValue;
    private int selectedSquare = nullValue;
    private int windowWidth;
    private int windowHeight;
    private int squareSize;
    private final int[] yellow1 = {246, 245, 149};
    private final int[] blue1 = {167, 203, 202};
    private final int[] blue2 = {139, 190, 174};
    private final int[] darkGreen = {238, 237, 213};
    private final int[] offWhite  = {124, 148, 93};

    private final PImage legalMoveBG;
    private final PImage targetedPieceBG;

    public ArrayList<Integer> legalMoveSquaresForSelectedPiece = new ArrayList<>();

    public Board(LightBlueMain main, SoundProcessor soundProcessor){
        boardArray = new Piece[64];
        this.main = main;
        this.soundProcessor = soundProcessor;
        this.windowWidth = main.getWindowWidth();
        this.windowHeight = main.getWindowHeight();
        this.squareSize = main.getSquareSize();
        legalMoveBG = main.loadImage("dot5.png");
        targetedPieceBG = main.loadImage("outline.png");
    }


    public void drawPieces(int windowWidth, boolean mouseIsHeldDown, float mouseX, float mouseY){
        for (Piece piece: boardArray){
            if (piece != null) {
                int row = piece.getLocation() / 8;
                int column = piece.getLocation() % 8;
                int squareSize = windowWidth / 8;

                // If the piece is not selected, or if the mouse is not being held down,
                // just display the image at wherever its location is on the board
                if (!piece.isSelected() || !mouseIsHeldDown)
                {
                    main.image(piece.getImage(), column * squareSize, row * squareSize, squareSize, squareSize);
                }

                // Display the piece, following the position of the cursor.
                else{
                    main.image(piece.getImage(), mouseX - (float) squareSize / 2,
                            mouseY - (float) squareSize / 2, squareSize, squareSize);
                }
            }
        }
    }


    public void drawBoard(){
        // Called for every frame, this method draws an empty boardArray.
        main.noStroke();

        for (int rank = 0; rank < 8; rank++){
            for (int file = 0; file < 8; file++){
                boolean isLightSquare = (file + rank) % 2 != 0;

                // If the current square is the selected square or the released square
                // draw the special colored square
                if (rank * 8 + file == selectedSquare){

                    main.fill(yellow1[0], yellow1[1], yellow1[2]);
                }

                // Highlight the previous move squares in alternating shades of blue
                else if (rank * 8 + file == previousMoveStartSquare ||
                        rank * 8 + file == previousMoveTargetSquare){
                    if (isLightSquare){
                        main.fill(blue1[0], blue1[1], blue1[2]);
                    }
                    else{
                        main.fill(blue2[0], blue2[1], blue2[2]);
                    }
                }


                // The square is not a special highlighted square, just draw the boardArray
                else{
                    if (!isLightSquare){
                        main.fill(darkGreen[0], darkGreen[1], darkGreen[2]);
                    }
                    else{
                        main.fill(offWhite[0], offWhite[1], offWhite[2]);
                    }
                }


                // Draw the square itself after color is established
                main.rect(file * (float) (windowWidth / 8),
                        rank * (float) (windowHeight / 8),
                        (float) windowWidth / 8, (float) windowHeight / 8);


                if (legalMoveSquaresForSelectedPiece.contains(rank * 8 + file))
                {
                    if (boardArray[rank * 8 + file] == null){
                        main.image(legalMoveBG, file * (float) (windowWidth / 8), rank * (float) (windowHeight / 8),
                                (float) windowWidth / 8, (float) windowHeight / 8);
                    }
                    else{
                        main.image(targetedPieceBG, file * (float) (windowWidth / 8), rank * (float) (windowHeight / 8),
                                (float) windowWidth / 8, (float) windowHeight / 8);
                    }

                }
            }
        }
    }

    public void loadFromFen(String fen){
        soundProcessor.playSound("start");
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
        boardArray[piece.getLocation()] = piece;
    }

    public void clearBoard(){
        for (int i = 0; i < 64 ; i ++){
            boardArray[i] = null;
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

    public void printBoard(){
        System.out.println("-------------------------------");
        for (int i = 0 ; i < 64 ; i ++){
            if (boardArray[i] != null){
                // Print out the piece's character if not selected, otherwise put single quotes
                // around the character.
                // ex: [k] = unselected black king, ['P'] = selected white pawn
                if (!boardArray[i].isSelected())
                {
                    System.out.print('[' + boardArray[i].getPieceChar() + ']' + " ");
                }
                else{
                    System.out.print("['" + boardArray[i].getPieceChar() + "']" + " ");
                }
            }

//            else{
//                    if (i != enPassantSquare){
//                        System.out.print('[' + " " + ']' + " ");
//                    }
//                    else{
//                        System.out.print('[' + "E" + ']' + " ");
//                    }
//
//            }

            // Print blank line after 8 squares
            if ((i + 1) % 8 == 0){
                System.out.println();
            }
        }
        System.out.println("-------------------------------\n\n");
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

    public Piece[] getBoardArray(){
        return this.boardArray;
    }


}
