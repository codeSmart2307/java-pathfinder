/**
 * Author: Raneesh Gomez
 * IIT Student ID: 2016087
 * UoW ID: 16266986
 * Algorithms: Theory, Design and Implementation
 * Coursework: Shortest Path Finder based on a grid using the A* Search Algorithm
 * File Name: CellNode.java
 */

package lk.raneesh.pathfinder.models;

public class CellNode {

    // x coordinate for cell node
    private int i;
    // y coordinate for cell node
    private int j;
    // Distance from starting node to cell node
    private double gCost;
    // Heuristic cost for cell node based on ending node
    private double heuristicCost;
    // Final Cost = gCost + heuristicCost
    private double finalCost;
    // Parent cell node of cell node
    private CellNode parentNode;
    // Boolean flag to detect visited status
    private boolean isVisited;
    // Boolean flag to detect blocked status
    private boolean isBlocked;

    public CellNode(int i, int j) {
        this.i = i;
        this.j = j;
    }

    // Getters and Setters for instance variables

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public double getgCost() {
        return gCost;
    }

    public void setgCost(double gCost) {
        this.gCost = gCost;
    }

    public double getHeuristicCost() {
        return heuristicCost;
    }

    public void setHeuristicCost(double heuristicCost) {
        this.heuristicCost = heuristicCost;
    }

    public double getFinalCost() {
        return finalCost;
    }

    public void setFinalCost(double finalCost) {
        this.finalCost = finalCost;
    }

    public CellNode getParentNode() {
        return parentNode;
    }

    public void setParentNode(CellNode parentNode) {
        this.parentNode = parentNode;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    @Override
    public String toString() {
        return "CellNode{" +
                "i=" + i +
                ", j=" + j +
                ", gCost=" + gCost +
                ", heuristicCost=" + heuristicCost +
                ", finalCost=" + finalCost +
                ", parentNode=" + parentNode +
                '}';
    }
}
