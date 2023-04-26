package puzzles.hoppers.ptui;

import puzzles.common.Observer;
import puzzles.hoppers.model.HoppersConfig;
import puzzles.hoppers.model.HoppersModel;
import puzzles.chess.ptui.ChessPTUI;

import java.io.IOException;
import java.util.Scanner;

import java.io.PrintWriter;

public class HoppersPTUI implements Observer<HoppersModel, String> {
    private HoppersModel model;
    public static String filename;
    public static HoppersConfig selection;
    public static HoppersConfig secondselection;

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


    public void run(String filename, HoppersConfig selection, HoppersConfig secondSelection) throws IOException {
        Scanner in = new Scanner( System.in );
        for ( ; ; ) {
            System.out.print( "> " );
            String line = in.nextLine();
            String[] words = line.split( "\\s+" );
            if (words.length > 0) {
                if (words[0].startsWith("r")){
                    model.reset();
                }
                if (words[0].startsWith("h")){
                    model.hint();
                }
                if (words[0].startsWith("l")){
                    model.load(filename);
                }
                if (words[0].startsWith("s")){

                    model.select(selection, secondSelection);
                }
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
                HoppersPTUI ptui = new HoppersPTUI();
                ptui.init(args[0]);
                ptui.run(filename, selection, secondselection);
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
        }
    }
}
