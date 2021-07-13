import java.util.ArrayList;

public class Knight extends Piece{
    private ArrayList<Move> moves;

    public Knight(String color, int location) {
        super(color, location, 3);

        if (color.equals("white")){
            setImage(LightBlueMain.getPieceImage("whiteKnight"));
        }
        else{
            setImage(LightBlueMain.getPieceImage("blackKnight"));
        }
    }

    public Knight(Knight other){
        super(other.getColor(), other.getLocation(), 5);

        if (other.getColor().equals("white")){
            setImage(LightBlueMain.getPieceImage("whiteKnight"));
        }
        else{
            setImage(LightBlueMain.getPieceImage("blackKnight"));
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