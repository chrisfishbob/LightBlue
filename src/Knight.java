import java.util.ArrayList;

public class Knight extends Piece{

    public Knight(String color, int location) {
        super(color, location, 3);

        if (color.equals("white")){
            setImage(Board.whiteKnight);
        }
        else{
            setImage(Board.blackKnight);
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

//    public ArrayList<Move> generateMoves(){
//        ArrayList<Move> moves = new ArrayList<>();
//
//
//    }

}