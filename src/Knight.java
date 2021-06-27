import processing.core.PImage;

public class Knight extends Piece{
    private final PImage image;

    public Knight(String color, int location) {
        super(color, location);

        if (color.equals("white")){
            image = Corsica.whiteKnight;
        }
        else{
            image = Corsica.blackKnight;
        }
    }

    public PImage getImage() {
        return image;
    }
}