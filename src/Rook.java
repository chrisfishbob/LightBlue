import java.util.ArrayList;

public class Rook extends Piece{


    public Rook(String color, int location) {
        super(color, location, 5);

        if (color.equals("white")){
            setImage(Board.whiteRook);
        }
        else{
            setImage(Board.blackRook);
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