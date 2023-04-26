package puzzles.chess.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.CornerRadii;


import java.io.IOException;
import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import javafx.application.Application;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.FileChooser;

import puzzles.common.Observer;
import puzzles.common.Coordinates;
import puzzles.chess.model.ChessModel;
import puzzles.chess.model.ChessConfig;

/**
 * Author:jw5250
 * ChessGUI
     * Design plan:
         * Similar to PTUI
             * Part for displaying the messages from the update
             * Part for the board and pieces
             * Bottom part for the load, reset, and hint buttons
     * Current goal:
         * Need to find a way to resize gridpane
         * Visuals not updating properly
     * Questions:
         * When trying to reassign the board with new GridPane, visuals stop updating with the model.
 *
 * */
public class ChessGUI extends Application implements Observer<ChessModel, String> {
    private ChessModel model;
    private Label description;//Label that changes depending on thing done.
    private GridPane board;//Board display.

    /** The size of all icons, in square dimension */
    private final static int ICON_SIZE = 75;
    /** the font size for labels and buttons */
    private final static int FONT_SIZE = 12;

    private Stage stage;

    /** The resources directory is located directly underneath the gui package */
    private final static String RESOURCES_DIR = "resources/";

    //Link config characters to image links.
    static public final HashMap<String, String> imageMap = new HashMap(){
        {
            put(ChessConfig.spriteToClass.BISHOP.toString(), RESOURCES_DIR+"bishop.png");
            put(ChessConfig.spriteToClass.ROOK.toString(), RESOURCES_DIR+"rook.png");
            put(ChessConfig.spriteToClass.PAWN.toString(), RESOURCES_DIR+"pawn.png");
            put(ChessConfig.spriteToClass.KING.toString(), RESOURCES_DIR+"king.png");
            put(ChessConfig.spriteToClass.KNIGHT.toString(), RESOURCES_DIR+"knight.png");
            put(ChessConfig.spriteToClass.QUEEN.toString(), RESOURCES_DIR+"queen.png");
        }
    };//Quick way to turn internal model display into GUI display.
    // for demonstration purposes
    private Image bishop = new Image(getClass().getResourceAsStream(RESOURCES_DIR+"bishop.png"));

    /** a definition of light and dark and for the button backgrounds */
    private static final Background LIGHT =
            new Background( new BackgroundFill(Color.WHITE, null, null));
    private static final Background DARK =
            new Background( new BackgroundFill(Color.MIDNIGHTBLUE, null, null));

    @Override
    public void init() {
        // get the file name from the command line
        String filename = getParameters().getRaw().get(0);
        try {
            model = new ChessModel(filename);
            model.addObserver(this);
        }catch(IOException i){
            System.out.println("Invalid file: " + filename);
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        //System.out.println("Starting stage");
        description = new Label();
        description.setAlignment(Pos.CENTER);
        board = new GridPane();
        VBox mainContainer = new VBox();
        mainContainer.getChildren().add(description);
        boardReflectModel(model);
        mainContainer.getChildren().add(board);
        mainContainer.getChildren().add(bottomButtonPanel());
        Scene game = new Scene(mainContainer);
        stage.setScene(game);
        stage.show();
    }
    /**
     * Update the board part of the game.
     * */
    private void boardReflectModel(ChessModel modelCurrent){
        if(modelCurrent == null){
            return;
        }//Not working properly.
        //System.out.println("Model detected.");
        String[][] boardRef = modelCurrent.returnCurrentBoardReference();
        /*for(int i = 0; i < boardRef.length;i++){
            for(int j = 0; j < boardRef[i].length;j++) {
            System.out.print(boardRef[i][j] + " ");
            }
            System.out.println();
        }*/
        board.getChildren().clear();
        for(int i = 0; i < boardRef.length;i++){
            for(int j = 0; j < boardRef[i].length;j++) {
                Button button = new Button();
                if( ((i+j) % 2) == 0) {
                    button.setBackground(LIGHT);
                }else{
                    button.setBackground(DARK);
                }
                button.setMinSize(ICON_SIZE, ICON_SIZE);
                button.setMaxSize(ICON_SIZE, ICON_SIZE);
                if(imageMap.containsKey(boardRef[i][j])) {
                    Image newImage = new Image(getClass().getResourceAsStream(imageMap.get(boardRef[i][j])));
                    button.setGraphic(new ImageView(newImage));
                }
                button.setUserData(new Coordinates(i, j));
                button.setOnAction(event->{

                    Button source = (Button)event.getSource();
                    Coordinates data = (Coordinates)source.getUserData();
                    modelCurrent.select(data.row(), data.col());
                });
                board.add(button, j, i);
            }
        }
        //System.out.println(board.getColumnCount());
    }
    /**
     * Set up the bottom part of the application
     * */
    private HBox bottomButtonPanel(){
        HBox layout = new HBox();
        Button loadButton = new Button();
        loadButton.setText("Load");
        loadButton.setOnAction(event->{
            //System.out.println("Load");
            FileChooser chooser = new FileChooser();
            String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
            currentPath += File.separator + "data" + File.separator + "chess";  // or "hoppers"
            chooser.setInitialDirectory(new File(currentPath));
            File file = chooser.showOpenDialog(stage);
            String filename = "data" + File.separator + "chess" + File.separator;
            if(file != null) {
                filename += file.getName();
            }
            model.load(filename);
        });
        layout.getChildren().add(loadButton);

        Button resetButton = new Button();
        resetButton.setText("Reset");
        resetButton.setOnAction(event->{
            //System.out.println("Reset");
            model.reset();
        });
        layout.getChildren().add(resetButton);
        Button hintButton = new Button();
        hintButton.setText("Hint");
        hintButton.setOnAction(event->{
            //System.out.println("Hint");
            model.hint();
        });
        layout.getChildren().add(hintButton);
        layout.setAlignment(Pos.CENTER);
        return layout;
    }
    @Override
    public void update(ChessModel chessModel, String msg) {//Not getting called. Why?
        //System.out.println("Called update!");
        description.setText(msg);
        boardReflectModel(chessModel);//Not updating the grid
        this.stage.sizeToScene();  // when a different sized puzzle is loaded
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
