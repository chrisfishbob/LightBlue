import processing.core.PImage;

public class Rook extends Piece{
    private final PImage image;

    public Rook(String color, int location) {
        super(color, location);

        if (color.equals("white")){
            image = Corsica.whiteRook;
        }
        else{
            image = Corsica.blackRook;
        }
    }

    public PImage getImage() {
        return image;
    }
}