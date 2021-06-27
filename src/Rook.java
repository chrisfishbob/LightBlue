import processing.core.PImage;

public class Rook extends Piece{
    private final PImage image;

    public Rook(String color, int location) {
        super(color, location);

        if (color.equals("white")){
            image = ChessMain.whiteRook;
        }
        else{
            image = ChessMain.blackRook;
        }
    }

    public PImage getImage() {
        return image;
    }
}