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

    public ArrayList<Move> generateMoves(){
        ArrayList<Move> moves = new ArrayList<>();
        int boardIndex = getLocation();
        int rank = boardIndex / 8;
        int file = boardIndex % 8;
        int targetSquare;

        if (isInBounds(rank - 2, file - 1)){
            targetSquare = (rank - 2) * 8 + (file - 1);
            moves.add(new Move(boardIndex, targetSquare));
            Board.otherHighlightedSquares.add(targetSquare);
        }

        if (isInBounds(rank - 2, file + 1)){
            targetSquare = (rank - 2) * 8 + (file + 1);
            moves.add(new Move(boardIndex, targetSquare));
            Board.otherHighlightedSquares.add(targetSquare);
        }

        if (isInBounds(rank - 1, file - 2)){
            targetSquare = (rank - 1) * 8 + (file - 2);
            moves.add(new Move(boardIndex, targetSquare));
            Board.otherHighlightedSquares.add(targetSquare);
        }

        if (isInBounds(rank - 1, file + 2)){
            targetSquare = (rank - 1) * 8 + (file + 2);
            moves.add(new Move(boardIndex, targetSquare));
            Board.otherHighlightedSquares.add(targetSquare);
        }

        if (isInBounds(rank + 1, file - 2)){
            targetSquare = (rank + 1) * 8 + (file - 2);
            moves.add(new Move(boardIndex, targetSquare));
            Board.otherHighlightedSquares.add(targetSquare);
        }

        if (isInBounds(rank + 1, file + 2)){
            targetSquare = (rank + 1) * 8 + (file + 2);
            moves.add(new Move(boardIndex, targetSquare));
            Board.otherHighlightedSquares.add(targetSquare);
        }

        if (isInBounds(rank + 2, file - 1)){
            targetSquare = (rank + 2) * 8 + (file - 1);
            moves.add(new Move(boardIndex, targetSquare));
            Board.otherHighlightedSquares.add(targetSquare);
        }

        if (isInBounds(rank + 2, file + 1)){
            targetSquare = (rank + 2) * 8 + (file + 1);
            moves.add(new Move(boardIndex, targetSquare));
            Board.otherHighlightedSquares.add(targetSquare);
        }


        return moves;
    }

    public boolean isInBounds(int rank, int file){
        return rank >= 0 && rank < 8 && file >= 0 && file < 8;
    }

}