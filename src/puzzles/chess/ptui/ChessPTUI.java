package puzzles.chess.ptui;

import puzzles.common.Observer;
import puzzles.chess.model.ChessModel;

import java.io.IOException;
import java.util.Scanner;
/**
 * ChessPTUI class
 * Author:jw5250
 * Problem with load.
 * */
public class ChessPTUI implements Observer<ChessModel, String> {
    private ChessModel model;//The model in question.
    private String[][] graphics;//The display.
    public void init(String filename) throws IOException {
        this.model = new ChessModel(filename);
        this.model.addObserver(this);
        model.load(filename);
        displayHelp();
    }
    /**
     * Update visuals and message respective to how model responds to input
     * */
    @Override
    public void update(ChessModel model, String data) {
        // for demonstration purposes
        System.out.println(data);//Assume graphics is at least a 1x1 grid
        graphics = model.returnCurrentBoardReference();
        System.out.print("   ");
        for(int i = 0; i < graphics[0].length;i++){
            System.out.print(i + " ");
        }
        System.out.println();
        System.out.print("  ");
        for(int i = 0; i < 2 * graphics[0].length;i++){
            System.out.print("-");
        }
        System.out.println();
        for(int i = 0; i < graphics.length;i++){
            System.out.print(i + "| ");
            for(int j = 0; j < graphics[i].length;j++){
                System.out.print(graphics[i][j] + " ");
            }
            System.out.println();
        }

    }

    private void displayHelp() {
        System.out.println( "h(int)              -- hint next move" );//Generate config path, do next move if solvable.
        System.out.println( "l(oad) filename     -- load new puzzle file" );//Load new game file.
        System.out.println( "s(elect) r c        -- select cell at r, c" );//Prepare cell at r, c
        System.out.println( "q(uit)              -- quit the game" );//Quits the game
        System.out.println( "r(eset)             -- reset the current game" );//Get original config.
    }
    /**
     * Additional code for piece selection.
     * */
    private void selectPiece(String[] input){
        if( input.length != 3 ){
            return;
        }else{
            int row = Integer.parseInt(input[1]);
            int col = Integer.parseInt(input[2]);
            model.select(row, col);
        }
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
                } else if(words[0].startsWith( "r" )){
                    model.reset();
                }else if(words[0].startsWith( "h" )){
                    model.hint();
                }else if(words[0].startsWith( "s" )){
                    selectPiece(words);
                }else if(words[0].startsWith( "l" )){
                    model.load(words[1]);
                } else {
                    displayHelp();
                }
            }
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java ChessPTUI filename");
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

