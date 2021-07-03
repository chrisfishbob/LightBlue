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

    public String toString(){
        if (getColor().equals("white")){
            return "B at " + getLocation();
        }
        else{
            return "b at " + getLocation();
        }
    }
}