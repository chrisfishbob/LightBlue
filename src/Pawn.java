import processing.core.PImage;

public class Pawn extends Piece{
    private final PImage image;

    public Pawn(String color, int location) {
        super(color, location);

        if (color.equals("white")){
            image = ChessMain.whitePawn;
        }
        else{
            image = ChessMain.blackPawn;
        }
    }

    public PImage getImage() {
        return image;
    }
}