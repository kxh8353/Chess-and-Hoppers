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
import javafx.stage.FileChooser;
import puzzles.common.Coordinates;
import puzzles.common.Observer;
import puzzles.hoppers.model.HoppersConfig;
import puzzles.hoppers.model.HoppersModel;

import javafx.application.Application;
import javafx.stage.Stage;
import puzzles.hoppers.ptui.HoppersPTUI;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;

import static jdk.jfr.consumer.EventStream.openFile;

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

//    public static Image water = new Image(Objects.requireNonNull(HoppersGUI.class.getResourceAsStream(RESOURCES_DIR + "water.png")));
    public static Image water = new Image(HoppersGUI.class.getResourceAsStream(RESOURCES_DIR+"water.png"));

    private Stage stage;



    public Coordinates coordinates;

    public Button[][] updateButton; // = new Button[coordinates.row()][coordinates.col()];

    public Button squares;

    public Button load;
    public Button reset;
    public Button hint;
    public String filename;
    public GridPane gpane;
    public boolean loaded;
    private boolean isInitialized = false;
    public void init() {
        String filename = getParameters().getRaw().get(0);


        try {
            model = new HoppersModel(filename);
            model.addObserver(this);
        }catch(IOException e){
            System.out.println("file not found");
        }
        coordinates = new Coordinates(model.getter().numberOfRow, model.getter().numberOfCol); // newly added
        this.updateButton = new Button[coordinates.row()][coordinates.col()]; // newly added
    }

    private GridPane design(){
        GridPane gridpane = new GridPane();
        for (int row = 0; row < model.getter().numberOfRow; row++){
            for (int col = 0; col < model.getter().numberOfCol; col++){
                squares = new Button();
                squares.setMaxSize(75, 75);
                squares.setMinSize(75, 75);
                squares.setGraphic(new ImageView(water));

//                load = new Button("LOAD");
                final int r = row;
                final int c = col;
                squares.setOnAction(event-> {
                    try {
                        model.select(r, c);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });




                if (((row+col)%2)==0){
                    squares.setGraphic(new ImageView(lilyPad));
                }
                if (model.getter().getGrid()[row][col].equals("R")) {
                    squares.setGraphic(new ImageView(redFrog));
                }
                if (model.getter().getGrid()[row][col].equals("G")){
                    squares.setGraphic(new ImageView(greenFrog));
                }
                updateButton[row][col] = squares; // newly added

                gridpane.add(squares, row, col);
            }
            gridpane.setAlignment(Pos.CENTER);
        }
        return gridpane;
    }

    @Override
    public void start(Stage stage) throws Exception {
        String filename = getParameters().getRaw().get(0);
        this.stage = stage;
        gpane = design(); // newly added
        stage.setTitle("Hoppers GUI");
        BorderPane bpane = new BorderPane();
        HBox hbox = new HBox();
        Label label = new Label("well well well...");
        hbox.setAlignment(Pos.TOP_CENTER);
        hbox.getChildren().add(label);
        bpane.setTop(hbox);

        bpane.setCenter(gpane); // newly added

        Scene scene = new Scene(bpane);
        stage.setScene(scene); // newly added

        // three bottom buttons

        HBox loader = new HBox();
        load = new Button("LOAD");

        load.setOnAction(event-> {
            FileChooser fileChooser = new FileChooser();
            String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
            currentPath += File.separator + "data" + File.separator + "hoppers";  // or "hoppers"
            fileChooser.setInitialDirectory(new File(currentPath));

            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                try {
                    openFile(file.toPath());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                this.filename = String.valueOf(file);
                model.load(this.filename);
            }

        });
        loader.getChildren().add(load);

        HBox resetter = new HBox();
        reset = new Button("RESET");
        reset.setOnAction(event -> {
            try {
                model.reset();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        resetter.getChildren().add(reset);

        HBox hinter = new HBox();
        hint = new Button("HINT");
        hint.setOnAction(event -> {
            try {
                model.hint();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        hinter.getChildren().add(hint);



        HBox threeButtons = new HBox();
        threeButtons.getChildren().add(loader);
        threeButtons.getChildren().add(resetter);
        threeButtons.getChildren().add(hinter);
        bpane.setBottom(threeButtons);

        stage.setScene(scene);
        stage.show();
        isInitialized = true;
    }

    @Override
    public void update(HoppersModel hoppersModel, String msg) {
        System.out.println(msg);

        if (!loaded){
            load = new Button("LOAD");
            load.setOnAction(event -> {
                model.load(this.filename);
            });
        }
        if (!isInitialized) return;
        for (int row = 0; row < model.getter().numberOfRow; row++){
            for (int col = 0; col<model.getter().numberOfCol; col++){
                updateButton[row][col].setText(model.getter().toString());
            }
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
