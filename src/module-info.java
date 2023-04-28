module BFSPuzzleSolver {
    requires transitive javafx.controls;
    requires jdk.jfr;
    exports puzzles.common;
    exports puzzles.common.solver;
    exports puzzles.hoppers.gui;
    exports puzzles.hoppers.model;
    exports puzzles.chess.gui;
    exports puzzles.chess.model;
}