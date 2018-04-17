package lk.raneesh.pathfinder.controllers;

import javafx.scene.control.Alert;
import lk.raneesh.pathfinder.models.CellNode;
import lk.raneesh.pathfinder.utility.GridWeight;
import lk.raneesh.pathfinder.views.PathFinderGUI;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class PathFinderController {

    public static int startI;
    public static int startJ;
    public static int endI;
    public static int endJ;
    public static String distanceMetric;
    public static CellNode[][] gridNodes = new CellNode[PathFinderGUI.getRows()][PathFinderGUI.getColumns()];
    public static PriorityQueue<CellNode> openNodes;
    public static ArrayList<CellNode> path;

    public static void runPathfinder() {

        if (!gridNodes[startI][startJ].isBlocked()) {
            if (!gridNodes[endI][endJ].isBlocked()) {
                // Set starting node's gCost to 0
                gridNodes[startI][startJ].setgCost(0);
                // Starting node's parent doesn't exist since the path starts there
                gridNodes[startI][startJ].setParentNode(null);

                // Traverse though nodes in grid and assign heuristic and final cost
                for (int i = 0; i < gridNodes.length; i++) {
                    for (int j = 0; j < gridNodes.length; j++) {
                        // Calculate heuristic cost for all nodes in the grid based on the chosen distance metric and end node
                        if (distanceMetric.equals("Manhattan")) {
                            double heuristicCost = Math.abs(i - endI) + Math.abs(j - endJ);
                            gridNodes[i][j].setHeuristicCost(heuristicCost);
                            gridNodes[i][j].setFinalCost(gridNodes[i][j].getgCost() + gridNodes[i][j].getHeuristicCost());
                        }
                        else if (distanceMetric.equals("Euclidean")) {
                            double heuristicCost = Math.sqrt(Math.pow((i - endI), 2) + Math.pow((j - endJ), 2));
                            gridNodes[i][j].setHeuristicCost(heuristicCost);
                            gridNodes[i][j].setFinalCost(gridNodes[i][j].getgCost() + gridNodes[i][j].getHeuristicCost());
                        }
                        else if (distanceMetric.equals("Chebyshev")) {
                            int heuristicCost = Math.max(Math.abs(i - endI), Math.abs(j - endJ));
                            gridNodes[i][j].setHeuristicCost(heuristicCost);
                            gridNodes[i][j].setFinalCost(gridNodes[i][j].getgCost() + gridNodes[i][j].getHeuristicCost());
                        }
                    }
                }
                //System.out.println(gridNodes[endI][endJ].getHeuristicCost());
            }
            else {
                Alert sourceXAlert = new Alert(Alert.AlertType.ERROR); //Alert popup of type "ERROR"
                sourceXAlert.setTitle("Blocked Node Alert");
                sourceXAlert.setContentText("Selected Ending Node is not viable as it is blocked!");
                sourceXAlert.show();
                return;
            }
        }
        else {
            Alert sourceXAlert = new Alert(Alert.AlertType.ERROR); //Alert popup of type "ERROR"
            sourceXAlert.setTitle("Blocked Node Alert");
            sourceXAlert.setContentText("Selected Starting Node is not viable as it is blocked!");
            sourceXAlert.show();
            return;
        }

        openNodes = new PriorityQueue<>(new Comparator<CellNode>() {
            @Override
            public int compare(CellNode cellNode1, CellNode cellNode2) {
                if (cellNode1.getFinalCost() < cellNode2.getFinalCost()) {
                    return -1;
                } else if (cellNode1.getFinalCost() > cellNode2.getFinalCost()) {
                    return 1;
                }
                // If both final costs are equal then then FIFO is implemented
                return 0;
            }
        });

        // Runs search on the grid
        travelGrid();

        // If the ending node has been visited
        if (gridNodes[endI][endJ].isVisited()) {
            // Build path
            CellNode currentNode = gridNodes[endI][endJ];
            path = new ArrayList<>();
            path.add(currentNode);

            while (currentNode.getParentNode() != null) {
                // Add parent node to the path
                path.add(currentNode.getParentNode());

                // Ready for next parent node
                currentNode = currentNode.getParentNode();
            }
        }
        else {
            // If the end node has not been reached and the path has been obstructed in the middle
            Alert pathAbsentAlert = new Alert(Alert.AlertType.ERROR); //Alert popup of type "WARNING"
            pathAbsentAlert.setTitle("Path Not Found");
            pathAbsentAlert.setContentText("Path in " + distanceMetric + " distance metric not found!");
            pathAbsentAlert.show();
        }

//        try {
//            for (int i = 0; i < path.size(); i++) {
//                System.out.println(path.get(i));
//            }
//        }
//        catch (NullPointerException ex) {
//            System.out.println("Path cannot be generated!");
//        }

    }

    public static void travelGrid() {
        // Add the starting node's coordinates to the openNodes Priority Queue
        openNodes.add(gridNodes[startI][startJ]);

        // CellNode object of the starting node
        CellNode currentNode;

        try {
            // Traverse the grid till no more open nodes exist in the Priority Queue or destination has been reached
            while (true) {
                // Remove the current node from the Priority Queue and initialize it to the currentNode variable
                currentNode = openNodes.remove();

                // Current node has been visited and is not visitable anymore
                gridNodes[currentNode.getI()][currentNode.getJ()].setVisited(true);

                // If no more open nodes are available, stop the search
                if (currentNode == null) {
                    break;
                }

                // If the current node is the destination node, stop the search
                if (currentNode.equals(gridNodes[endI][endJ])) {
                    return;
                }

                CellNode tempNeighborNode;
                int[][] tempCellArray = GridWeight.getGridWeight();
                if (PathFinderGUI.getRows() == 40) {
                    tempCellArray = GridWeight.getDoubledGridWeight();
                }
                else if (PathFinderGUI.getRows() == 80) {
                    tempCellArray = GridWeight.getQuadrupledGridWeight();
                }

                if (currentNode.getI() - 1 >= 0) {
                    // Node to the top of the current node
                    tempNeighborNode = gridNodes[currentNode.getI() - 1][currentNode.getJ()];
                    updateFinalCost(currentNode, tempNeighborNode, currentNode.getgCost() + tempCellArray[tempNeighborNode.getI()][tempNeighborNode.getJ()]);

                    if (currentNode.getJ() - 1 >= 0) {
                        // Node to the top left of the current node
                        tempNeighborNode = gridNodes[currentNode.getI() - 1][currentNode.getJ() - 1];
                        updateFinalCost(currentNode, tempNeighborNode, currentNode.getgCost() + tempCellArray[tempNeighborNode.getI()][tempNeighborNode.getJ()]);
                    }
                    if (currentNode.getJ() + 1 < gridNodes[0].length) {
                        // Node to the top right of the current node
                        tempNeighborNode = gridNodes[currentNode.getI() - 1][currentNode.getJ() + 1];
                        updateFinalCost(currentNode, tempNeighborNode, currentNode.getgCost() + tempCellArray[tempNeighborNode.getI()][tempNeighborNode.getJ()]);
                    }
                }

                if (currentNode.getJ() - 1 >= 0) {
                    // Node to the left of the current node
                    tempNeighborNode = gridNodes[currentNode.getI()][currentNode.getJ() - 1];
                    updateFinalCost(currentNode, tempNeighborNode, currentNode.getgCost() + tempCellArray[tempNeighborNode.getI()][tempNeighborNode.getJ()]);
                }

                if (currentNode.getJ() + 1 < gridNodes.length) {
                    // Node to the right of the current node
                    tempNeighborNode = gridNodes[currentNode.getI()][currentNode.getJ() + 1];
                    updateFinalCost(currentNode, tempNeighborNode, currentNode.getgCost() + tempCellArray[tempNeighborNode.getI()][tempNeighborNode.getJ()]);
                }

                if (currentNode.getI() + 1 < gridNodes.length) {
                    // Node to the bottom of the current node
                    tempNeighborNode = gridNodes[currentNode.getI() + 1][currentNode.getJ()];
                    updateFinalCost(currentNode, tempNeighborNode, currentNode.getgCost() + tempCellArray[tempNeighborNode.getI()][tempNeighborNode.getJ()]);

                    if (currentNode.getJ() - 1 >= 0) {
                        // Node to the bottom left of the current node
                        tempNeighborNode = gridNodes[currentNode.getI() + 1][currentNode.getJ() - 1];
                        updateFinalCost(currentNode, tempNeighborNode, currentNode.getgCost() + tempCellArray[tempNeighborNode.getI()][tempNeighborNode.getJ()]);
                    }
                    if (currentNode.getJ() + 1 < gridNodes[0].length) {
                        // Node to the bottom right of the current node
                        tempNeighborNode = gridNodes[currentNode.getI() + 1][currentNode.getJ() + 1];
                        updateFinalCost(currentNode, tempNeighborNode, currentNode.getgCost() + tempCellArray[tempNeighborNode.getI()][tempNeighborNode.getJ()]);
                    }
                }
            }
        }
        catch(NullPointerException ex) {
            System.out.println("Path is blocked!");
        }
    }

    public static void updateFinalCost(CellNode currentNode, CellNode tempNode, double tempgCost) {
        // If the node is a blocked node or has already been visited we cannot consider it as a neighbor node
        if (tempNode.isBlocked() || tempNode.isVisited()) {
            return;
        }

        // Calculate temporary final cost for the selected temporary neighbor node
        double tempFinalCost = tempgCost + tempNode.getHeuristicCost();

        // Check if the temporary node is already in the open nodes queue
        boolean isInOpen = openNodes.contains(tempNode);

        // If the node is present in the open nodes queue
        if (isInOpen) {
            if (tempgCost < tempNode.getgCost()) {
                // Update gCost of the neighbor node
                tempNode.setgCost(tempgCost);
                // Update final cost of the neighbor node
                tempNode.setFinalCost(tempFinalCost);
                // Set the parent node of the neighbor node as the current node
                tempNode.setParentNode(currentNode);
            }
        }
        // If the node is not present in the open nodes queue
        else {
            // Update gCost of the neighbor node
            tempNode.setgCost(tempgCost);
            // Update final cost of the neighbor node
            tempNode.setFinalCost(tempFinalCost);
            // Set the parent node of the neighbor node as the current node
            tempNode.setParentNode(currentNode);
            // If the neighbor node is not already in the open nodes queue add it in
            openNodes.add(tempNode);
        }
    }
}
