package puzzles.chess.model;


import puzzles.common.Observer;
import puzzles.common.solver.Solver;
import puzzles.common.solver.Configuration;
import puzzles.common.Coordinates;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * ChessModel.java

 * Author:jw5250
 * */
public class ChessModel {
    /** the collection of observers of this model */
    private final List<Observer<ChessModel, String>> observers = new LinkedList<>();

    /** the current configuration */
    private ChessConfig currentConfig;//The current configuration
    private ChessConfig originConfig;//The original configuration; the starting point.
    private Coordinates positionOfPieceSelected;//The position of the piece to be moved. Null if none have been selected.
    /**
     * The view calls this to add itself as an observer.
     *
     * @param observer the view
     */
    public void addObserver(Observer<ChessModel, String> observer) {
        this.observers.add(observer);
    }

    /**
     * The model's state has changed (the counter), so inform the view via
     * the update method
     */
    private void alertObservers(String data) {
        for (var observer : observers) {
            observer.update(this, data);
        }
    }

    public ChessModel(String filename) throws IOException {
        currentConfig = new ChessConfig(filename);
        originConfig = new ChessConfig(filename);
        positionOfPieceSelected = null;
    }
    /**
     * Converts file into a chess configuration.
     * @param filename the file in question.
     * */
    public void load(String filename){//Load a new thing into the PTUI.
        try {
            originConfig = new ChessConfig(filename);
            currentConfig = new ChessConfig(originConfig);//Test if this works.
            alertObservers("Loaded:" + filename);
        }catch(IOException o){
            alertObservers("Failed to load: " + filename);
        }
    }

    /**
     * Select and move a piece, depending on if the coordinate currently exists or not.
     * Currently works.
     * @param row The row
     * @param column The column
     * */
    public void select(int row, int column){
        if(positionOfPieceSelected == null){
            positionOfPieceSelected = new Coordinates(row, column);
            if( !currentConfig.returnBoardReference()[row][column].equals(chessPiece.EMPTY) ){
                alertObservers("Selected " + positionOfPieceSelected.toString());
                return;
            }else{
                alertObservers("Invalid selection " + positionOfPieceSelected.toString());
                positionOfPieceSelected = null;
                return;
            }

        }else{
            Coordinates newDestination = new Coordinates(row, column);
            ArrayList<Coordinates> validMoves = (ArrayList<Coordinates>)currentConfig.getValidPositionsOfAPiece(positionOfPieceSelected);
            for(int i = 0; i < validMoves.size();i++){
                if(validMoves.get(i).equals(newDestination)){
                    //Change code to where new config with the movement is generated.
                    //Line of code below works.
                    currentConfig = currentConfig.configAfterMovement(newDestination, currentConfig.getPieceAtCoordinates(positionOfPieceSelected));
                    alertObservers( "Captured from" + positionOfPieceSelected.toString() + " to " + newDestination.toString());
                    positionOfPieceSelected = null;
                    return;
                }
            }
            alertObservers( "Can't capture from " + positionOfPieceSelected.toString() + " to " + newDestination.toString());
            //Call update and relay that the position is not feasible.
            positionOfPieceSelected = null;
        }

    }
    /**
     * Nudge player into the direction of the goal.
     * */
    public void hint(){
        try {
            ArrayList<Configuration> path = (ArrayList<Configuration>) Solver.getShortestPath(currentConfig);
           final int SOLUTION_FLAG = 1;
            if(path.size() > SOLUTION_FLAG){
                currentConfig = (ChessConfig)path.get(1);//Get the next possible move
                //Solution has not been reached.
            }
            alertObservers("Next step!");
        }catch(IOException o){
            System.out.println("How");
        }
    }
    /**
     * Resets the current board.
     * */
    public void reset(){//Reset config
        currentConfig = new ChessConfig(originConfig);//Test if this works.
        alertObservers( "Puzzle reset!");
    }
    /**
     * Getterfor anything that needs the internal model for updates.
     * */
    public String[][] returnCurrentBoardReference(){
        return currentConfig.returnBoardReference();
    }
}
