package puzzles.hoppers.model;

import puzzles.common.solver.Configuration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputFilter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Collection;

// TODO: implement your HoppersConfig for the common solver

public class HoppersConfig implements Configuration{
    private static int DIM;
    private int[] row;
    private int[] col;
    private int curseRow;
    private int curseCol;
    private String[][] grid;

    private int x;
    private int y;
    public HoppersConfig(String filename) throws IOException {
        try (BufferedReader in = new BufferedReader(new FileReader(filename))) {
            DIM = Integer.parseInt(in.readLine());
            String line = in.readLine();
            String[] firstField = line.split("\\s+");
            row = new int[DIM];
            col = new int[DIM];
            curseRow = 0;
            curseCol = -1;
            for (int f = 0; f < DIM; f++) {
                row[f] = Integer.parseInt(firstField[f]);
            }
            line = in.readLine();
            String[] secondFields = line.split("\\s+"); // colums - left to right
            for (int g = 0; g < secondFields.length; g++) {
                col[g] = Integer.parseInt(secondFields[g]);
            }
            grid = new String[DIM][DIM];
            for (int i = 0; i < DIM; i++) {
                line = in.readLine();
                String[] dimensionFields = line.split("\\s+");
                grid[i] = dimensionFields;
            }
        }
    }

    @Override
    public boolean isSolution() {

        return false;
    }

    @Override
    public Collection<Configuration> getNeighbors() {
        Collection<Configuration>neighbors = new LinkedList<>();

        return null;
    }
}
