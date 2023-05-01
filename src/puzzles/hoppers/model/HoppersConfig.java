package puzzles.hoppers.model;

import puzzles.common.Coordinates;
import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * @author Kevin Huang
 */

// TODO: implement your HoppersConfig for the common solver

public class HoppersConfig implements Configuration{

    public static final String GREEN_FROG = "G"; // make all of these private

    public static final String RED_FROG = "R";

    public static final String VALID_SPACE = ".";
    public static int numberOfRow;
    public static int numberOfCol;

    public Coordinates coordinates;
    private String[][] grid;
    public String filename;


    public HoppersConfig(String filename) throws IOException { // happens when pgrm cannot read a file
        this.filename = filename;
        try (BufferedReader in = new BufferedReader(new FileReader(filename))) {
            String line = in.readLine();
            String[] fields = line.split("\\s+");
            numberOfRow = Integer.parseInt(fields[0]);
            numberOfCol = Integer.parseInt(fields[1]);
            grid = new String[numberOfRow][numberOfCol];
            for (int i = 0; i < numberOfRow && i< numberOfCol; i++) {
                line = in.readLine();
                String[] dimensionFields = line.split("\\s+");
                grid[i] = dimensionFields;
            }
        }
    }

    /**
     * deep copy of class
     * @param other other instance of HoppersConfig
     * @param currentRow the current row
     * @param currentCol the current colum
     * @param destinationRow the row that is selected
     * @param destinationCol the colum that is selected
     */
    public HoppersConfig(HoppersConfig other, int currentRow, int currentCol, int destinationRow, int destinationCol){
        this.coordinates = new Coordinates(currentRow, currentCol);
        this.grid = new String[numberOfRow][numberOfCol];
        for (int row = 0; row<numberOfRow; row++) {
            for (int col = 0; col<numberOfCol; col++) {
                grid[row][col] = other.grid[row][col]; // assigns new row and colum
            }
        }

        grid[destinationRow][destinationCol] = grid[currentRow][currentCol]; // move from left to right
        grid[currentRow][currentCol] = VALID_SPACE; // original space is now valid
        grid[(destinationRow+currentRow)/2][(destinationCol+currentCol)/2] = VALID_SPACE;


    }

    /**
     * if configuration is a solution
     * @return true if red frog is left, false otherwise
     */

    @Override
    public boolean isSolution() {
        boolean isRedFrog = false;
        for (int x = 0; x < numberOfRow; x++) { //curserow
            for (int y = 0; y < numberOfCol; y++) { // cursecol
                if (grid[x][y].equals(GREEN_FROG)){
                    return false;
                }
                if (grid[x][y].equals(RED_FROG)){
                    isRedFrog = true;
                }
            }
        }
        return isRedFrog;
    }

    /**
     *
     * @return the grid that the program tests
     */

    public String[][] getGrid(){
        return grid;
    }

    /**
     * gets neighboring frogs and checks conditions amongst 8 directions
     * @return the neighbors after every move
     */
    @Override
    public Collection<Configuration> getNeighbors() {
        Collection<Configuration>neighbors = new LinkedList<>();
        // get the moves of the next board
        for (int x = 0; x < numberOfRow; x++) {
            for (int y = 0; y < numberOfCol; y++) {

                if (grid[x][y].equals(GREEN_FROG) || grid[x][y].equals(RED_FROG)){

                    if (x + 4 < numberOfRow && grid[x + 2][y].equals(GREEN_FROG)) { // south
                        if (x + 4 < numberOfRow && grid[x + 4][y].equals(VALID_SPACE)){
                            neighbors.add(new HoppersConfig(this, x, y, x+4, y));

                        }


                    } if (y + 4 < numberOfCol && grid[x][y + 2].equals(GREEN_FROG)) { // east
                        if (y + 4 < numberOfCol && grid[x][y + 4].equals(VALID_SPACE)){
                            neighbors.add(new HoppersConfig(this, x, y, x, y+4));

                        }


                    } if (x - 4 >= 0 && grid[x - 2][y].equals(GREEN_FROG)) { // north
                        if (x - 4 >= 0 && grid[x - 4][y].equals(VALID_SPACE)){
                            neighbors.add(new HoppersConfig(this, x, y, x-4, y));

                        }


                    } if (y - 4 >=0 && grid[x][y - 2].equals(GREEN_FROG)) { // west
                        if (y - 4 >=0 && grid[x][y - 4].equals(VALID_SPACE)){
                            neighbors.add(new HoppersConfig(this, x, y, x, y-4));

                        }


                    } if (x + 2 < numberOfRow && y + 2 < numberOfCol && grid[x + 1][y + 1].equals(GREEN_FROG)) { // southeast
                        if (x + 2 < numberOfRow && y + 2 < numberOfCol && grid[x + 2][y + 2].equals(VALID_SPACE)){
                            neighbors.add(new HoppersConfig(this, x, y, x+2, y+2));

                        }


                    } if (x + 2 < numberOfRow && y - 2 >=0 && grid[x + 1][y - 1].equals(GREEN_FROG)) { // southwest
                        if (x + 2 < numberOfRow && y - 2 < numberOfCol && grid[x + 2][y - 2].equals(VALID_SPACE)){
                            neighbors.add(new HoppersConfig(this, x, y, x+2, y-2));

                        }


                    } if (x - 2 >= 0 && y + 2 < numberOfCol && grid[x - 1][y + 1].equals(GREEN_FROG)) { // northeast
                        if (x - 2 < numberOfRow && y + 2 < numberOfCol && grid[x - 2][y + 2].equals(VALID_SPACE)){
                            neighbors.add(new HoppersConfig(this, x, y, x-2, y+2));

                        }


                    } if (x - 2 >= 0 && y - 2 >=0 && grid[x - 1][y - 1].equals(GREEN_FROG)) { // northwest
                        if (x - 2 < numberOfRow && y - 2 < numberOfCol && grid[x - 2][y - 2].equals(VALID_SPACE)){
                            neighbors.add(new HoppersConfig(this, x, y, x-2, y-2));

                        }
                    }
                }
            }
        }
        return neighbors;
    }

    /**
     * checks if the range is within the board
     * @param coordinates the specified coordinates
     * @return true if less than the coordinates, false otherwise
     */
    public boolean isValidMove(Coordinates coordinates){
        if (coordinates.col()<numberOfCol && coordinates.row()<numberOfRow ){ // >
            return true;
        }
        return false;
    }

    /**
     * if the space is empty or contains "."
     * @param coordinates coordinates that it checks
     * @return true if valid, false otherwise
     */

    public boolean isvalidSpace(Coordinates coordinates) {
     return grid[coordinates.row()][coordinates.col()].equals(VALID_SPACE);
    }

    /**
     *
     * @param start
     * @param end
     * @return
     */
    public boolean jumpOverGreenFrog(Coordinates start, Coordinates end){
        return grid[(start.row()+ end.row())/2][(start.col()+end.col())/2].equals(GREEN_FROG);
    }

    public boolean southeast(Coordinates cod){
        if (cod.row()<numberOfRow && cod.col()<numberOfCol && !grid[cod.row()+1][cod.col()+1].equals(GREEN_FROG) && (grid[cod.row()+1][cod.col()+1].equals(VALID_SPACE)
                || grid[cod.row()+2][cod.col()+2].equals(VALID_SPACE)
                || grid[cod.row()+3][cod.col()+3].equals(VALID_SPACE)
                || grid[cod.row()+4][cod.col()+4].equals(VALID_SPACE)
                || grid[cod.row()+5][cod.col()+5].equals(VALID_SPACE)
                || grid[cod.row()+6][cod.col()+6].equals(VALID_SPACE)
                || grid[cod.row()+7][cod.col()+7].equals(VALID_SPACE)
                || grid[cod.row()+8][cod.col()+8].equals(VALID_SPACE)
                || grid[cod.row()+9][cod.col()+9].equals(VALID_SPACE)
                || grid[cod.row()+10][cod.col()+10].equals(VALID_SPACE)
                || grid[cod.row()+11][cod.col()+11].equals(VALID_SPACE))){

            return true;
        }
        return false;
    }

    /**
     * if current object equals the other object instanceof in class
     * @param other the other instanceof
     * @return true if otherinstanceof, false otherwise
     */

        @Override
    public boolean equals(Object other) {
        boolean result = true;
        if (other instanceof HoppersConfig h){
            result = Arrays.deepEquals(grid, h.getGrid());
        }
        return result;
    }

    /**
     * generates hashcode for processing configurations
     * @return hashcode
     */
    @Override
    public int hashCode() {
        return Arrays.deepHashCode(grid);
    }

    /**
     * converts output/results to string
     * @return a string representation
     */
    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        for (int row = 0; row<numberOfRow; row++ ){
            result.append("\n");
            for (int col = 0; col<numberOfCol; col++){

                result.append(grid[row][col] + " ");
            }
        }
        return result + "";

    }
}
