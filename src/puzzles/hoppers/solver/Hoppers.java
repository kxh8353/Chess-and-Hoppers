package puzzles.hoppers.solver;
import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;
import puzzles.hoppers.model.HoppersConfig;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Hoppers {

//    public static int row = 0;
//    public static int col = 0;
//    private String[][] grid;

    private static String line;


    public Hoppers(String filename) throws IOException {
        line = new String(filename);
    }


    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: java Hoppers filename");
        } else {
            try {
                line = args[0];
                System.out.println("File: " + line);

                Configuration init = new HoppersConfig(args[0]);
                System.out.println(init + "\n");
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
        }
        HoppersConfig config = new HoppersConfig(line);
        ArrayList<Configuration> solved = (ArrayList<Configuration>) Solver.getShortestPath(config);
        System.out.println("Total Configs: " + Solver.totalconfigs);
        System.out.println("Unique Configs: " + Solver.uniqueconfigs);
        if (!solved.isEmpty()){
            for (int i = 0; i<solved.size(); i++) {
                System.out.println("step " + i + ": " + solved.get(i));
            }
        }else{
            System.out.println("No Solution");
        }
    }
}


