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

    public String toString(){
        if (getColor().equals("white")){
            return "N at " + getLocation();
        }
        else{
            return "n at " + getLocation();
        }
    }

    public String getPieceChar(){
        if (getColor().equals("white")){
            return "N";
        }
        else{
            return "n";
        }
    }

}