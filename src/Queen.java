public class Queen extends Piece{

    public Queen(String color, int location) {
        super(color, location);

        if (color.equals("white")){
            setImage(Corsica.whiteQueen);
        }
        else{
            setImage(Corsica.blackQueen);
        }
    }
}
