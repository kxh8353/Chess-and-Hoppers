package puzzles.clock;

import puzzles.common.solver.Solver;
import puzzles.hoppers.model.HoppersConfig;

import java.io.IOException;
import java.util.ArrayList;

public class Clock {

    public static HoppersConfig configs;
    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println(("Usage: java Clock hours stop end"));
        } else {
            // TODO
            int hours = Integer.parseInt(args[0]);
            int starthour = Integer.parseInt(args[1]);
            int endhour = Integer.parseInt(args[2]);
            System.out.println("Hours + " + hours  + ", " + "Start: " + starthour + ", " + "End: " + endhour);

            HoppersConfig config = new HoppersConfig(hours, starthour, endhour);
            ArrayList<HoppersConfig> solved = new HoppersConfig(hours, starthour, endhour);
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
