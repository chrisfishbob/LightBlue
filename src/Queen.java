import java.util.ArrayList;

public class Queen extends Piece{
    ArrayList<Move> moves;

    public Queen(String color, int location) {
        super(color, location, 9);

        if (color.equals("white")){
            setImage(LightBlueMain.getPieceImage("whiteQueen"));
        }
        else{
            setImage(LightBlueMain.getPieceImage("blackQueen"));
        }
    }

    public Queen(Queen other){
        super(other.getColor(), other.getLocation(), 5);

        if (other.getColor().equals("white")){
            setImage(LightBlueMain.getPieceImage("whiteQueen"));
        }
        else{
            setImage(LightBlueMain.getPieceImage("blackQueen"));
        }
    }


    public String toString(){
        if (getColor().equals("white")){
            return "Q at " + getLocation();
        }
        else{
            return "q at " + getLocation();
        }
    }


    public String getPieceChar(){
        if (getColor().equals("white")){
            return "Q";
        }
        else{
            return "q";
        }
    }

}
