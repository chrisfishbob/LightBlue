import processing.core.PApplet;
import processing.core.PImage;
import java.lang.*;
import java.util.HashMap;


public class Corsica extends PApplet {
    Piece[] board;
    private final int windowWidth = 800;
    private final int windowHeight = 800;
    public static PImage blackKing;
    public static PImage whiteKing;
    public static PImage blackQueen;
    public static PImage whiteQueen;
    public static PImage blackRook;
    public static PImage whiteRook;
    public static PImage blackBishop;
    public static PImage whiteBishop;
    public static PImage blackKnight;
    public static PImage whiteKnight;
    public static PImage blackPawn;
    public static PImage whitePawn;

    public void settings(){
        size(windowWidth, windowHeight);
    }

    public void setup(){
        loadImages();
        board = new Piece[64];
//        putPiece(new Knight("white", 0));
//        putPiece(new Pawn ("black", 3));
//        removePieceAt(0);
        loadFromFen("rRBpPQnN");

    }


    public void draw(){
        // Main loop of the program
        background(64);
        drawBoard();

        for (Piece piece: board){
            if (piece != null) {
                int row = piece.getLocation() / 8;
                int column = piece.getLocation() % 8;
                int squareSize = windowWidth / 8;

                image(piece.getImage(), column * squareSize, row * squareSize, squareSize, squareSize);
            }
        }
    }

    public void drawBoard(){
        // Called for every frame, this method draws an empty board.

        noStroke();

        for (int file = 0; file < 8; file++){
            for (int rank = 0; rank < 8; rank++){
                boolean isLightSquare = (file + rank) % 2 != 0;

                if (!isLightSquare){
                    fill(238, 237, 213);
                }
                else{
                    fill(124, 148, 93);
                }

                rect(rank * (float) (windowWidth / 8),
                        file * (float) (windowHeight / 8), (float) windowWidth / 8, (float) windowHeight / 8);
            }
        }
    }

    public void loadFromFen(String fen){
        int location = 0;
        HashMap<Character, Piece> pieceHashMap = generatePieceHashMap();

        for (char chr : fen.toCharArray()){
            if (Character.isLetter(chr)){
                Piece piece = pieceHashMap.get(chr);
                piece.setLocation(location);
                putPiece(piece);
            }

                location ++;
        }
    }


    public void putPiece(Piece piece){
        board[piece.getLocation()] = piece;
    }

    public void removePiece(Piece piece){
        board[piece.getLocation()] = null;
    }

    public void removePieceAt(int location){
        board[location] = null;
    }

    public HashMap<Character, Piece> generatePieceHashMap(){
        HashMap<Character, Piece> pieceHashMap = new HashMap<>();
        pieceHashMap.put('r', new Rook("black", 0));
        pieceHashMap.put('b', new Bishop("black", 0));
        pieceHashMap.put('R', new Rook("white", 0));
        pieceHashMap.put('B', new Bishop("white", 0));
        pieceHashMap.put('p', new Pawn("black", 0));
        pieceHashMap.put('P', new Pawn("white", 0));
        pieceHashMap.put('k', new King("black", 0));
        pieceHashMap.put('K', new King("white", 0));
        pieceHashMap.put('q', new Queen("black", 0 ));
        pieceHashMap.put('Q', new Queen("white", 0 ));
        pieceHashMap.put('n', new Knight("black", 0 ));
        pieceHashMap.put('N', new Knight("white", 0 ));

        return pieceHashMap;
    }

    public void loadImages(){
        // Called by setup method. This method loads all the images used

        blackKing = loadImage("BlackKing.png");
        whiteKing = loadImage("WhiteKing.png");
        blackQueen = loadImage("BlackQueen.png");
        whiteQueen = loadImage("WhiteQueen.png");
        blackRook = loadImage("BlackRook.png");
        whiteRook = loadImage("WhiteRook.png");
        blackBishop = loadImage("BlackBishop.png");
        whiteBishop = loadImage("WhiteBishop.png");
        blackKnight = loadImage("BlackKnight.png");
        whiteKnight = loadImage("WhiteKnight.png");
        blackPawn = loadImage("BlackPawn.png");
        whitePawn = loadImage("WhitePawn.png");
    }


    public static void main(String[] args){
        String[] appletArgs = new String[] {"Corsica"};
        PApplet.main(appletArgs);
    }
}