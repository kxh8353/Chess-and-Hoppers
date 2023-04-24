package puzzles.chess.solver;

import puzzles.chess.model.ChessConfig;
import puzzles.common.solver.Solver;

import java.io.IOException;

public class Chess {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Chess filename");
        }
        try {
            System.out.println("File:" + args[0]);
            ChessConfig start = new ChessConfig(args[0]);
            System.out.println(start.toString());
            Solver solutionMaker5000 = new Solver(start);
            solutionMaker5000.solutionFound();
        } catch (IOException i) {
            System.out.println("File not there.");
        }
    }
}
