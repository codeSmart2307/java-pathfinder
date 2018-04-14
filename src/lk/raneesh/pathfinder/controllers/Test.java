package lk.raneesh.pathfinder.controllers;

import lk.raneesh.pathfinder.utility.GridWeight;

public class Test {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print("Row:" + i + "Column:" + j + " ");
            }
            System.out.println();
        }

        System.out.println(GridWeight.getGridWeight().length);
    }
}
