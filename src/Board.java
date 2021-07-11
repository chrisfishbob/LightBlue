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
    private final int windowWidth;
    private final int windowHeight;
    private final int squareSize;
    private final int[] yellow1 = {246, 245, 149};
    private final int[] blue1 = {167, 203, 202};
    private final int[] blue2 = {139, 190, 174};
    private final int[] darkGreen = {238, 237, 213};
    private final int[] offWhite  = {124, 148, 93};

    private final PImage legalMoveBG;
    private final PImage targetedPieceBG;

    public ArrayList<Integer> legalMoveSquaresForSelectedPiece = new ArrayList<>();
    private Piece capturedPiece;
    private Move previousMove;
    private String colorToMove = "white";
    private int enPassantSquare = nullValue;
    private boolean pieceAlreadySelected;
    private boolean unselectOnRelease;
    private int releasedSquare = nullValue;


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

            else{
                    if (i != enPassantSquare){
                        System.out.print('[' + " " + ']' + " ");
                    }
                    else{
                        System.out.print('[' + "E" + ']' + " ");
                    }

            }

            // Print blank line after 8 squares
            if ((i + 1) % 8 == 0){
                System.out.println();
            }
        }
        System.out.println("-------------------------------\n\n");
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
        selectedSquare = nullValue;
        legalMoveSquaresForSelectedPiece.clear();


        System.out.println(piece.getColor());
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
                enPassantSquare = nullValue;
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
                enPassantSquare = nullValue;
            }
        }
        else{
            enPassantSquare = nullValue;
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
        selectedSquare = nullValue;
        legalMoveSquaresForSelectedPiece.clear();
        previousMoveStartSquare = nullValue;
        previousMoveTargetSquare = nullValue;
        enPassantSquare = nullValue;
        colorToMove = piece.getColor().equals("white") ? "white" : "black";

        if (capturedPiece != null){
            boardArray[startSquare] = capturedPiece;
        }
    }


    public void selectPiece(Piece piece){
        piece.setSelected(true);
        pieceAlreadySelected = true;
        // We have to mark unselectOnRelease as false because unselecting is handled
        // during the mouseReleased event. Not marking the square to not unselect on
        // release will cause the selected piece to be immediately unselected upon mouse release
        unselectOnRelease = false;
        ArrayList<Move> allMoves = MoveGenerator.generateAllMoves(colorToMove);

        for (Move move : allMoves){
            if (move.getStartSquare() == piece.getLocation()){
                legalMoveSquaresForSelectedPiece.add(move.getTargetSquare());
            }
        }

//        if (piece.getColor().equals(colorToMove)){
//            piece.generateMoves();
//        }

    }



    public void processMouseRelease(int mouseX, int mouseY){
        // Checks if the player released the mouse in bounds
        if (mouseX >= 0 && mouseY >= 0 && mouseX < windowWidth && mouseY < windowHeight){
            int file = mouseX / squareSize;
            int rank = mouseY / squareSize;
            int test = selectedSquare;
            releasedSquare = rank * 8 + file;


            // Move the piece if the player dragged the piece to a different square
            if (selectedSquare != releasedSquare && selectedSquare != nullValue){
                if (legalMoveSquaresForSelectedPiece.contains(releasedSquare)){
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
                selectedSquare = nullValue;
                releasedSquare = nullValue;
            }
        }

        // Player release mouse out of bounds, unselect selected piece and set selected Square to null
        else {
            unselectPiece(boardArray[selectedSquare]);
            selectedSquare = nullValue;
        }
    }


    public void unselectPiece(Piece piece){
        piece.setSelected(false);
        pieceAlreadySelected = false;
        legalMoveSquaresForSelectedPiece.clear();
    }


    public void processHighlighting(int mouseX, int mouseY){
        int file = mouseX / squareSize;
        int rank = mouseY / squareSize;
        selectedSquare = rank * 8 + file;

        // If the player clicks on an empty square, deselect all squares
        if (boardArray[selectedSquare] == null){
            selectedSquare = nullValue;
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

    public void resetBoard(){
        // This method resets the boardArray to its initial stage
        loadFromFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");
        colorToMove = "white";
        selectedSquare = nullValue;
        releasedSquare = nullValue;
        pieceAlreadySelected = false;
        unselectOnRelease = false;
        previousMoveStartSquare = nullValue;
        previousMoveTargetSquare = nullValue;
        legalMoveSquaresForSelectedPiece.clear();
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

    public void changeColorToMove(){
        colorToMove = colorToMove.equals("white") ? "black" : "white";
    }

    public Piece[] getBoardArray(){
        return this.boardArray;
    }

    public Move getPreviousMove(){
        return previousMove;
    }

    public String getColorToMove(){
        return colorToMove;
    }
}
