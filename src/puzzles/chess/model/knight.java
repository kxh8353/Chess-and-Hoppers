package puzzles.chess.model;

import puzzles.common.Coordinates;

import java.util.ArrayList;

public class knight  extends chessPiece{
    knight(Coordinates loc, String cr){
        super(loc, cr);
    }
    public knight(chessPiece other){//Deep copy constructor
        super(other);
    }
    public chessPiece clone(){
        return new knight(this);
    }
    @Override
    public boolean equals(Object other){
        if(other instanceof knight){
            knight otherPiece = (knight)other;
            if(location.equals(otherPiece.location)){
                return true;
            }
        }
        return false;
    }
    /**
     * "Move" a single space in all L shaped directions. See chessPiece for more.
     * @param boardRef board reference
     * */
    public ArrayList<Coordinates> moveAndFindPiece(String[][] boardRef){
        int minRowAndCol = 0;
        int maxRow = boardRef.length;
        int maxCol = boardRef[0].length;
        Coordinates NORTHRIGHTL = new Coordinates(-2, 1);
        Coordinates NORTHLEFTL = new Coordinates(-2, -1);

        Coordinates SOUTHRIGHTL = new Coordinates(2, 1);
        Coordinates SOUTHLEFTL = new Coordinates(2, -1);

        Coordinates EASTRIGHTL = new Coordinates(1, 2);
        Coordinates EASTLEFTL = new Coordinates(-1, 2);

        Coordinates WESTRIGHTL = new Coordinates(-1, -2);
        Coordinates WESTLEFTL = new Coordinates(1, -2);
        Coordinates[] directions = {
                NORTHRIGHTL, NORTHLEFTL,
                SOUTHRIGHTL, SOUTHLEFTL,
                EASTRIGHTL, EASTLEFTL,
                WESTRIGHTL, WESTLEFTL
        };
        ArrayList<Coordinates> validMoves = new ArrayList<>();
        for(int i = 0; i < directions.length; i++) {
            int currentRow = location.row() + directions[i].row();
            int currentCol = location.col() + directions[i].col();
            if (maxRow > currentRow && maxCol > currentCol && minRowAndCol <= currentRow && minRowAndCol <= currentCol) {
                if (!boardRef[currentRow][currentCol].equals(chessPiece.EMPTY)) {
                    validMoves.add(new Coordinates(currentRow, currentCol));
                }
            }
        }
        return validMoves;
    }
}
