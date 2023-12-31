package puzzles.hoppers.model;

import puzzles.common.Coordinates;
import puzzles.common.Observer;
import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class HoppersModel {
    /** the collection of observers of this model */
    private final List<Observer<HoppersModel, String>> observers = new LinkedList<>();

    /** the current configuration */
    private HoppersConfig currentConfig;
    private String filename; // original filename
    private Configuration configurationConfig;
    private Coordinates coordinates;
    private Coordinates selectedSpace;



    // instead of passing in a new configutaiuon into select, you want to pass a coordinate into the select function
    // isvalid finctun belongs in the hoppersconfig
    // check if the coordinates are valid in the current config
    // model takes in a coordinate

    public HoppersConfig getter(){
        return currentConfig;
    }
    // rows
    // cols

    /**
     * The view calls this to add itself as an observer.
     *
     * @param observer the view
     */
    public void addObserver(Observer<HoppersModel, String> observer) {
        this.observers.add(observer);
    }

    /**
     * The model's state has changed (the counter), so inform the view via
     * the update method
     */
    private void alertObservers(String msg) {
        for (var observer : observers) {
            observer.update(this, msg);
        }
    }

    /**
     * initialization
     * @param filename specified filename
     * @throws IOException
     */

    public HoppersModel(String filename) throws IOException {
        this.filename = filename;
        currentConfig = new HoppersConfig(filename);

    }

    /**
     *  if the current state of the puzzle is solvable, the puzzle should
     *  advance to the next step in the solution with an indication that it was successful.
     *  Otherwise the puzzle should remain in the same state and indicate there is no solution.
     */
    public void hint() throws IOException {

        ArrayList<Configuration> path = (ArrayList<Configuration>) Solver.getShortestPath(currentConfig);
        if (!path.isEmpty()){
            if (path.size()>1) {
                // if path lenght is greater than 1
                currentConfig = (HoppersConfig) path.get(1);
                alertObservers("Next step!");

                if ((currentConfig.isSolution())) {
                    alertObservers("already solved!");
                }
            }

        }
        else{
            alertObservers("No solution");
        }
    }

    /**
     * When loading, the user will provide the path and name of a puzzle file for the game to load.
     * If the file is readable it is guaranteed to be a valid puzzle file and the new puzzle file
     * should be loaded and displayed, along with an indication of success.
     * If the file cannot be read, an error message should be displayed and the previous puzzle file should remain loaded.
     */
    public void load(String filename){ // new filename
        try {
            this.filename = filename;
            currentConfig = new HoppersConfig(filename);
            alertObservers("Loaded: " + this.filename);
        }catch(IOException e){
            alertObservers("garbage");

        }
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
    public void select(int r, int c) throws IOException {
        Coordinates coordinates = new Coordinates(r, c);
        if (selectedSpace == null){
            if (currentConfig.isValidMove(coordinates) && currentConfig.getGrid()[r][c].equals("G") || currentConfig.getGrid()[r][c].equals("R")){ // if theres a frog at the current spot, store it as a select
                    selectedSpace = coordinates;
                    alertObservers("Selected: " + coordinates);
            } else{
                alertObservers("no frog at " + coordinates);
            }
        }else{
            // there is currently a selected froig and you are clocking somewhere else
            // do jumping here
            Coordinates secondSelection = new Coordinates(r, c);
            // the coordinate jump to has to be a valid space
            // has to jump over a green frog
            if (currentConfig.isValidMove(secondSelection) && currentConfig.isvalidSpace(secondSelection) && currentConfig.jumpOverGreenFrog(selectedSpace,secondSelection)) {
                // do the jump by chnageing the grid or the current config
                currentConfig = new HoppersConfig(currentConfig, selectedSpace.row(), selectedSpace.col(), r, c);
                alertObservers("jumped from " + selectedSpace + " to " + secondSelection); // to new coordinates secondselection
                selectedSpace = null;
            }
            else{
                alertObservers("cant jump from: " +  selectedSpace + " to " + secondSelection);
            }
            selectedSpace = null;
        }
    }

    /**
     * The user can quit from and end the program.
     */
    public static void quit(){
        System.exit(0);
    }

    /**
     * The previously loaded file should be reloaded, causing the puzzle
     * to return to its initial state. An indication of the reset should be informed to the user.
     */
    public void reset() throws IOException {
        currentConfig = new HoppersConfig(filename);
        alertObservers("Puzzle  Reset!");
    }

    /**
     * converts results to string
     * @return a string representation
     */
    public String toString(){
        return currentConfig.toString();
    }
}