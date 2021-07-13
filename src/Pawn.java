import java.util.ArrayList;

public class Pawn extends Piece{
    ArrayList<Move> moves;

    public Pawn(String color, int location) {
        super(color, location, 1);

        if (color.equals("white")){
            setImage(LightBlueMain.getPieceImage("whitePawn"));
        }
        else{
            setImage(LightBlueMain.getPieceImage("blackPawn"));
        }
    }

    public Pawn(Pawn other){
        super(other.getColor(), other.getLocation(), 5);

        if (other.getColor().equals("white")){
            setImage(LightBlueMain.getPieceImage("whitePawn"));
        }
        else{
            setImage(LightBlueMain.getPieceImage("blackPawn"));
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