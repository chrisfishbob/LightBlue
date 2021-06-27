import processing.core.PApplet;
import processing.core.PImage;

public class Piece {
    private final String kind;
    private final String color;
    private int location;
    private PImage pieceImage;

    public Piece(String kind, String color, int location){
        this.kind = kind;
        this.color = color;
        this.location = location;

        switch (kind){
            case "blackKing":
                this.pieceImage = ChessMain.blackKing;
                break;
            case "whiteKing":
                this.pieceImage = ChessMain.whiteKing;
                break;

        }
    }

    public String getKind(){
        return this.kind;
    }

    public String getColor(){
        return this.color;
    }

    public int getLocation(){
        return this.location;
    }

    public PImage getPieceImage() {
        return pieceImage;
    }
}
