public class Pawn extends Piece{

    public Pawn(String color, int location) {
        super(color, location);

        if (color.equals("white")){
            setImage(Corsica.whitePawn);
        }
        else{
            setImage(Corsica.blackPawn);
        }
    }
}