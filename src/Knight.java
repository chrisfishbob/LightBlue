import java.util.ArrayList;

public class Knight extends Piece{
    private ArrayList<Move> moves;

    public Knight(String color, int location) {
        super(color, location, 3);

        if (color.equals("white")){
            setImage(LightBlueMain.whiteKnight);
        }
        else{
            setImage(LightBlueMain.blackKnight);
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