public class King extends Piece{

    public King(String color, int location) {
        super(color, location);

        if (color.equals("white")){
            setImage(Corsica.whiteKing);
        }
        else{
            setImage(Corsica.blackKing);
        }
    }
}
