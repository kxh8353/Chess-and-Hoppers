package puzzles.strings;


import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;
import java.util.ArrayList;

/** Project 2 part 1 - Strings
 * Author: Kevin Huang
 */
public class Strings {

    public static StringsConfig configs;
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println(("Usage: java Strings start finish"));
            return;
        }
        String start = args[0];
        String end = args[1];
        System.out.println("Start: " + start + ", " + "End: " + end);

        StringsConfig config = new StringsConfig(start, end);
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

