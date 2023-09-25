package puzzles.chess.solver;

import puzzles.chess.model.ChessConfig;
import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;
import java.util.ArrayList;
import java.io.IOException;
/**
 * Chess.java
 * Author:jw5250
 *
 * */
public class Chess {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Chess filename");
        }
        try {
            ChessConfig start = new ChessConfig(args[0]);
            ArrayList<Configuration> g = (ArrayList<Configuration>)Solver.getShortestPath(start);
        } catch (IOException i) {
            System.out.println("File not there.");
        }
    }
}
