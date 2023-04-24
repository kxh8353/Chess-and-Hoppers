package puzzles.chess.model;

import puzzles.common.Coordinates;

import java.util.ArrayList;

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
