package lk.raneesh.pathfinder.models;

public class CellNode {

    private int i;
    private int j;
    private int nodeCost;
    private int heuristicCost;
    private int finalCost;
    private CellNode parentNode;
    private boolean isVisited;

    public CellNode(int i, int j) {
        this.i = i;
        this.j = j;
    }

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

    public int getNodeCost() {
        return nodeCost;
    }

    public void setNodeCost(int nodeCost) {
        this.nodeCost = nodeCost;
    }

    public int getHeuristicCost() {
        return heuristicCost;
    }

    public void setHeuristicCost(int heuristicCost) {
        this.heuristicCost = heuristicCost;
    }

    public int getFinalCost() {
        return finalCost;
    }

    public void setFinalCost(int finalCost) {
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

    @Override
    public String toString() {
        return "CellNode{" +
                "i=" + i +
                ", j=" + j +
                ", nodeCost=" + nodeCost +
                ", heuristicCost=" + heuristicCost +
                ", finalCost=" + finalCost +
                ", parentNode=" + parentNode +
                '}';
    }
}
