package lk.raneesh.pathfinder.views;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lk.raneesh.pathfinder.utility.GridWeight;

public class PathFinderGUI extends Application {

    private GridPane landscapeGrid;
    private GridPane controlGrid;

    private Text sourceCoordinates;
    private TextField sourceXInput;
    private TextField sourceYInput;
    private Text destinationCoordinates;
    private TextField destinationXInput;
    private TextField destinationYInput;
    private RadioButton manhattanBtn;
    private RadioButton euclideanBtn;
    private RadioButton chebyshevBtn;
    private Button runBtn;
    private Button clearBtn;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane primaryBorderPane = new BorderPane();
        primaryBorderPane.setPadding(new Insets(20, 50, 50, 50));
        primaryBorderPane.setPrefSize(800, 800);

        primaryBorderPane.setTop(generateControls());
        primaryBorderPane.setCenter(generateLandscape());

        Scene scene = new Scene(primaryBorderPane, 800, 1000);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Shortest Path Finder based on the A* Algorithm");
        primaryStage.show();
    }

    public GridPane generateLandscape() {
        landscapeGrid = new GridPane();
        landscapeGrid.setPadding(new Insets(0, 0, 0, 0));
        //landscapeGrid.setPrefSize(500, 500);
        landscapeGrid.setMaxSize(800, 800);
        landscapeGrid.setStyle("-fx-background-color: #F4F4F4; -fx-background-image: url('images/landscape.PNG'); " +
                "-fx-background-size: 710 640;");
        landscapeGrid.setGridLinesVisible(false);

        int numRows = 20;
        int numColumns = 20;

        for (int i = 0 ; i < numRows ; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPercentHeight(100.0 / numRows); //Assigns a percentage of the vertical height for the number of rows specified.
            landscapeGrid.getRowConstraints().add(rowConstraints);
        }

        for (int i = 0; i < numColumns; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setPercentWidth(100.0/ numColumns); //Assigns a percentage of the horizontal length for the number of columns specified.
            landscapeGrid.getColumnConstraints().add(columnConstraints);
        }

        NumberBinding rectangleAreaSize = Bindings.min(landscapeGrid.heightProperty(), landscapeGrid.widthProperty());

        for (int i = 0; i < GridWeight.getGridWeight().length; i++) {
            for (int j = 0; j < GridWeight.getGridWeight()[i].length; j++) {
                Rectangle gridCell = new Rectangle();

                switch(GridWeight.getGridWeight()[j][i]) {
                    case 1:
                        gridCell.setFill(Color.web("#009900"));
                        gridCell.setStyle("-fx-opacity: 0.0;");

                        gridCell.setOnMouseEntered(event -> {
                            gridCell.setFill(Color.web("#00BB00"));
                            gridCell.setStyle("-fx-opacity: 1;");
                        });

                        gridCell.setOnMouseExited(event -> {
                            gridCell.setFill(Color.web("#009900"));
                            gridCell.setStyle("-fx-opacity: 0.0;");
                        });

                        break;
                    case 2:
                        gridCell.setFill(Color.web("#005500"));
                        gridCell.setStyle("-fx-opacity: 0.0;");

                        gridCell.setOnMouseEntered(event -> {
                            gridCell.setFill(Color.web("#007700"));
                            gridCell.setStyle("-fx-opacity: 1;");
                        });

                        gridCell.setOnMouseExited(event -> {
                            gridCell.setFill(Color.web("#005500"));
                            gridCell.setStyle("-fx-opacity: 0.0;");
                        });
                        break;
                    case 3:
                        gridCell.setFill(Color.web("#777777"));
                        gridCell.setStyle("-fx-opacity: 0.0;");

                        gridCell.setOnMouseEntered(event -> {
                            gridCell.setFill(Color.web("#999999"));
                            gridCell.setStyle("-fx-opacity: 1;");
                        });

                        gridCell.setOnMouseExited(event -> {
                            gridCell.setFill(Color.web("#777777"));
                            gridCell.setStyle("-fx-opacity: 0.0;");
                        });
                        break;
                    case 4:
                        gridCell.setFill(Color.web("#0000BB"));
                        gridCell.setStyle("-fx-opacity: 0.0;");

                        gridCell.setOnMouseEntered(event -> {
                            gridCell.setFill(Color.web("#0000FF"));
                            gridCell.setStyle("-fx-opacity: 1;");
                        });
                        gridCell.setOnMouseExited(event -> {
                            gridCell.setFill(Color.web("#0000BB"));
                            gridCell.setStyle("-fx-opacity: 0.0;");
                        });
                        break;
                    default:
                        gridCell.setFill(Color.web("#00DD00"));
                        gridCell.setStyle("-fx-background-color: #00DD00; -fx-opacity: 0.0;");
                        gridCell.setOnMouseEntered(event -> {
                            gridCell.setFill(Color.web("#00FF00"));
                            gridCell.setStyle("-fx-opacity: 1;");
                        });
                        gridCell.setOnMouseExited(event -> {
                            gridCell.setFill(Color.web("#00DD00"));
                            gridCell.setStyle("-fx-opacity: 0.0;");
                        });
                        break;
                }

                landscapeGrid.add(gridCell, i, j);

                gridCell.widthProperty().bind(rectangleAreaSize.divide(18));
                gridCell.heightProperty().bind(rectangleAreaSize.divide(21));
            }
        }
        return landscapeGrid;
    }

    public GridPane generateControls() {
        controlGrid = new GridPane();
        controlGrid.setPadding(new Insets(10, 10, 10, 25));
        controlGrid.setPrefSize(300, 300);
        controlGrid.setVgap(10);
        controlGrid.setHgap(15);

        //Setting Column and Row Constraints to define number of rows and columns in the grid pane
        int numRows = 10;
        int numColumns = 15;

        for (int i = 0 ; i < numRows ; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPercentHeight(100.0 / numRows); //Assigns a percentage of the vertical height for the number of rows specified.
            controlGrid.getRowConstraints().add(rowConstraints);
        }

        for (int i = 0; i < numColumns; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setPercentWidth(100.0/ numColumns); //Assigns a percentage of the horizontal length for the number of columns specified.
            controlGrid.getColumnConstraints().add(columnConstraints);
        }

        //Header for the Source Coordinates
        sourceCoordinates = new Text("Start");
        sourceCoordinates.setFont(Font.font("Tahoma", FontWeight.BOLD, 14));
        controlGrid.add(sourceCoordinates, 3, 0, 3, 1);

        //Input Field for the Source X Coordinate
        sourceXInput = new TextField();
        sourceXInput.setPromptText("X"); //Setting a placeholder for the field
        sourceXInput.setPrefWidth(30);
        controlGrid.add(sourceXInput, 3, 1, 2, 1);

        //Input Field for the Source Y Coordinate
        sourceYInput = new TextField();
        sourceYInput.setPromptText("Y"); //Setting a placeholder for the field
        sourceYInput.setPrefWidth(30);
        controlGrid.add(sourceYInput, 5, 1, 2, 1);

        //Header for the Destination Coordinates
        destinationCoordinates = new Text("End");
        destinationCoordinates.setFont(Font.font("Tahoma", FontWeight.BOLD, 14));
        controlGrid.add(destinationCoordinates, 8, 0, 3, 1);

        //Input Field for the Destination X Coordinate
        destinationXInput = new TextField();
        destinationXInput.setPromptText("X"); //Setting a placeholder for the field
        destinationXInput.setPrefWidth(30);
        controlGrid.add(destinationXInput, 8, 1, 2, 1);

        //Input Field for the Destination Y Coordinate
        destinationYInput = new TextField();
        destinationYInput.setPromptText("Y"); //Setting a placeholder for the field
        destinationYInput.setPrefWidth(30);
        controlGrid.add(destinationYInput, 10, 1, 2, 1);

        ToggleGroup heuristicGroup = new ToggleGroup(); //Define Group for Radio Buttons for Heuristics

        //Radio Button for the Manhattan Distance Metric
        manhattanBtn = new RadioButton("Manhattan");
        manhattanBtn.setToggleGroup(heuristicGroup);
        manhattanBtn.setSelected(true);
        controlGrid.add(manhattanBtn, 2, 4, 3, 1);

        //Radio Button for the Euclidean Distance Metric
        euclideanBtn = new RadioButton("Euclidean");
        euclideanBtn.setToggleGroup(heuristicGroup);
        controlGrid.add(euclideanBtn, 6, 4, 3, 1);

        //Radio Button for the Chebyshev Distance Metric
        chebyshevBtn = new RadioButton("Chebyshev");
        chebyshevBtn.setToggleGroup(heuristicGroup);
        controlGrid.add(chebyshevBtn, 10, 4, 3, 1);

        //Run button to generate shortest path based on a selected heuristic
        runBtn = new Button("RUN");
        runBtn.setMaxSize(90, 50);
        runBtn.setStyle("-fx-background-color: #009432; -fx-background-radius: 14;");
        controlGrid.add(runBtn, 5, 6, 8, 3);

        //Defining mouse hover events for run button
        runBtn.setOnMouseEntered(event -> {
            runBtn.setStyle("-fx-background-color: #A3CB38; -fx-background-radius: 14;");
        });
        runBtn.setOnMouseExited(event -> {
            runBtn.setStyle("-fx-background-color: #009432; -fx-background-radius: 14;");
        });

        //Clear button to clear any generated paths
        clearBtn = new Button("CLEAR");
        clearBtn.setMaxSize(90, 50);
        clearBtn.setStyle("-fx-background-color: #EA2027; -fx-background-radius: 14;");
        controlGrid.add(clearBtn, 7, 6, 8, 3);

        //Defining mouse hover events for clear button
        clearBtn.setOnMouseEntered(event -> {
            clearBtn.setStyle("-fx-background-color: #ff4d4d; -fx-background-radius: 14;");
        });
        clearBtn.setOnMouseExited(event -> {
            clearBtn.setStyle("-fx-background-color: #EA2027; -fx-background-radius: 14;");
        });

        return controlGrid;
    }
}
