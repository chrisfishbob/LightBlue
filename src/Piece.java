import processing.core.PImage;

public abstract class Piece {
    private final String color;
    private int location;
    private PImage image;


    public Piece(String color, int location){
        this.color = color;
        this.location = location;
    }

    public int getLocation(){
        return location;
    }

    public void setImage(PImage image){
        this.image = image;
    }

    public PImage getImage(){
        return this.image;
    }


}
