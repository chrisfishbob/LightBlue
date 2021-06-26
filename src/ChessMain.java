import processing.core.PApplet;
import processing.core.PImage;


public class  ChessMain extends PApplet {
    private final int windowWidth = 800;
    private final int windowHeight = 800;
    private PImage king;

    public void settings(){
        size(windowWidth, windowHeight);
        king = loadImage("BlackKing2.png");
    }


    public void draw(){

        background(64);
        drawBoard();
        image(king, 0, 0, 100, 100 );
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

    public static void main(String[] args){
        String[] appletArgs = new String[] {"ChessMain"};
        PApplet.main(appletArgs);
    }
}
