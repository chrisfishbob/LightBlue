import processing.core.PImage;

public class Queen extends Piece{
    private final PImage image;

    public Queen(String color, int location) {
        super(color, location);

        if (color.equals("white")){
            image = Corsica.whiteQueen;
        }
        else{
            image = Corsica.blackQueen;
        }
    }

    public PImage getImage() {
        return image;
    }
}
