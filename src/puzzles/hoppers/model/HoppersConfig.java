package puzzles.hoppers.model;

import javafx.application.Application;
import puzzles.common.Observer;
import puzzles.common.solver.Configuration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputFilter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Collection;
import puzzles.hoppers.gui.HoppersGUI;

// TODO: implement your HoppersConfig for the common solver

public class HoppersConfig implements Configuration{
    private int thisrow;
    private int thiscol;
    private int[] row;
    private int[] col;
    private int curseRow;
    private int curseCol;
    private String[][] grid;
    public HoppersConfig(String filename) throws IOException {
        try (BufferedReader in = new BufferedReader(new FileReader(filename))) {
            String line = in.readLine();
            String[] firstField = line.split("\\s+");
            row = new int[thisrow];
            col = new int[thiscol];
            curseRow = 0;
            curseCol = -1;
            for (int rowan = 0; rowan < thisrow; rowan++) {
                row[rowan] = Integer.parseInt(firstField[rowan]);
            }
            line = in.readLine();
            String[] secondFields = line.split("\\s+"); // colums - left to right
            for (int g = 0; g < secondFields.length; g++) {
                col[g] = Integer.parseInt(secondFields[g]);
            }
            grid = new String[thisrow][thiscol];
            for (int i = 0; i < thiscol && i< thisrow; i++) {
                line = in.readLine();
                String[] dimensionFields = line.split("\\s+");
                grid[i] = dimensionFields;
            }
        }
    }

    @Override
    public boolean isSolution() {

        for (int x = 0; x < thiscol; x++) { //curserow
            for (int y = 0; y < thisrow; y++) { // cursecol
                boolean isRedFrog = true;
                if (grid[x][y].equals("G")){
                     isRedFrog = false;
                }if (grid[x][y].equals(".")){
                     isRedFrog = false;
                }if (grid[x][y].equals("*")){
                     isRedFrog = false;
                }if (isRedFrog){
                    return true;
                }
            }
        }
        return true;
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
        for (int x = 0; x < thisrow; x++) { //curserow
            for (int y = 0; y < thiscol; y++) { // cursecol
                if (grid[x][y].equals("G") || grid[x][y].equals("R")){

                    if (x + 1 < thiscol && grid[x + 1][y].equals("G")) { // east

                    } if (y + 1 < thisrow && grid[x][y + 1].equals("G")) { // south

                    } if (x - 1 >= 0 && grid[x - 1][y].equals("G")) { // west

                    } if (y - 1 >=0 && grid[x][y - 1].equals("G")) { // north

                    } if (x + 1 < thiscol && y + 1 < thisrow && grid[x + 1][y + 1].equals("G")) { // southeast

                    } if (x + 1 < thiscol && y - 1 >=0 && grid[x + 1][y - 1].equals("G")) { // southwest

                    } if (x - 1 >= 0 && y + 1 < thisrow && grid[x - 1][y + 1].equals("G")) { // northeast

                    } if (x - 1 >= 0 && y - 1 >=0 && grid[x - 1][y - 1].equals("G")) { // northwest


                    }
//                    neighbors.addAll(getCurrentNeighbors());
                }
            }
        }

        // do a check if the red frog is still there
        // loop through every coordinate, and check if there is a frog. every time you find a frog,
        // check if it has a valid move
        return neighbors;
    }
}
