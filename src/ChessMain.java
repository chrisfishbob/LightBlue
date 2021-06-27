import processing.core.PApplet;
import processing.core.PImage;


public class ChessMain extends PApplet {
    private final int windowWidth = 800;
    private final int windowHeight = 800;
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

    public void settings(){
        size(windowWidth, windowHeight);
        loadImages();

    }


    public void draw(){

        background(64);
        drawBoard();
        King king = new King("white", 0);
        image(king.getImage(), 0, 0, 100, 100 );
    }

    public void drawBoard(){
        noStroke();

        for (int file = 0; file < 8; file++){
            for (int rank = 0; rank < 8; rank++){
                boolean isLightSquare = (file + rank) % 2 != 0;

                if (!isLightSquare){
                    fill(238, 237, 213);
                }
                else{
                    fill(124, 148, 93);
                }

                rect(rank * (float) (windowWidth / 8),
                        file * (float) (windowHeight / 8), (float) windowWidth / 8, (float) windowHeight / 8);
            }
        }
    }

    public void loadImages(){
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

    public static void main(String[] args){
        String[] appletArgs = new String[] {"ChessMain"};
        PApplet.main(appletArgs);

    }
}
