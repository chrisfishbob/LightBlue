import processing.core.PImage;

public abstract class Piece {
    private final String color;
    private int location;

    public Piece(String color, int location){
        this.color = color;
        this.location = location;
    }

    public abstract PImage getImage();
}
