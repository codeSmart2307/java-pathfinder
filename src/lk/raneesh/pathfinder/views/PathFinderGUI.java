/**
 * Author: Raneesh Gomez
 * IIT Student ID: 2016087
 * UoW ID: 16266986
 * Algorithms: Theory, Design and Implementation
 * Coursework: Shortest Path Finder based on a grid using the A* Search Algorithm
 * File Name: PathFinderGUI.java
 */

package lk.raneesh.pathfinder.views;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
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

import lk.raneesh.pathfinder.controllers.PathFinderController;
import lk.raneesh.pathfinder.models.CellNode;
import lk.raneesh.pathfinder.utility.GridWeight;

import edu.princeton.cs.introcs.Stopwatch;

public class PathFinderGUI extends Application {

    private GridPane landscapeGrid;

    private Text costLabel;
    private Text timeLabel;
    private TextField sourceXInput;
    private TextField sourceYInput;
    private TextField destinationXInput;
    private TextField destinationYInput;
    private ToggleGroup heuristicGroup;
    private Button clearFieldsBtn;
    private Button runBtn;
    private Button clearPathBtn;
    private Button doubleBtn;
    private Button quadrupleBtn;
    private Button defaultGridBtn;

    private static int destinationNodeClickCount = 0;
    private static boolean isStartNodeSelected = false;
    private static boolean isGridDouble = false;
    private static boolean isGridQuadruple = false;
    private static int rows = 20;
    private static int columns = 20;

    public static int getRows() {
        return rows;
    }

    public static int getColumns() {
        return columns;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane primaryBorderPane = new BorderPane();
        primaryBorderPane.setPadding(new Insets(20, 50, 50, 50));
        primaryBorderPane.setPrefSize(800, 800);

        primaryBorderPane.setTop(generateControls());

        Stopwatch gridGenerateTime = new Stopwatch();
        primaryBorderPane.setCenter(generateLandscape());
        System.out.println("Grid Generation | Time Elapsed: " + gridGenerateTime.elapsedTime() + " seconds");

        Scene scene = new Scene(primaryBorderPane, 800, 1000);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Shortest Path Finder based on the A* Algorithm");
        primaryStage.show();

        getInput(primaryBorderPane);
    }

    public void getInput(BorderPane borderPane) {
        clearFieldsBtn.setOnMouseClicked(event -> {
            sourceXInput.setText("");
            sourceYInput.setText("");
            destinationXInput.setText("");
            destinationYInput.setText("");
            PathFinderController.startI = -1;
            PathFinderController.startJ = -1;
            PathFinderController.endI = -1;
            PathFinderController.endJ = -1;

        });
        defaultGridBtn.setOnMouseClicked(event -> {
            rows = 20;
            columns = 20;
            isGridDouble = false;
            isGridQuadruple = false;
            borderPane.setCenter(generateLandscape());
        });
        doubleBtn.setOnMouseClicked(event -> {
            rows = 40;
            columns = 40;
            isGridDouble = true;
            isGridQuadruple = false;
            isStartNodeSelected = false;
            destinationNodeClickCount = 0;
            costLabel.setText("0.0");
            PathFinderController.openNodes = null;
            PathFinderController.path = null;
            PathFinderController.gridNodes = new CellNode[rows][columns];
            borderPane.setCenter(generateLandscape());
        });
        quadrupleBtn.setOnMouseClicked(event -> {
            rows = 80;
            columns = 80;
            isGridQuadruple = true;
            isGridDouble = false;
            isStartNodeSelected = false;
            destinationNodeClickCount = 0;
            costLabel.setText("0.0");
            PathFinderController.openNodes = null;
            PathFinderController.path = null;
            PathFinderController.gridNodes = new CellNode[rows][columns];
            borderPane.setCenter(generateLandscape());
        });
        clearPathBtn.setOnMouseClicked(event -> {
            borderPane.setCenter(generateLandscape());
            isStartNodeSelected = false;
            destinationNodeClickCount = 0;
            costLabel.setText("0.0");
            PathFinderController.openNodes = null;
            PathFinderController.path = null;
        });
        runBtn.setOnMouseClicked(event -> {
            Stopwatch totalTime = new Stopwatch();
            try {
                if (!sourceXInput.getText().equals("") && !sourceYInput.getText().equals("") && !destinationXInput.getText().equals("") && !destinationYInput.getText().equals("")) {
                    try {
                        if (Integer.parseInt(sourceXInput.getText()) >= 0 && Integer.parseInt(sourceXInput.getText()) <= (rows - 1)) {
                            PathFinderController.startI = Integer.parseInt(sourceXInput.getText());
                        } else {
                            Alert sourceXAlert = new Alert(Alert.AlertType.ERROR); //Alert popup of type "ERROR"
                            sourceXAlert.setTitle("Invalid Input Alert");
                            sourceXAlert.setContentText("Starting Point X Coordinate Out of Bounds!");
                            sourceXAlert.show();
                        }
                        if (Integer.parseInt(sourceYInput.getText()) >= 0 && Integer.parseInt(sourceYInput.getText()) <= (rows - 1)) {
                            PathFinderController.startJ = Integer.parseInt(sourceYInput.getText());
                        } else {
                            Alert sourceYAlert = new Alert(Alert.AlertType.ERROR); //Alert popup of type "ERROR"
                            sourceYAlert.setTitle("Invalid Input Alert");
                            sourceYAlert.setContentText("Starting Point Y Coordinate Out of Bounds!");
                            sourceYAlert.show();
                        }
                        if (Integer.parseInt(destinationXInput.getText()) >= 0 && Integer.parseInt(destinationXInput.getText()) <= (rows - 1)) {
                            PathFinderController.endI = Integer.parseInt(destinationXInput.getText());
                        } else {
                            Alert destinationXAlert = new Alert(Alert.AlertType.ERROR); //Alert popup of type "ERROR"
                            destinationXAlert.setTitle("Invalid Input Alert");
                            destinationXAlert.setContentText("Ending Point X Coordinate Out of Bounds!");
                            destinationXAlert.show();
                        }
                        if (Integer.parseInt(destinationYInput.getText()) >= 0 && Integer.parseInt(destinationYInput.getText()) <= (rows - 1)) {
                            PathFinderController.endJ = Integer.parseInt(destinationYInput.getText());
                        } else {
                            Alert destinationYAlert = new Alert(Alert.AlertType.ERROR); //Alert popup of type "ERROR"
                            destinationYAlert.setTitle("Invalid Input Alert");
                            destinationYAlert.setContentText("Ending Point Y Coordinate Out of Bounds!");
                            destinationYAlert.show();
                        }
                    } catch (NumberFormatException ex) {
                        Alert numberFormatAlert = new Alert(Alert.AlertType.ERROR); //Alert popup of type "ERROR"
                        numberFormatAlert.setTitle("Invalid Input Alert");
                        numberFormatAlert.setContentText("Input is not a number! Please try again");
                        numberFormatAlert.show();
                    }
                } else {
                    Alert emptyFieldsAlert = new Alert(Alert.AlertType.WARNING); //Alert popup of type "ERROR"
                    emptyFieldsAlert.setTitle("Empty Fields Alert");
                    emptyFieldsAlert.setContentText("Please fill all the fields!");
                    emptyFieldsAlert.show();
                }


                // Get selected radio button with distance metric
                RadioButton selectedHeuristicBtn = (RadioButton) heuristicGroup.getSelectedToggle();
                String heuristicValue = selectedHeuristicBtn.getText();
                // Assign selected radio button value to distance metric variable in controller class
                PathFinderController.distanceMetric = heuristicValue;

                Stopwatch algorithmRunTime = new Stopwatch();
                PathFinderController.runPathfinder();
                timeLabel.setText(String.valueOf(algorithmRunTime.elapsedTime()) + " seconds");
                System.out.println("A* Search | Time Elapsed: " + algorithmRunTime.elapsedTime() + " seconds");

                int[][] gridCellArr = GridWeight.getGridWeight();

                if (isGridDouble && !isGridQuadruple) {
                    gridCellArr = GridWeight.getDoubledGridWeight();
                } else if (isGridQuadruple && !isGridDouble) {
                    gridCellArr = GridWeight.getQuadrupledGridWeight();
                }

                Stopwatch drawTime = new Stopwatch();
                for (int k = 0; k < PathFinderController.path.size(); k++) {
                    Circle pathCircle = new Circle();
                    pathCircle.setFill(Color.web("#FF0000"));
                    pathCircle.setRadius(15.0f);
                    if (isGridDouble && !isGridQuadruple) {
                        pathCircle.setRadius(6.0f);
                    }
                    if (isGridQuadruple && !isGridDouble) {
                        pathCircle.setRadius(2.0f);
                    }
                    pathCircle.setStyle("-fx-opacity: 1;");
                    landscapeGrid.add(pathCircle, PathFinderController.path.get(k).getJ(), PathFinderController.path.get(k).getI());
                }

                System.out.println("Path Drawing | Time Elapsed: " + drawTime.elapsedTime() + " seconds");
                costLabel.setText(String.valueOf(PathFinderController.gridNodes[PathFinderController.endI][PathFinderController.endJ].getFinalCost()));

            } catch (NullPointerException | ArrayIndexOutOfBoundsException ex) {
                System.out.println("Path cannot be drawn!");
            }

            System.out.println("Total Time Elapsed: " + totalTime.elapsedTime() + " seconds");
        });
    }

    public GridPane generateLandscape() {
        landscapeGrid = new GridPane();
        landscapeGrid.setPadding(new Insets(0, 0, 0, 0));
        landscapeGrid.setMaxSize(800, 800);
        landscapeGrid.setStyle("-fx-background-color: #F4F4F4; -fx-background-image: url('images/landscape.PNG'); " +
                "-fx-background-size: 710 640; -fx-background-repeat: stretch;");
        landscapeGrid.setGridLinesVisible(true);

        for (int i = 0; i < rows; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setFillHeight(true);
            rowConstraints.setVgrow(Priority.ALWAYS);
            rowConstraints.setValignment(VPos.CENTER);
            landscapeGrid.getRowConstraints().add(rowConstraints);
        }

        for (int i = 0; i < columns; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setFillWidth(true);
            columnConstraints.setHgrow(Priority.ALWAYS);
            columnConstraints.setHalignment(HPos.CENTER);
            landscapeGrid.getColumnConstraints().add(columnConstraints);
        }

        NumberBinding rectangleAreaSize = Bindings.min(landscapeGrid.heightProperty(), landscapeGrid.widthProperty());

        int[][] gridCellArr = GridWeight.getGridWeight();

        if (isGridDouble && !isGridQuadruple) {
            gridCellArr = GridWeight.getDoubledGridWeight();
        } else if (isGridQuadruple && !isGridDouble) {
            gridCellArr = GridWeight.getQuadrupledGridWeight();
        }

        for (int i = 0; i < gridCellArr.length; i++) {
            for (int j = 0; j < gridCellArr.length; j++) {
                Rectangle gridCell = new Rectangle();

                CellNode gridCellNode = new CellNode(i, j);
                gridCellNode.setgCost(Integer.MAX_VALUE);
                PathFinderController.gridNodes[i][j] = gridCellNode;

                switch (gridCellArr[i][j]) {
                    case 1:
                        gridCell.setFill(Color.web("#00DD00"));
                        gridCell.setStyle("-fx-opacity: 0;");
                        gridCell.setOnMouseEntered(event -> {
                            gridCell.setFill(Color.web("#00FF00"));
                            gridCell.setStyle("-fx-opacity: 1;");
                        });
                        gridCell.setOnMouseExited(event -> {
                            gridCell.setFill(Color.web("#00DD00"));
                            gridCell.setStyle("-fx-opacity: 0;");
                        });
                        break;
                    case 2:
                        gridCell.setFill(Color.web("#009900"));
                        gridCell.setStyle("-fx-opacity: 0;");

                        gridCell.setOnMouseEntered(event -> {
                            gridCell.setFill(Color.web("#00BB00"));
                            gridCell.setStyle("-fx-opacity: 1;");
                        });
                        gridCell.setOnMouseExited(event -> {
                            gridCell.setFill(Color.web("#009900"));
                            gridCell.setStyle("-fx-opacity: 0;");
                        });

                        break;
                    case 3:
                        gridCell.setFill(Color.web("#005500"));
                        gridCell.setStyle("-fx-opacity: 0;");

                        gridCell.setOnMouseEntered(event -> {
                            gridCell.setFill(Color.web("#007700"));
                            gridCell.setStyle("-fx-opacity: 1;");
                        });
                        gridCell.setOnMouseExited(event -> {
                            gridCell.setFill(Color.web("#005500"));
                            gridCell.setStyle("-fx-opacity: 0;");
                        });
                        break;
                    case 4:
                        gridCell.setFill(Color.web("#777777"));
                        gridCell.setStyle("-fx-opacity: 0;");

                        gridCell.setOnMouseEntered(event -> {
                            gridCell.setFill(Color.web("#999999"));
                            gridCell.setStyle("-fx-opacity: 1;");
                        });
                        gridCell.setOnMouseExited(event -> {
                            gridCell.setFill(Color.web("#777777"));
                            gridCell.setStyle("-fx-opacity: 0;");
                        });
                        break;
                    // Default case will be if the weight is 5 meaning a blocked node
                    default:
                        gridCellNode.setBlocked(true); // Node is initialized as a blocked node
                        gridCell.setFill(Color.web("#0000BB"));
                        gridCell.setStyle("-fx-opacity: 0;");

                        gridCell.setOnMouseEntered(event -> {
                            gridCell.setFill(Color.web("#0000FF"));
                            gridCell.setStyle("-fx-opacity: 1;");
                        });
                        gridCell.setOnMouseExited(event -> {
                            gridCell.setFill(Color.web("#0000BB"));
                            gridCell.setStyle("-fx-opacity: 0;");
                        });
                        break;
                }

                landscapeGrid.add(gridCell, j, i);

                gridCell.setOnMouseClicked(event -> {

                    if (!isStartNodeSelected) {
                        isStartNodeSelected = true;
                        Node source = (Node) event.getSource();
                        int column = GridPane.getColumnIndex(source);
                        int row = GridPane.getRowIndex(source);
                        Circle startNode = new Circle();
                        startNode.setFill(Color.web("#FFFFFF"));
                        startNode.setRadius(15.0f);
                        if (isGridDouble && !isGridQuadruple) {
                            startNode.setRadius(6.0f);
                        }
                        if (isGridQuadruple && !isGridDouble) {
                            startNode.setRadius(2.0f);
                        }
                        startNode.setStyle("-fx-opacity: 1;");
                        landscapeGrid.add(startNode, column, row);

                        sourceXInput.setText(String.valueOf(row));
                        sourceYInput.setText(String.valueOf(column));
                    } else {
                        if (destinationNodeClickCount == 0) {
                            Node destination = (Node) event.getSource();
                            int column = GridPane.getColumnIndex(destination);
                            int row = GridPane.getRowIndex(destination);
                            Circle endNode = new Circle();
                            endNode.setFill(Color.web("#000000"));
                            endNode.setRadius(15.0f);
                            if (isGridDouble && !isGridQuadruple) {
                                endNode.setRadius(6.0f);
                            }
                            if (isGridQuadruple && !isGridDouble) {
                                endNode.setRadius(2.0f);
                            }
                            endNode.setStyle("-fx-opacity: 1;");
                            landscapeGrid.add(endNode, column, row);
                            destinationNodeClickCount++;

                            destinationXInput.setText(String.valueOf(row));
                            destinationYInput.setText(String.valueOf(column));
                        }
                    }
                });


                gridCell.setHeight(30);
                gridCell.setWidth(30);

                if (isGridDouble && !isGridQuadruple) {
                    gridCell.widthProperty().bind(rectangleAreaSize.divide(50));
                    gridCell.heightProperty().bind(rectangleAreaSize.divide(50));
                } else if (isGridQuadruple && !isGridDouble) {
                    gridCell.widthProperty().bind(rectangleAreaSize.divide(400));
                    gridCell.heightProperty().bind(rectangleAreaSize.divide(600));
                }
            }
        }

        return landscapeGrid;
    }

    public GridPane generateControls() {
        GridPane controlGrid = new GridPane();
        controlGrid.setPadding(new Insets(10, 10, 10, 25));
        controlGrid.setPrefSize(300, 300);
        controlGrid.setGridLinesVisible(false);
        controlGrid.setVgap(10);
        controlGrid.setHgap(15);

        //Setting Column and Row Constraints to define number of rows and columns in the grid pane
        int numRows = 10;
        int numColumns = 15;

        for (int i = 0; i < numRows; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPercentHeight(100.0 / numRows); //Assigns a percentage of the vertical height for the number of rows specified.
            controlGrid.getRowConstraints().add(rowConstraints);
        }

        for (int i = 0; i < numColumns; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setPercentWidth(100.0 / numColumns); //Assigns a percentage of the horizontal length for the number of columns specified.
            controlGrid.getColumnConstraints().add(columnConstraints);
        }

        //Header for the Source Coordinates
        Text sourceCoordinates = new Text("Start");
        sourceCoordinates.setFont(Font.font("Tahoma", FontWeight.BOLD, 14));
        controlGrid.add(sourceCoordinates, 4, 0, 3, 1);

        //Input Field for the Source X Coordinate
        sourceXInput = new TextField();
        sourceXInput.setPromptText("X"); //Setting a placeholder for the field
        sourceXInput.setPrefWidth(30);
        controlGrid.add(sourceXInput, 4, 1, 2, 1);

        //Input Field for the Source Y Coordinate
        sourceYInput = new TextField();
        sourceYInput.setPromptText("Y"); //Setting a placeholder for the field
        sourceYInput.setPrefWidth(30);
        controlGrid.add(sourceYInput, 6, 1, 2, 1);

        //Header for the Destination Coordinates
        Text destinationCoordinates = new Text("End");
        destinationCoordinates.setFont(Font.font("Tahoma", FontWeight.BOLD, 14));
        controlGrid.add(destinationCoordinates, 9, 0, 3, 1);

        //Input Field for the Destination X Coordinate
        destinationXInput = new TextField();
        destinationXInput.setPromptText("X"); //Setting a placeholder for the field
        destinationXInput.setPrefWidth(30);
        controlGrid.add(destinationXInput, 9, 1, 2, 1);

        //Input Field for the Destination Y Coordinate
        destinationYInput = new TextField();
        destinationYInput.setPromptText("Y"); //Setting a placeholder for the field
        destinationYInput.setPrefWidth(30);
        controlGrid.add(destinationYInput, 11, 1, 2, 1);

        //Clear button to clear fields
        clearFieldsBtn = new Button("CLEAR FIELDS");
        clearFieldsBtn.setMaxSize(110, 20);
        clearFieldsBtn.setStyle("-fx-background-color: #999999; -fx-background-radius: 14; -fx-text-fill: white;");
        controlGrid.add(clearFieldsBtn, 14, 1, 10, 1);

        //Defining mouse hover events for clear button
        clearFieldsBtn.setOnMouseEntered(event -> {
            clearFieldsBtn.setStyle("-fx-background-color: #CCCCCC; -fx-background-radius: 14; -fx-text-fill: white;");
        });
        clearFieldsBtn.setOnMouseExited(event -> {
            clearFieldsBtn.setStyle("-fx-background-color: #999999; -fx-background-radius: 14; -fx-text-fill: white;");
        });

        heuristicGroup = new ToggleGroup(); //Define Group for Radio Buttons for Heuristics

        //Radio Button for the Manhattan Distance Metric
        RadioButton manhattanBtn = new RadioButton("Manhattan");
        manhattanBtn.setToggleGroup(heuristicGroup);
        manhattanBtn.setSelected(true);
        controlGrid.add(manhattanBtn, 3, 4, 3, 1);

        //Radio Button for the Euclidean Distance Metric
        RadioButton euclideanBtn = new RadioButton("Euclidean");
        euclideanBtn.setToggleGroup(heuristicGroup);
        controlGrid.add(euclideanBtn, 7, 4, 3, 1);

        //Radio Button for the Chebyshev Distance Metric
        RadioButton chebyshevBtn = new RadioButton("Chebyshev");
        chebyshevBtn.setToggleGroup(heuristicGroup);
        controlGrid.add(chebyshevBtn, 11, 4, 3, 1);

        //Default button to default grid size
        defaultGridBtn = new Button("DEFAULT GRID");
        defaultGridBtn.setMaxSize(120, 30);
        defaultGridBtn.setStyle("-fx-background-color: #000099; -fx-background-radius: 14; -fx-text-fill: white;");
        controlGrid.add(defaultGridBtn, 1, 6, 14, 3);

        //Defining mouse hover events for clear button
        defaultGridBtn.setOnMouseEntered(event -> {
            defaultGridBtn.setStyle("-fx-background-color: #0000BB; -fx-background-radius: 14; -fx-text-fill: white;");
        });
        defaultGridBtn.setOnMouseExited(event -> {
            defaultGridBtn.setStyle("-fx-background-color: #000099; -fx-background-radius: 14; -fx-text-fill: white;");
        });

        //Run button to generate shortest path based on a selected heuristic
        runBtn = new Button("RUN");
        runBtn.setMaxSize(110, 40);
        runBtn.setStyle("-fx-background-color: #009432; -fx-background-radius: 14;");
        controlGrid.add(runBtn, 5, 6, 5, 3);

        //Defining mouse hover events for run button
        runBtn.setOnMouseEntered(event -> {
            runBtn.setStyle("-fx-background-color: #A3CB38; -fx-background-radius: 14;");
        });
        runBtn.setOnMouseExited(event -> {
            runBtn.setStyle("-fx-background-color: #009432; -fx-background-radius: 14;");
        });

        //Clear button to clear any generated paths
        clearPathBtn = new Button("CLEAR PATH");
        clearPathBtn.setMaxSize(110, 40);
        clearPathBtn.setStyle("-fx-background-color: #EA2027; -fx-background-radius: 14;");
        controlGrid.add(clearPathBtn, 8, 6, 5, 3);

        //Defining mouse hover events for clear button
        clearPathBtn.setOnMouseEntered(event -> {
            clearPathBtn.setStyle("-fx-background-color: #ff4d4d; -fx-background-radius: 14;");
        });
        clearPathBtn.setOnMouseExited(event -> {
            clearPathBtn.setStyle("-fx-background-color: #EA2027; -fx-background-radius: 14;");
        });

        //Double button to double grid size
        doubleBtn = new Button("GRID x2");
        doubleBtn.setMaxSize(90, 30);
        doubleBtn.setStyle("-fx-background-color: #000099; -fx-background-radius: 14; -fx-text-fill: white;");
        controlGrid.add(doubleBtn, 15, 6, 8, 3);

        //Defining mouse hover events for clear button
        doubleBtn.setOnMouseEntered(event -> {
            doubleBtn.setStyle("-fx-background-color: #0000BB; -fx-background-radius: 14; -fx-text-fill: white;");
        });
        doubleBtn.setOnMouseExited(event -> {
            doubleBtn.setStyle("-fx-background-color: #000099; -fx-background-radius: 14; -fx-text-fill: white;");
        });

        //Quadruple button to quadruple grid size
        quadrupleBtn = new Button("GRID x4");
        quadrupleBtn.setMaxSize(90, 30);
        quadrupleBtn.setStyle("-fx-background-color: #000099; -fx-background-radius: 14; -fx-text-fill: white;");
        controlGrid.add(quadrupleBtn, 12, 6, 8, 3);

        //Defining mouse hover events for clear button
        quadrupleBtn.setOnMouseEntered(event -> {
            quadrupleBtn.setStyle("-fx-background-color: #0000BB; -fx-background-radius: 14; -fx-text-fill: white;");
        });
        quadrupleBtn.setOnMouseExited(event -> {
            quadrupleBtn.setStyle("-fx-background-color: #000099; -fx-background-radius: 14; -fx-text-fill: white;");
        });

        Text costIdentifierLabel = new Text("TOTAL COST: ");
        costIdentifierLabel.setStyle("-fx-font-size: 11pt;");
        controlGrid.add(costIdentifierLabel, 3, 8, 5, 3);

        costLabel = new Text("0.0");
        costLabel.setStyle("-fx-font-size: 12pt; -fx-font-weight: bold;");
        controlGrid.add(costLabel, 6, 8, 5, 3);

        Text timeIdentifierLabel = new Text("TOTAL TIME: ");
        timeIdentifierLabel.setStyle("-fx-font-size: 11pt;");
        controlGrid.add(timeIdentifierLabel, 9, 8, 3, 3);

        timeLabel = new Text("0.0 seconds");
        timeLabel.setStyle("-fx-font-size: 12pt; -fx-font-weight: bold;");
        controlGrid.add(timeLabel, 12, 8, 10, 3);

        return controlGrid;
    }
}