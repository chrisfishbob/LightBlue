import processing.core.PImage;

public class King extends Piece{
    private final PImage image;

    public King(String color, int location) {
        super(color, location);

        if (color.equals("white")){
            image = Corsica.whiteKing;
        }
        else{
            image = Corsica.blackKing;
        }
    }

    public PImage getImage(){
        return this.image;
    }
}
