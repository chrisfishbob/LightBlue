public class Move {
    private final int startSquare;
    private final int targetSquare;
    private boolean hasSpecialMoveFlag;
    private String specialFlagKind;

    public Move(int startSquare, int targetSquare){
        this.startSquare = startSquare;
        this.targetSquare = targetSquare;
        hasSpecialMoveFlag = false;
    }

    public Move(int startSquare, int targetSquare, String specialFlagKind){
        this.startSquare = startSquare;
        this.targetSquare = targetSquare;
        hasSpecialMoveFlag = true;
        this.specialFlagKind = specialFlagKind;
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
