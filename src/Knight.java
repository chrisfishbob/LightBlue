public class Knight extends Piece{

    public Knight(String color, int location) {
        super(color, location);

        if (color.equals("white")){
            setImage(Corsica.whiteKnight);
        }
        else{
            setImage(Corsica.blackKnight);
        }
    }

}