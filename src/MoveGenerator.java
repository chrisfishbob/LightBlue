import java.util.ArrayList;
import java.util.HashMap;

public class MoveGenerator {
    private static HashMap<Integer, ArrayList<Move>> potentialLegalKnightMoveMap;
    private static int[] SlidingDirectionOffsets = {8, - 8, -1, 1, 7, -7, 9, -9};
    private static int[][] NumSquaresToEdge = new int[64][8];


    public static ArrayList<Move> generateAllMoves(String colorToGenerate) throws InterruptedException {
        ArrayList<Move> allMoves = new ArrayList<>();

        for (Piece piece : LightBlueMain.getBoard()){
            if (piece != null){
                if (piece.getColor().equals(colorToGenerate)){
                    piece.generateMoves();
                    allMoves.addAll(piece.getMoves());
                }
            }
        }

        return allMoves;
    }


    public static void PrecomputeMoveData(){
        for (int file = 0; file < 8; file ++){
            for (int rank = 0; rank < 8; rank ++){
                int numNorth = rank;
                int numSouth = 7 - rank;
                int numWest = file;
                int numEast = 7 - file;
                int numNorthWest = Math.min(numNorth, numWest);
                int numSouthEast = Math.min(numSouth, numEast);
                int numNorthEast = Math.min(numNorth, numEast);
                int numSouthWest = Math.min(numSouth, numWest);

                int squareIndex = rank * 8 + file;

                NumSquaresToEdge[squareIndex] = new int[]{
                        numSouth,
                        numNorth,
                        numWest,
                        numEast,
                        numSouthWest,
                        numNorthEast,
                        numSouthEast,
                        numNorthWest,
                        };
            }
        }
    }


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

    public static int[][] getNumSquaresToEdge(){
        return NumSquaresToEdge;
    }

    public static int[] getSlidingDirectionOffsets() {
        return SlidingDirectionOffsets;
    }

    public static HashMap<Integer, ArrayList<Move>> getPotentialLegalKnightMoveMap(){
        return potentialLegalKnightMoveMap;
    }
}
