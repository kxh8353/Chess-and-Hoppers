package puzzles.chess.model;
import puzzles.common.Coordinates;
import java.util.ArrayList;
public abstract class chessPiece {

    private String sprite;
    protected Coordinates location;


    public static String EMPTY = ".";
    /**
     * Check each possible area where a piece can move. If there is such, return the coordinates of the target
     * piece found. Otherwise, return null.
     *
     * */
    public chessPiece(Coordinates loc, String cr){
        location = new Coordinates(loc.row(), loc.col());
        sprite = cr;
    }
    public chessPiece(chessPiece other){//Deep copy constructor
        location = new Coordinates(other.location.row(), other.location.col());
        sprite = new String(other.sprite);
    }
    /**
     * Deep copy a chessPiece object.
     * */
    public abstract chessPiece clone();
    /**
     * "Move" a piece and get all potential valid coordinates.
     * @param boardRef
     * */
    public abstract ArrayList<Coordinates> moveAndFindPiece(String[][] boardRef);
    public void changeCoordinates(Coordinates c){
        location = new Coordinates(c.row(), c.col());
    }
    public Coordinates getCoordinates(){
        return location;
    }
    public String getSprite(){
        return sprite;
    }
    @Override
    public boolean equals(Object other){
        if(other instanceof chessPiece){
            chessPiece otherPiece = (chessPiece)other;
            if(location.equals(otherPiece.location)
                    && sprite.equals(otherPiece.sprite)){
                return true;
            }
        }
        return false;
    }
    @Override
    public int hashCode(){
        return location.hashCode() + sprite.hashCode();
    }
    public String toString(){
        return sprite + location.toString();
    }
}
