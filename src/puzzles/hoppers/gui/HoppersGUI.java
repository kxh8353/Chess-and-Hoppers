package puzzles.hoppers.gui;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import puzzles.common.Coordinates;
import puzzles.common.Observer;
import puzzles.hoppers.model.HoppersConfig;
import puzzles.hoppers.model.HoppersModel;

import javafx.application.Application;
import javafx.stage.Stage;
import puzzles.hoppers.ptui.HoppersPTUI;

import java.io.IOException;
import java.util.Objects;

public class HoppersGUI extends Application implements Observer<HoppersModel, String> {
    /** The size of all icons, in square dimension */
    private final static int ICON_SIZE = 75;
    /** the font size for labels and buttons */
    private final static int FONT_SIZE = 12;

    /** The resources directory is located directly underneath the gui package */
    private final static String RESOURCES_DIR = "resources/";
    private HoppersModel model;

    // for demonstration purposes
    public Image redFrog = new Image(Objects.requireNonNull(getClass().getResourceAsStream(RESOURCES_DIR + "red_frog.png")));

    public Image greenFrog = new Image(Objects.requireNonNull(getClass().getResourceAsStream(RESOURCES_DIR + "green_frog.png")));
    public Image lilyPad = new Image(Objects.requireNonNull(getClass().getResourceAsStream(RESOURCES_DIR + "lily_pad.png")));

    public static Image water = new Image(Objects.requireNonNull(HoppersGUI.class.getResourceAsStream(RESOURCES_DIR + "water.png")));
//    public static Image water = new Image(HoppersGUI.class.getResourceAsStream(RESOURCES_DIR+"water.png"));

    private Stage stage;



    public Coordinates coordinates;

//    public Button[][] updateButton; // = new Button[coordinates.row()][coordinates.col()];

    public Button squares;

    public Button load;
    public Button reset;
    public Button hint;
    public String filename;
    public GridPane gpane;

    public boolean hnt;


    public void init() {
        String filename = getParameters().getRaw().get(0);
//        this.updateButton = new Button[coordinates.row()][coordinates.col()];
//        coordinates = new Coordinates(filename);
        try {
            model = new HoppersModel(filename);
            model.addObserver(this);
        }catch(IOException e){
            System.out.println("file not found");
        }
    }

    private GridPane design(){
        GridPane gridpane = new GridPane();
        for (int row = 0; row < coordinates.row(); row++){
            for (int col = 0; col < coordinates.col(); col++){
                squares = new Button();
                squares.setStyle("""
                        -fx-background-color: blue;
                        """);
                if (((row+col)%2)==0){
                    squares.setGraphic(new ImageView(lilyPad));
                }
//                updateButton[row][col] = squares;
                gridpane.add(squares, row, col);
            }
        }
        return gridpane;
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
//        gpane = design();
        stage.setTitle("Hoppers GUI");
        BorderPane bpane = new BorderPane();
        HBox hbox = new HBox();
        Label label = new Label("well well well...");
        hbox.setAlignment(Pos.TOP_CENTER);
        hbox.getChildren().add(label);
        bpane.setTop(hbox);

        Button button = new Button();
        button.setGraphic(new ImageView(redFrog));
        button.setMinSize(ICON_SIZE, ICON_SIZE);
        button.setMaxSize(ICON_SIZE, ICON_SIZE);
        bpane.setCenter(button);
        Scene scene = new Scene(bpane);
//        stage.setScene(scene);

        HBox loadbutton = new HBox();
        load = new Button("LOAD");
        load.setOnAction(event-> model.load(filename));
        loadbutton.getChildren().add(load);

        HBox resetbutton = new HBox();
        reset = new Button("RESET");
        reset.setOnAction(event -> {
            try {
                model.reset();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        resetbutton.getChildren().add(reset);

        HBox hintbutton = new HBox();
        hint = new Button("HINT");
        hint.setOnAction(event -> {
            try {
                model.hint();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        hintbutton.getChildren().add(hint);

//        HBox threeButtons = new HBox(load, reset, hint);
        HBox threeButtons = new HBox();
        threeButtons.getChildren().add(loadbutton);
        threeButtons.getChildren().add(resetbutton);
        threeButtons.getChildren().add(hintbutton);
        bpane.setBottom(threeButtons);

        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void update(HoppersModel hoppersModel, String msg) {
        if (!hnt){
            hint = new Button("HINT");
            hint.setOnAction(event -> {
                try {
                    model.hint();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        this.stage.sizeToScene();  // when a different sized puzzle is loaded
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java HoppersPTUI filename");
        } else {
            Application.launch(args);
        }
    }
}
