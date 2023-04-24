package puzzles.hoppers.ptui;

import puzzles.common.Observer;
import puzzles.hoppers.model.HoppersModel;
import puzzles.chess.ptui.ChessPTUI;

import java.io.IOException;
import java.util.Scanner;

import java.io.PrintWriter;

public class HoppersPTUI implements Observer<HoppersModel, String> {
    private HoppersModel model;
    private PrintWriter out;

    public void init(String filename) throws IOException {
        this.model = new HoppersModel(filename);
        this.model.addObserver(this);
        displayHelp();
    }

    @Override
    public void update(HoppersModel model, String data) {
        // for demonstration purposes
        System.out.println(data);
        System.out.println(model);
    }

    private void displayHelp() {
        System.out.println( "h(int)              -- hint next move" );
        System.out.println( "l(oad) filename     -- load new puzzle file" );
        System.out.println( "s(elect) r c        -- select cell at r, c" );
        System.out.println( "q(uit)              -- quit the game" );
        System.out.println( "r(eset)             -- reset the current game" );
    }

    /**
     *  if the current state of the puzzle is solvable, the puzzle should
     *  advance to the next step in the solution with an indication that it was successful.
     *  Otherwise the puzzle should remain in the same state and indicate there is no solution.
     */
    public void HINT(String filename) throws IOException {
        this.model.hint(filename);
    }

    /**
     * When loading, the user will provide the path and name of a puzzle file for the game to load.
     * If the file is readable it is guaranteed to be a valid puzzle file and the new puzzle file
     * should be loaded and displayed, along with an indication of success.
     * If the file cannot be read, an error message should be displayed and the previous puzzle file should remain loaded.
     */
    public void LOAD(String filename){
        this.model.load(filename);
    }

    /**
     * Part 1:
     * For the first selection, the user should be able to select a cell on the board with the intention of
     * selecting the piece at that location. If there is a piece there, there should be an indication and selection
     * should advance to the second part. Otherwise if there is no piece there an error message should be displayed
     * and selection has ended.
     *
     * Part 2:
     * For the second selection, the user should be able to select another cell on the board with the intention of moving
     * the previously selected piece to this location. If the move is valid, it should be made and the board should be
     * updated and with an appropriate indication. If the move is invalid, and error message should be displayed.
     */
    public void SELECT(String filename){
        this.model.select(filename);
    }

    /**
     * The user can quit from and end the program.
     */
    public void QUIT(){
        this.model.quit();
    }

    /**
     * The previously loaded file should be reloaded, causing the puzzle
     * to return to its initial state. An indication of the reset should be informed to the user.
     */
    public void RESET() throws IOException {
        this.model.reset();
    }

    public void run() {
        Scanner in = new Scanner( System.in );
        for ( ; ; ) {
            System.out.print( "> " );
            String line = in.nextLine();
            String[] words = line.split( "\\s+" );
            if (words.length > 0) {
                if (words[0].startsWith( "q" )) {
                    break;
                }
                else {
                    displayHelp();
                }
            }
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java HoppersPTUI filename");
        } else {
            try {
                ChessPTUI ptui = new ChessPTUI();
                ptui.init(args[0]);
                ptui.run();
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
        }
    }
}
