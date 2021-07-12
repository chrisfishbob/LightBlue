import processing.core.PImage;

import java.util.ArrayList;

public abstract class Piece {
    private final String color;
    private int location;
    private PImage image;
    private boolean isSelected = false;
    private double value;


    public Piece(String color, int location, double value){
        this.color = color;
        this.location = location;
        this.value = value;
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

    public double getValue(){
        return this.value;
    }

    public abstract String toString();

    public abstract String getPieceChar();

    public void setSelected(boolean bool){
        this.isSelected = bool;
    }

    public boolean isSelected(){
        return this.isSelected;
    }
}
