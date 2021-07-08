import java.util.ArrayList;
import java.util.HashMap;

public class MoveGenerator {
    public static HashMap<Integer, ArrayList<Move>> potentialLegalKnightMoveMap;

    public static void preGenerateKnightMoves(){
        // Pre-generates all the potential legal knight moves during set up.
        // Moves that turn out to be illegal during run time are filtered out
        // in the generateMoves method for the Knight object.

        HashMap<Integer, ArrayList<Move>> potentialLegalKnightMovesMap = new HashMap<>();
        int targetSquare;
        int[][] knightOffsets = {{-2, -1}, {-2, 1}, {-1, -2}, {-1, 2}, {1, - 2}, {1, 2}, {2, -1}, {2, 1}};

        // Loops through all the squares and calculates all the potentially legal knight moves
        for (int boardIndex = 0; boardIndex < 64; boardIndex++){
            int rank = boardIndex / 8;
            int file = boardIndex % 8;
            ArrayList<Move> potentialLegalKnightMoves = new ArrayList<>();

            // A perfectly placed knight has eight legal moves. Loop through all of them and check
            // if they are potentially legal (does not move out of bounds)
            for (int i = 0; i < 8; i ++){
                if (isInBounds(rank + knightOffsets[i][0], file + knightOffsets[i][1])){
                    targetSquare = (rank + knightOffsets[i][0]) * 8 + (file + knightOffsets[i][1]);
                    potentialLegalKnightMoves.add(new Move(boardIndex, targetSquare));
                }
            }

            potentialLegalKnightMovesMap.put(boardIndex, potentialLegalKnightMoves);
        }

       potentialLegalKnightMoveMap = potentialLegalKnightMovesMap;
    }

    public static boolean isInBounds(int rank, int file){
        return rank >= 0 && rank < 8 && file >= 0 && file < 8;
    }
}
