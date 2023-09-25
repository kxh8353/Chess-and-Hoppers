package puzzles.clock;
import java.io.IOException;
import java.util.ArrayList;
import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;

/** Project 2 part 1 - Clock
 * Author: Kevin Huang
 */
public class Clock{

    public static ClockConfig configs;
    public static void main(String[] args) throws IOException {

        if (args.length < 2) {
            System.out.println(("Usage: java Clock start stop"));
            return;
        }
        int hours = Integer.parseInt((args[0]));
        int starthour = Integer.parseInt(args[1]);
        int endhour = Integer.parseInt(args[2]);
        System.out.println("Hours: " + hours + ", " + "Start: " + starthour + ", " + "End: " + endhour);

        ClockConfig config = new ClockConfig(hours, starthour, endhour);
        ArrayList<Configuration> solved = (ArrayList<Configuration>) Solver.getShortestPath(config);
        System.out.println("Total Configs: " + Solver.totalconfigs);
        System.out.println("Unique Configs: " + Solver.uniqueconfigs);
        if (!solved.isEmpty()){
            for (int i = 0; i<solved.size(); i++){
                System.out.println("step " + i + ": " + solved.get(i));
            }
        }else{
            System.out.println("No solution");
        }
    }
}















