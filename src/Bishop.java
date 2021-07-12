import java.util.ArrayList;

public class Bishop extends Piece{
    ArrayList<Move> moves = new ArrayList<>();

    public Bishop(String color, int location) {
        super(color, location, 3.25);

        if (color.equals("white")){
            setImage(LightBlueMain.getPieceImage("whiteBishop"));
        }
        else{
            setImage(LightBlueMain.getPieceImage("blackBishop"));
        }
    }

    public String toString(){
        if (getColor().equals("white")){
            return "B at " + getLocation();
        }
        else{
            return "b at " + getLocation();
        }
    }

    public String getPieceChar(){
        if (getColor().equals("white")){
            return "B";
        }
        else{
            return "b";
        }
    }

}