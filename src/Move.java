public class Move {
    private final int startSquare;
    private final int targetSquare;
    private boolean hasSpecialMoveFlag;
    private String specialFlagKind;

    public Move(int startSquare, int targetSquare){
        this.startSquare = startSquare;
        this.targetSquare = targetSquare;
        this.hasSpecialMoveFlag = false;
    }

    public Move(int startSquare, int targetSquare, String specialFlagKind){
        this.startSquare = startSquare;
        this.targetSquare = targetSquare;
        this.hasSpecialMoveFlag = true;
        this.specialFlagKind = specialFlagKind;
    }

    public String toString(){
        return "Start: " + startSquare + "  Target: " + targetSquare;
    }


    public int getStartSquare() {
        return startSquare;
    }


    public int getTargetSquare(){
        return targetSquare;
    }

    public boolean isSpecialMove() {
        return hasSpecialMoveFlag;
    }

    public String getSpecialFlagKind(){
        return this.specialFlagKind;
    }
}
