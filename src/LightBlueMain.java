import processing.core.PApplet;
import processing.core.PImage;
import java.lang.*;



public class LightBlueMain extends PApplet {
    private Piece[] boardArray;
    private Board board;
    private MoveGenerator moveGenerator;
    private final int windowWidth = 800;
    private final int windowHeight = 800;
    private final int squareSize = windowWidth / 8;

    private static PImage blackKing;
    private static PImage whiteKing;
    private static PImage blackQueen;
    private static PImage whiteQueen;
    private static PImage blackRook;
    private static PImage whiteRook;
    private static PImage blackBishop;
    private static PImage whiteBishop;
    private static PImage blackKnight;
    private static PImage whiteKnight;
    private static PImage blackPawn;
    private static PImage whitePawn;

    private boolean mouseIsHeldDown = false;
    public static boolean isMute = false;
    private SoundProcessor soundProcessor;


    public void settings(){
        size(windowWidth, windowHeight);
    }

    public void setup(){
        loadImages();
        moveGenerator = new MoveGenerator();
        soundProcessor = new SoundProcessor();
        board = new Board(this, soundProcessor, moveGenerator);

        boardArray = board.getBoardArray();
//        board.loadFromFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");
//        moveGenerator.preGenerateKnightMoves();
//        moveGenerator.PrecomputeMoveData();
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
        board.drawBoard();
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
//        targetedPieceBG = loadImage("outline.png");
    }



    public void mousePressed(){
        mouseIsHeldDown = true;
        processMouseClick();
    }


    public void mouseReleased(){
        mouseIsHeldDown = false;
        board.processMouseRelease(mouseX, mouseY);
    }


    public void keyPressed(){
        switch (key) {
            case 'p' -> {
                board.printBoard();
                System.out.println("Released square is: " + board.getReleasedSquare());
                System.out.println("Selected square is: " + board.getSelectedSquare());
                System.out.println(board.getColorToMove() + " to move");
            }
            case 'e' -> System.out.println(board.getEvaluation());
            case 'r' -> board.resetBoard();
            case 't' -> board.changeColorToMove();
            case 'v' -> board.verifyBoard();
            case 'm' -> moveGenerator.generateAllMoves(board, board.getColorToMove());
            case 'u' -> board.unMakeMove(board.getPreviousMove());
        }
    }


    public void processMouseClick(){
        board.processHighlighting(mouseX, mouseY);
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

    public static PImage getPieceImage(String pieceName) {
        return switch (pieceName){
            case "blackKing" -> blackKing;
            case "blackQueen" -> blackQueen;
            case "blackRook" -> blackRook;
            case "blackBishop" -> blackBishop;
            case "blackKnight" -> blackKnight;
            case "blackPawn" -> blackPawn;
            case "whiteKing" -> whiteKing;
            case "whiteQueen" -> whiteQueen;
            case "whiteRook" -> whiteRook;
            case "whiteBishop" -> whiteBishop;
            case "whiteKnight" -> whiteKnight;
            case "whitePawn" -> whitePawn;
            default -> throw new IllegalStateException("Unexpected value: " + pieceName);
        };
    }

    public static void main(String[] args){
        String[] appletArgs = new String[] {"LightBlueMain"};
        PApplet.main(appletArgs);
    }

}
