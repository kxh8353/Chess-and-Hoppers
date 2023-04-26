package puzzles.chess.model;

import puzzles.common.Coordinates;

import java.util.ArrayList;

public class rook  extends chessPiece {
    rook(Coordinates loc, String cr){
        super(loc, cr);
    }
    public rook(chessPiece other){//Deep copy constructor
        super(other);
    }
    public chessPiece clone(){
        return new rook(this);
    }
    @Override
    public boolean equals(Object other){
        if(other instanceof rook){
            rook otherPiece = (rook)other;
            if(location.equals(otherPiece.location)){
                return true;
            }
        }
        return false;
    }
    /**
     * "Move" a single space in all lines. Stops in respective direction when a piece is found. See chessPiece for more.
     * @param boardRef board reference
     * */
    public ArrayList<Coordinates> moveAndFindPiece(String[][] boardRef){
        int minRowAndCol = 0;
        int maxRow = boardRef.length;
        int maxCol = boardRef[0].length;
        Coordinates NORTH = new Coordinates(-1, 0);
        Coordinates SOUTH = new Coordinates(1, 0);
        Coordinates EAST = new Coordinates(0, 1);
        Coordinates WEST = new Coordinates(0, -1);
        Coordinates[] directions = {NORTH, SOUTH, EAST, WEST};
        ArrayList<Coordinates> validMoves = new ArrayList<>();
        for(int i = 0; i < directions.length; i++){
            int currentRow = location.row() + directions[i].row();
            int currentCol = location.col() + directions[i].col();
            while(maxRow > currentRow  && maxCol > currentCol && minRowAndCol <= currentRow && minRowAndCol <= currentCol){
                if(!boardRef[currentRow][currentCol].equals(chessPiece.EMPTY)){
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
