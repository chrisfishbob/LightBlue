import java.util.ArrayList;

public class Rook extends Piece{
    ArrayList<Move> moves = new ArrayList<>();

    public Rook(String color, int location) {
        super(color, location, 5);

        if (color.equals("white")){
            setImage(LightBlueMain.whiteRook);
        }
        else{
            setImage(LightBlueMain.blackRook);
        }
    }

    public String toString(){
        if (getColor().equals("white")){
            return "R at " + getLocation();
        }
        else{
            return "r at " + getLocation();
        }
    }

    public String getPieceChar(){
        if (getColor().equals("white")){
            return "R";
        }
        else{
            return "r";
        }
    }



}