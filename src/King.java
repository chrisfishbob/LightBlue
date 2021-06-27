import processing.core.PImage;

public class King extends Piece{
    private final PImage image;

    public King(String color, int location) {
        super(color, location);

        if (color.equals("white")){
            image = ChessMain.whiteKing;
        }
        else{
            image = ChessMain.blackKing;
        }
    }

    public PImage getImage(){
        return this.image;
    }
}
