import processing.core.PImage;

public class Bishop extends Piece{
    private final PImage image;

    public Bishop(String color, int location) {
        super(color, location);

        if (color.equals("white")){
            image = Corsica.whiteBishop;
        }
        else{
            image = Corsica.blackBishop;
        }
    }

    public PImage getImage() {
        return image;
    }
}