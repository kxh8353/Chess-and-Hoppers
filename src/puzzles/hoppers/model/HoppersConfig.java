package puzzles.hoppers.model;

import puzzles.common.Coordinates;
import puzzles.common.solver.Configuration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Collection;

// TODO: implement your HoppersConfig for the common solver

public class HoppersConfig implements Configuration{
    private int numberOfRow;
    private int numberOfCol;
    private String[][] grid;




    public HoppersConfig(String filename) throws IOException {
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

        this.numberOfRow = other.numberOfRow;
        this.numberOfCol = other.numberOfCol;
        this.grid = new String[numberOfRow][numberOfCol];
        for (int col = 0; col<numberOfCol; col++){
            for (int row = 0; row<numberOfRow; row++){
                grid[row][col] = other.grid[row][col]; // assigns new row and colum
            }
        }
    }

    @Override
    public boolean isSolution() {
        boolean isRedFrog = false;
        for (int x = 0; x < numberOfRow; x++) { //curserow
            for (int y = 0; y < numberOfCol; y++) { // cursecol
                if (grid[x][y].equals("G")){
                    return false;
                }
                if (grid[x][y].equals("R")){
                    isRedFrog = true;
                }
            }
        }
        return isRedFrog;
    }


//   public Collection<Configuration> getCurrentNeighbors() throws IOException {
//        // check each grid , generate configurations
//       // takes in row and colum that willl be checked
//       // when this fjunction is called, determine valid moves
//       // check condition
//       Collection<Configuration>neighbors = new LinkedList<>();
//       if (grid[thisrow][thiscol])
//       StringBuilder builder = new StringBuilder();
//       builder.append();
//       neighbors.add(new HoppersConfig(builder.toString()));
//
//
//       return neighbors;
//   }

    @Override
    public Collection<Configuration> getNeighbors() throws IOException {
        Collection<Configuration>neighbors = new LinkedList<>(); // get the moves of the next board, create a helper function
        for (int x = 0; x < numberOfRow; x++) { //curserow
            for (int y = 0; y < numberOfCol; y++) { // cursecol
                if (grid[x][y].equals("G") || grid[x][y].equals("R")){

                    if (x + 2 < numberOfCol && grid[x + 2][y].equals("G")) { // south
                        if (x + 4 < numberOfCol && grid[x + 4][y].equals(".")){
                            neighbors.add(new HoppersConfig(this, x, y, x+4, y));
                        }

                    } if (y + 2 < numberOfRow && grid[x][y + 2].equals("G")) { // east
                        if (y + 4 < numberOfRow && grid[x][y + 4].equals(".")){
                            neighbors.add(new HoppersConfig(this, x, y, x, y+4));
                        }

                    } if (x - 2 >= 0 && grid[x - 2][y].equals("G")) { // north
                        if (x - 4 >= 0 && grid[x - 4][y].equals(".")){
                            neighbors.add(new HoppersConfig(this, x, y, x-4, y));
                        }

                    } if (y - 2 >=0 && grid[x][y - 2].equals("G")) { // west
                        if (y - 4 >=0 && grid[x][y - 4].equals(".")){
                            neighbors.add(new HoppersConfig(this, x, y, x, y-4));
                        }

                    } if (x + 1 < numberOfCol && y + 1 < numberOfRow && grid[x + 1][y + 1].equals("G")) { // southeast
                        if (x + 2 < numberOfCol && y + 2 < numberOfRow && grid[x + 2][y + 2].equals(".")){
                            neighbors.add(new HoppersConfig(this, x, y, x+2, y+2));
                        }

                    } if (x + 1 < numberOfCol && y - 1 >=0 && grid[x + 1][y - 1].equals("G")) { // southwest
                        if (x + 2 < numberOfCol && y - 2 < numberOfRow && grid[x + 2][y - 2].equals(".")){
                            neighbors.add(new HoppersConfig(this, x, y, x+2, y-2));
                        }

                    } if (x - 1 >= 0 && y + 1 < numberOfRow && grid[x - 1][y + 1].equals("G")) { // northeast
                        if (x - 2 < numberOfCol && y + 2 < numberOfRow && grid[x - 2][y + 2].equals(".")){
                            neighbors.add(new HoppersConfig(this, x, y, x-2, y+2));
                        }

                    } if (x - 1 >= 0 && y - 1 >=0 && grid[x - 1][y - 1].equals("G")) { // northwest
                        if (x - 2 < numberOfCol && y - 2 < numberOfRow && grid[x - 2][y - 2].equals(".")){
                            neighbors.add(new HoppersConfig(this, x, y, x-2, y-2));
                        }

                    }
                }
            }
        }
        return neighbors;
    }
}
