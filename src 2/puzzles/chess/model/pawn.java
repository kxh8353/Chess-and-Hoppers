package puzzles.chess.model;

import puzzles.common.Coordinates;

import java.util.ArrayList;
/**
 * pawn.java
 * Author:jw5250
 *
 * */
public class pawn extends chessPiece{
    public pawn(Coordinates loc, String cr){
        super(loc, cr);
    }
    public pawn(chessPiece other){//Deep copy constructor
        super(other);
    }
    public chessPiece clone(){
        return new pawn(this);
    }
    @Override
    public boolean equals(Object other){
        if(other instanceof pawn){
            pawn otherPiece = (pawn)other;
            if(location.equals(otherPiece.location)){
                return true;
            }
        }
        return false;
    }
    /**
     * "Move" a single space in only two upper diagonal positions. See chessPiece for more.
     * @param boardRef board reference
     * */
    public ArrayList<Coordinates> moveAndFindPiece(String[][] boardRef) {
        //Assuming board is rectangular.
        int minRowAndCol = 0;
        int maxCol = boardRef[0].length;
        int newRow = location.row() - 1;
        int newLeftCol = location.col() - 1;
        int newRightCol = location.col() + 1;
        ArrayList<Coordinates> validMoves = new ArrayList<>();
        if (newRow < minRowAndCol) {
            return validMoves;
        }
        if (newLeftCol >= minRowAndCol && !boardRef[newRow][newLeftCol].equals(chessPiece.EMPTY)) {
            validMoves.add(new Coordinates(newRow, newLeftCol));
        }
        if (newRightCol < maxCol && !boardRef[newRow][newRightCol].equals(chessPiece.EMPTY)) {
            validMoves.add(new Coordinates(newRow, newRightCol));
        }
        return validMoves;
    }

}
