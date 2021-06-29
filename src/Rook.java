public class Rook extends Piece{

    public Rook(String color, int location) {
        super(color, location);

        if (color.equals("white")){
            setImage(Corsica.whiteRook);
        }
        else{
            setImage(Corsica.blackRook);
        }
    }
}