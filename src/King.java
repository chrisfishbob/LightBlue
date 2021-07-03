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

    public String toString(){
        if (getColor().equals("white")){
            return "K at " + getLocation();
        }
        else{
            return "k at " + getLocation();
        }
    }
}
