package puzzles.chess.model;

import puzzles.common.solver.Configuration;
import puzzles.common.Coordinates;

import java.io.IOException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.Collection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;
/**
 * ChessConfig.java
 * Notes:
     * Problems:
         * Pieces should return a set of potential movements.
 * */
public class ChessConfig implements Configuration {
    private String[][] board;//The board.
    private HashMap<Coordinates, chessPiece> pieces;//The list of pieces.
    private enum spriteToClass{
        KNIGHT("N"),
        BISHOP("B"),
        QUEEN("Q"),
        KING("K"),
        ROOK("R"),
        PAWN("P");
        private final String spr;

        private spriteToClass(String s){
            spr = s;
        }
        public String toString(){
            return spr;
        }
    };
    //Seems to be working.
    public ChessConfig(String filename) throws IOException {
        Scanner fileReader = new Scanner(new FileReader(filename));
        String[] rawDimensionData = fileReader.nextLine().split(" ");
        pieces = new HashMap<>();
        int rows = Integer.parseInt(rawDimensionData[0]);
        int cols = Integer.parseInt(rawDimensionData[1]);
        board = new String[rows][cols];
        for(int i = 0; i < rows; i++){
            String[] spriteData = fileReader.nextLine().split(" ");
            for(int j = 0; j < cols; j++){
                board[i][j] = spriteData[j];
                addPotentialPiece(i, j, spriteData[j]);
            }
        }
        fileReader.close();
    }
    public void printData(){
        //System.out.println("List of pieces:");
        //System.out.println(pieces.toString());
        //System.out.println("Amount of pieces:" + pieces.size());
        //System.out.println("Board:");
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j++){
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }

    }
    private void addPotentialPiece(int row, int col, String s){
        if(s.equals(spriteToClass.KING.toString())){
            pieces.put(new Coordinates(row, col), new king(new Coordinates(row, col), s));
        }else if(s.equals(spriteToClass.QUEEN.toString())){
            pieces.put(new Coordinates(row, col), new queen(new Coordinates(row, col), s));
        }else if(s.equals(spriteToClass.PAWN.toString())){
            pieces.put(new Coordinates(row, col), new pawn(new Coordinates(row, col), s));
        }else if(s.equals(spriteToClass.KNIGHT.toString())){
            pieces.put(new Coordinates(row, col), new knight(new Coordinates(row, col), s));
        }else if(s.equals(spriteToClass.BISHOP.toString())){
            pieces.put(new Coordinates(row, col), new bishop(new Coordinates(row, col), s));
        }else if(s.equals(spriteToClass.ROOK.toString())){
            pieces.put(new Coordinates(row, col), new rook(new Coordinates(row, col), s));
        }
    }
    public ChessConfig(ChessConfig other){
        board = new String[other.board.length][other.board[0].length];
        for(int i = 0; i < board.length;i++){
            for(int j = 0; j < board.length;j++){
                board[i][j] = other.board[i][j];
            }
        }
        pieces = new HashMap<>();
        for(Coordinates coord : other.pieces.keySet()){
            pieces.put(coord, other.pieces.get(coord));
        }
    }
    public ChessConfig(String[][] theBoard, HashMap<Coordinates, chessPiece> thePieces){
        board = new String[theBoard.length][theBoard[0].length];
        for(int i = 0; i < board.length;i++){
            for(int j = 0; j < board[i].length;j++){
                board[i][j] = theBoard[i][j];
            }
        }

        pieces = new HashMap<>();
        for(Coordinates coord : thePieces.keySet()){
         pieces.put(coord, thePieces.get(coord));
        }
    }

    @Override
    public boolean isSolution() {
        //Solution is when only one piece remains.
        return (pieces.size() <= 1);
    }
    @Override
    public boolean equals(Object other){
        if(other instanceof ChessConfig){

            ChessConfig otherConfig = (ChessConfig)other;
            //System.out.println("Comparing:" + toString() + " and " + otherConfig.toString());
            for(int i = 0; i < board.length;i++){
                for(int j = 0; j < board[i].length;j++){
                    if(!board[i][j].equals(otherConfig.board[i][j])){
                        return false;
                    }
                }
            }
            return true;


        }
        return false;

    }
    @Override
    public int hashCode(){
        return Arrays.deepHashCode(board);
    }

    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j++){
                s.append(board[i][j] + " ");
            }
            s.append("\n");
        }
        return s.toString();
    }
    @Override
    public Collection<Configuration> getNeighbors() {
        ArrayList<Configuration> configs = new ArrayList<>();
        for(chessPiece p : pieces.values()){
            ArrayList<Coordinates> newCoords = p.moveAndFindPiece(board);
            if(newCoords.size() == 0){
                continue;
            }
            //System.out.println("Available moves:" + p + ": " + newCoords);
            for(int i = 0; i < newCoords.size();i++) {
                String[][] newBoard = new String[board.length][board[0].length];
                for(int j = 0; j < board.length; j++){
                    for(int k = 0; k < board[j].length;k++){
                        newBoard[j][k] = board[j][k];
                    }
                }
                newBoard[p.getCoordinates().row()][p.getCoordinates().col()] = chessPiece.EMPTY;
                newBoard[newCoords.get(i).row()][newCoords.get(i).col()] = p.getSprite();
                HashMap<Coordinates, chessPiece> newPieces = new HashMap<>();
                for (Coordinates c : pieces.keySet()) {
                    newPieces.put(c, pieces.get(c));
                }
                chessPiece newPiece = p.clone();
                newPiece.changeCoordinates(newCoords.get(i));//Get new piece's coordinates changed.
                newPieces.put(newCoords.get(i), newPiece);//Change new location of p.
                newPieces.remove(p.getCoordinates());
                ChessConfig newConfig = new ChessConfig(newBoard, newPieces);
                configs.add(newConfig);
            }
        }
        //System.out.println("Number of configurations:" + configs.size());
        return configs;
    }

    public static void main(String[] s){
        String l = "chess-9.txt";
        try {
            ChessConfig c = new ChessConfig(l);
            c.printData();
        }catch(IOException i){
            System.out.println("File not found:" + l);

        }
    }
}
