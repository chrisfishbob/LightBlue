public class Pawn extends Piece{

    public Pawn(String color, int location) {
        super(color, location, 1);

        if (color.equals("white")){
            setImage(Corsica.whitePawn);
        }
        else{
            setImage(Corsica.blackPawn);
        }
    }

    public String toString(){
        if (getColor().equals("white")){
            return "P at " + getLocation();
        }
        else{
            return "p at " + getLocation();
        }
    }

    public String getPieceChar(){
        if (getColor().equals("white")){
            return "P";
        }
        else{
            return "p";
        }
    }
}