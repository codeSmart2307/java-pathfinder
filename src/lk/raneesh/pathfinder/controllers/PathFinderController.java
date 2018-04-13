package lk.raneesh.pathfinder.controllers;

import lk.raneesh.pathfinder.models.CellNode;
import lk.raneesh.pathfinder.utility.GridWeight;

import java.util.PriorityQueue;

public class PathFinderController {

    public static int startI;
    public static int startJ;
    public static int endI;
    public static int endJ;
    public static CellNode[][] gridNodes = new CellNode[GridWeight.getGridWeight().length][GridWeight.getGridWeight().length];
    public static PriorityQueue<CellNode> openNodes;

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


        }
    }

}
