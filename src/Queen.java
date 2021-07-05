public class Queen extends Piece{

    public Queen(String color, int location) {
        super(color, location, 9);

        if (color.equals("white")){
            setImage(Board.whiteQueen);
        }
        else{
            setImage(Board.blackQueen);
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
