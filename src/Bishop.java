public class Bishop extends Piece{

    public Bishop(String color, int location) {
        super(color, location);

        if (color.equals("white")){
            setImage(Corsica.whiteBishop);
        }
        else{
            setImage(Corsica.blackBishop);
        }
    }
}