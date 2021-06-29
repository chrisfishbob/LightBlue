import processing.core.PImage;

public abstract class Piece {
    private final String color;
    private int location;
    private PImage image;


    public Piece(String color, int location){
        this.color = color;
        this.location = location;
    }

    public Piece(Piece clone){
        this.color = clone.color;
        this.location = clone.location;
        this.image = clone.image;
    }

    public int getLocation(){
        return location;
    }

    public void setLocation(int location){
        this.location = location;
    }

    public void setImage(PImage image){
        this.image = image;
    }

    public PImage getImage(){
        return this.image;
    }



    public String toString(){
        return "Piece at " + location;
    }


}
