package lk.raneesh.pathfinder.views;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import lk.raneesh.pathfinder.utility.GridWeight;

public class PathFinderGUI extends Application {

    private GridPane landscapeGrid;
    private GridPane controlGrid;

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
        primaryStage.show();
    }

    public GridPane generateLandscape() {
        landscapeGrid = new GridPane();
        landscapeGrid.setPadding(new Insets(0, 0, 0, 0));
        landscapeGrid.setPrefSize(500, 500);
        landscapeGrid.setStyle("-fx-background-color: #880000;");
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

                        gridCell.setOnMouseEntered(event -> {
                            gridCell.setFill(Color.web("#00BB00"));
                        });

                        gridCell.setOnMouseExited(event -> {
                            gridCell.setFill(Color.web("#009900"));
                        });
                        break;
                    case 2:
                        gridCell.setFill(Color.web("#005500"));

                        gridCell.setOnMouseEntered(event -> {
                            gridCell.setFill(Color.web("#007700"));
                        });

                        gridCell.setOnMouseExited(event -> {
                            gridCell.setFill(Color.web("#005500"));
                        });
                        break;
                    case 3:
                        gridCell.setFill(Color.web("#777777"));

                        gridCell.setOnMouseEntered(event -> {
                            gridCell.setFill(Color.web("#999999"));
                        });

                        gridCell.setOnMouseExited(event -> {
                            gridCell.setFill(Color.web("#777777"));
                        });
                        break;
                    case 4:
                        gridCell.setFill(Color.web("#0000BB"));

                        gridCell.setOnMouseEntered(event -> {
                            gridCell.setFill(Color.web("#0000FF"));
                        });
                        gridCell.setOnMouseExited(event -> {
                            gridCell.setFill(Color.web("#0000BB"));
                        });
                        break;
                    default:
                        gridCell.setFill(Color.web("#00DD00"));
                        gridCell.setStyle("-fx-background-color: #00DD00;");
                        gridCell.setOnMouseEntered(event -> {
                            gridCell.setFill(Color.web("#00FF00"));
                        });
                        gridCell.setOnMouseExited(event -> {
                            gridCell.setFill(Color.web("#00DD00"));
                        });
                        break;
                }

                landscapeGrid.add(gridCell, i, j);

                gridCell.widthProperty().bind(rectangleAreaSize.divide(18));
                gridCell.heightProperty().bind(rectangleAreaSize.divide(18));
            }
        }
        return landscapeGrid;
    }

    public GridPane generateControls() {
        controlGrid = new GridPane();
        controlGrid.setPadding(new Insets(10, 10, 10, 10));
        controlGrid.setPrefSize(300, 300);
        controlGrid.setVgap(5);
        controlGrid.setHgap(5);

        return controlGrid;
    }
}
