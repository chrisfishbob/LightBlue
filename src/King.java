import java.util.ArrayList;

public class King extends Piece{
    ArrayList<Move> moves = new ArrayList<>();

    public King(String color, int location) {
        super(color, location, Double.POSITIVE_INFINITY);

        if (color.equals("white")){
            setImage(LightBlueMain.getPieceImage("whiteKing"));
        }
        else{
            setImage(LightBlueMain.getPieceImage("blackKing"));
        }
    }

    public King(King other){
        super(other.getColor(), other.getLocation(), 5);

        if (other.getColor().equals("white")){
            setImage(LightBlueMain.getPieceImage("whiteKing"));
        }
        else{
            setImage(LightBlueMain.getPieceImage("blackKing"));
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

    public String getPieceChar(){
        if (getColor().equals("white")){
            return "K";
        }
        else{
            return "k";
        }
    }



}
