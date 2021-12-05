package main;

import java.io.IOException;
import java.lang.Exception;

import numeric.XY_Tree;

public class Main {

    public static void main(String[] args) {
        /* Declare the type of interpolation */
        int interpolation = 2;
        /* Create the dataset for performing interpolation */
        XY_Tree xy_tree = null;
        try {
            xy_tree = new XY_Tree(interpolation);
        } catch(Exception ex) { ex.printStackTrace(); }

        /* Print the dataset you have entered */
        System.out.println(xy_tree);
        /* Print the formula for interpolation */
        System.out.println(xy_tree.formula(interpolation));

        /* Compute f(x) for x using Interpolation */
        System.out.print("\nWhich value of x would you like to compute f(x) using Interpolation?\nEnter x: ");
        System.out.println(xy_tree.computeY(interpolation));
    }
}

/*
    Interpolation Keys:
        1 - Linear
        2 - Quadratic
        3 - Cubic

    Input Method:
        true - User Input
        false - File Input
 */