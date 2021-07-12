import processing.core.PApplet;
import processing.core.PImage;
import java.lang.*;



public class LightBlueMain extends PApplet {
    private static Piece[] boardArray;
    private Board board;
    private MoveGenerator moveGenerator;


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


    private static int selectedSquare = getNullValue();
    private int releasedSquare = getNullValue();
    private boolean mouseIsHeldDown = false;

    public static boolean isMute = false;
    private final SoundProcessor soundProcessor = new SoundProcessor();


    public void settings(){
        size(windowWidth, windowHeight);
    }

    public void setup(){
        loadImages();
        moveGenerator = new MoveGenerator();
        board = new Board(this, soundProcessor, moveGenerator);

        boardArray = board.getBoardArray();
        board.loadFromFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");
        moveGenerator.preGenerateKnightMoves();
        moveGenerator.PrecomputeMoveData();
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
        targetedPieceBG = loadImage("outline.png");
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
                System.out.println("Released square is: " + releasedSquare);
                System.out.println("Selected square is: " + selectedSquare);
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


    public static int getNullValue(){
        return 99;
    }


    public static Piece[] getBoardArray(){
        return boardArray;
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
