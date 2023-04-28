package puzzles.chess.model;

import puzzles.common.Coordinates;
import java.util.ArrayList;
/**
 * bishop.java
 * Author:jw5250
 *
 * */
public class bishop extends chessPiece {
    bishop(Coordinates loc, String cr){
        super(loc, cr);
    }
    public bishop(chessPiece other){//Deep copy constructor
        super(other);
    }
    public chessPiece clone(){
        return new bishop(this);
    }
    @Override
    public boolean equals(Object other){
        if(other instanceof bishop){
            bishop otherPiece = (bishop)other;
            if(location.equals(otherPiece.location)){
                return true;
            }
        }
        return false;
    }
    /**
     * "Move" a single space in all diagonals. Stops in respective direction when a piece is found. See chessPiece for more.
     * @param boardRef board reference
     * */
    public ArrayList<Coordinates> moveAndFindPiece(String[][] boardRef){
        int minRowAndCol = 0;
        int maxRow = boardRef.length;
        int maxCol = boardRef[0].length;
        Coordinates NORTHEAST = new Coordinates(-1, 1);
        Coordinates NORTHWEST = new Coordinates(-1, -1);
        Coordinates SOUTHEAST = new Coordinates(1, 1);
        Coordinates SOUTHWEST = new Coordinates(1, -1);
        Coordinates[] directions = {NORTHEAST, SOUTHEAST, SOUTHWEST, NORTHWEST};
        ArrayList<Coordinates> validMoves = new ArrayList<>();
        for(int i = 0; i < directions.length; i++){
            int currentRow = location.row() + directions[i].row();
            int currentCol = location.col() + directions[i].col();
            while( ( (maxRow > currentRow) && (maxCol > currentCol) ) &&
                    ( (minRowAndCol <= currentRow) && (minRowAndCol <= currentCol) ) ){

                if(!boardRef[currentRow][currentCol].equals(chessPiece.EMPTY)){
                    //System.out.println( "Piece found: " + boardRef[currentRow][currentCol]);
                    validMoves.add(new Coordinates(currentRow, currentCol));
                    break;
                }
                currentRow += directions[i].row();
                currentCol += directions[i].col();
            }
        }
        return validMoves;
    }
}
