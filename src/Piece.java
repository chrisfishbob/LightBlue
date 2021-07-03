import processing.core.PImage;

public abstract class Piece {
    private final String color;
    private int location;
    private PImage image;
    private boolean isSelected = false;


    public Piece(String color, int location){
        this.color = color;
        this.location = location;
    }

    public Piece(Piece clone){
        this.color = clone.color;
        this.location = clone.location;
        this.image = clone.image;
        this.isSelected = clone.isSelected;
    }

    public String getColor(){
        return this.color;
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

    public abstract String toString();

    public void setSelected(boolean bool){
        this.isSelected = bool;
    }

    public boolean isSelected(){
        return this.isSelected;
    }


}
