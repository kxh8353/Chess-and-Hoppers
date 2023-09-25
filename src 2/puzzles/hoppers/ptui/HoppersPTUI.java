package puzzles.hoppers.ptui;

import puzzles.common.Observer;
import puzzles.hoppers.model.HoppersConfig;
import puzzles.hoppers.model.HoppersModel;
import puzzles.chess.ptui.ChessPTUI;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import java.io.PrintWriter;

public class HoppersPTUI implements Observer<HoppersModel, String> {
    private HoppersModel model;

    public static String filename;
    public static HoppersConfig selection;
    public static HoppersConfig secondselection;

    public void init(String filename) throws IOException {
        selection = new HoppersConfig(filename);
        secondselection = new HoppersConfig(filename);
        this.model = new HoppersModel(filename);
        this.model.addObserver(this);
        displayHelp();
    }

    /**
     * updates game status
     * @param model the object that wishes to inform this object
     *                about something that has happened.
     * @param data optional data the server.model can send to the observer
     *
     */

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
     * checks user input, and follows specified directions
     * @param selection the coordinates of the selected frog
     * @param secondSelection the coordinates of the space, regardless of validity
     * @throws IOException
     */
    public void run(HoppersConfig selection, HoppersConfig secondSelection) throws IOException {
        Scanner in = new Scanner( System.in );
        for ( ; ; ) {
            System.out.print( "> " );
            String line = in.nextLine();
            String[] words = line.split( "\\s+" );
            if (words.length > 0) {
                if (words[0].startsWith( "r" )){
                    model.reset();
                }
                if (words[0].startsWith( "h" )){
                    model.hint();
                }
                if (words[0].startsWith( "l" )){
                    model.load(words[1]);
                }
                if (words[0].startsWith( "s" )){

                    model.select(Integer.parseInt(words[1]), Integer.parseInt(words[2]));
                }
                if (words[0].startsWith( "q" )) {
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java HoppersPTUI filename");
        } else {
            try {
                HoppersPTUI ptui = new HoppersPTUI();
                ptui.init(args[0]);
                ptui.run(selection, secondselection);
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
        }
    }
}
