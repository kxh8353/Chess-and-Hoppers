package puzzles.hoppers.model;

import puzzles.common.Coordinates;
import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Collection;

// TODO: implement your HoppersConfig for the common solver

public class HoppersConfig implements Configuration{

    public static final String GREEN_FROG = "G";

    public static final String RED_FROG = "R";

    public static final String VALID_SPACE = ".";
    public int numberOfRow;
    public int numberOfCol;

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
    private HoppersConfig(HoppersConfig other, int currentRow, int currentCol, int destinationRow, int destinationCol){
        this.coordinates = new Coordinates(currentRow, currentCol);
        this.numberOfRow = other.numberOfRow; // current assigns to other instance of
        this.numberOfCol = other.numberOfCol;
        this.grid = new String[numberOfRow][numberOfCol];
        for (int col = 0; col<numberOfCol; col++) {
            for (int row = 0; row < numberOfRow; row++) {
                grid[row][col] = other.grid[row][col]; // assigns new row and colum
            }
        }

        grid[destinationRow][destinationCol] = grid[currentRow][currentCol]; // move from left to right
        grid[currentRow][currentCol] = VALID_SPACE; // original space is now valid
        grid[(destinationRow+currentRow)/2][(destinationCol+currentCol)/2] = VALID_SPACE;


    }

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


    public String[][] getGrid(){
        return grid;
    }
    @Override
    public Collection<Configuration> getNeighbors() {
        Collection<Configuration>neighbors = new LinkedList<>();
        // get the moves of the next board
        for (int x = 0; x < numberOfRow; x++) {
            for (int y = 0; y < numberOfCol; y++) {

                if (grid[x][y].equals(GREEN_FROG) || grid[x][y].equals(RED_FROG)){
                    Solver.totalconfigs++;

                    if (x + 4 < numberOfRow && grid[x + 2][y].equals(GREEN_FROG)) { // south
                        if (x + 4 < numberOfRow && grid[x + 4][y].equals(VALID_SPACE)){
                            neighbors.add(new HoppersConfig(this, x, y, x+4, y));
                            Solver.uniqueconfigs++;
                        }


                    } if (y + 4 < numberOfCol && grid[x][y + 2].equals(GREEN_FROG)) { // east
                        if (y + 4 < numberOfCol && grid[x][y + 4].equals(VALID_SPACE)){
                            neighbors.add(new HoppersConfig(this, x, y, x, y+4));
                            Solver.uniqueconfigs++;
                        }


                    } if (x - 4 >= 0 && grid[x - 2][y].equals(GREEN_FROG)) { // north
                        if (x - 4 >= 0 && grid[x - 4][y].equals(VALID_SPACE)){
                            neighbors.add(new HoppersConfig(this, x, y, x-4, y));
                            Solver.uniqueconfigs++;
                        }


                    } if (y - 4 >=0 && grid[x][y - 2].equals(GREEN_FROG)) { // west
                        if (y - 4 >=0 && grid[x][y - 4].equals(VALID_SPACE)){
                            neighbors.add(new HoppersConfig(this, x, y, x, y-4));
                            Solver.uniqueconfigs++;
                        }


                    } if (x + 2 < numberOfRow && y + 2 < numberOfCol && grid[x + 1][y + 1].equals(GREEN_FROG)) { // southeast
                        if (x + 2 < numberOfRow && y + 2 < numberOfCol && grid[x + 2][y + 2].equals(VALID_SPACE)){
                            neighbors.add(new HoppersConfig(this, x, y, x+2, y+2));
                            Solver.uniqueconfigs++;
                        }


                    } if (x + 2 < numberOfRow && y - 2 >=0 && grid[x + 1][y - 1].equals(GREEN_FROG)) { // southwest
                        if (x + 2 < numberOfRow && y - 2 < numberOfCol && grid[x + 2][y - 2].equals(VALID_SPACE)){
                            neighbors.add(new HoppersConfig(this, x, y, x+2, y-2));
                            Solver.uniqueconfigs++;
                        }


                    } if (x - 2 >= 0 && y + 2 < numberOfCol && grid[x - 1][y + 1].equals(GREEN_FROG)) { // northeast
                        if (x - 2 < numberOfRow && y + 2 < numberOfCol && grid[x - 2][y + 2].equals(VALID_SPACE)){
                            neighbors.add(new HoppersConfig(this, x, y, x-2, y+2));
                            Solver.uniqueconfigs++;
                        }


                    } if (x - 2 >= 0 && y - 2 >=0 && grid[x - 1][y - 1].equals(GREEN_FROG)) { // northwest
                        if (x - 2 < numberOfRow && y - 2 < numberOfCol && grid[x - 2][y - 2].equals(VALID_SPACE)){
                            neighbors.add(new HoppersConfig(this, x, y, x-2, y-2));
                            Solver.uniqueconfigs++;
                        }
                    }
                }
            }
        }
        return neighbors;
    }

    public boolean isValidMove(Coordinates coordinates){
        if (coordinates.col()<numberOfCol && coordinates.col()>0 && coordinates.row()<numberOfRow && coordinates.row()>0){ // >
            return true;
        }
        return false;
    }
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
