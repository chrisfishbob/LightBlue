public class Bishop extends Piece{

    public Bishop(String color, int location) {
        super(color, location, 3.25);

        if (color.equals("white")){
            setImage(Board.whiteBishop);
        }
        else{
            setImage(Board.blackBishop);
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