package lk.raneesh.pathfinder.controllers;

import lk.raneesh.pathfinder.models.CellNode;
import lk.raneesh.pathfinder.utility.GridWeight;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class PathFinderController {

    public static int startI;
    public static int startJ;
    public static int endI;
    public static int endJ;
    public static String distanceMetric;
    public static CellNode[][] gridNodes = new CellNode[GridWeight.getGridWeight().length][GridWeight.getGridWeight().length];
    public static PriorityQueue<CellNode> openNodes;
    public static ArrayList<CellNode> path;

    public static void runPathfinder() {
        // Set starting node's gCost to 0
        gridNodes[startI][startJ].setgCost(0);
        // Starting node's parent doesn't exist since the path starts there
        gridNodes[startI][startJ].setParentNode(null);

        // Traverse though nodes in grid and assign heuristic and final cost
        for (int i = 0; i < gridNodes.length; i++) {
            for (int j = 0; j < gridNodes.length; j++) {
                // Calculate heuristic cost for all nodes in the grid based on the chosen distance metric and end node
                if (distanceMetric.equals("Manhattan")) {
                    double heuristicCost = Math.abs(startI - endI) + Math.abs(startJ - endJ);
                    gridNodes[i][j].setHeuristicCost(heuristicCost);
                    gridNodes[i][j].setFinalCost(gridNodes[i][j].getgCost() + gridNodes[i][j].getHeuristicCost());
                }
                else if (distanceMetric.equals("Euclidean")) {
                    double heuristicCost = Math.sqrt(Math.pow((startI - endI), 2) + Math.pow((startJ - endJ), 2));
                    gridNodes[i][j].setHeuristicCost(heuristicCost);
                    gridNodes[i][j].setFinalCost(gridNodes[i][j].getgCost() + gridNodes[i][j].getHeuristicCost());
                }
                else if (distanceMetric.equals("Chebyshev")) {
                    int heuristicCost = Math.abs(startI - endI) + Math.abs(startJ - endJ);
                    gridNodes[i][j].setHeuristicCost(heuristicCost);
                    gridNodes[i][j].setFinalCost(gridNodes[i][j].getgCost() + gridNodes[i][j].getHeuristicCost());
                }
            }
        }
    }

    public static void travelGrid() {
        // Add the starting node's coordinates to the openNodes Priority Queue
        openNodes.add(gridNodes[startI][startJ]);

        // CellNode object of the starting node
        CellNode currentNode;

        // Traverse the grid till no more open nodes exist in the Priority Queue or destination has been reached
        while(true) {

            // Remove the current node from the Priority Queue and initialize it to the currentNode variable
            currentNode = openNodes.poll();

            // If no more open nodes are available, stop the search
            if (currentNode == null) {
                break;
            }
            // If the current node is the destination node, stop the search
            if (currentNode.equals(gridNodes[endI][endJ])) {
                return;
            }

            // Current node has been visited and is not visitable anymore
            gridNodes[currentNode.getI()][currentNode.getJ()].setVisited(true);

            CellNode neighborCellNode;

            if (currentNode.getI() - 1 >= 0) {
                // Node to the left of the current node
                neighborCellNode = gridNodes[currentNode.getI() - 1][currentNode.getJ() - 1];
            }


        }
    }

}
