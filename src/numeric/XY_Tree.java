package numeric;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class XY_Tree {
    /* Private Data Fields */
    private ArrayList<Coordinate> dataset;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private String filename;
    /* Overloaded Constructors */
    public XY_Tree(ArrayList<Coordinate> dataset) { this.dataset = dataset; }
    public XY_Tree(int i) throws Exception { this(i, ""); }
    public XY_Tree(int i, String filename) throws Exception { this(i, true, filename); }
    public XY_Tree(int i, boolean filed, String filename) throws Exception {
        System.out.println("\nWelcome to the Computational Method for " + (i == 1 ? "Linear" : (i == 2 ? "Quadratic" : "Cubic")) + " Interpolation!");
        if (filed) {
            System.out.print("\nHow many (x,y) coordinates are there in your dataset? ");
            set(new Scanner(System.in).nextInt());
        }

        if (!filename.equals("")) {
            if (filed) {
                oos = new ObjectOutputStream(new FileOutputStream(filename));
                oos.writeObject(this.dataset);
                oos.close();
            } else {
                ois = new ObjectInputStream(new FileInputStream(filename));
                ois.readObject();
                ois.close();
            }
        }
    }
    /* Getter-Setter Methods */
    public void setDataset(ArrayList<Coordinate> dataset) { this.dataset = dataset; }
    public ArrayList<Coordinate> getDataset() { return this.dataset; }
    public void setFilename(String filename) { this.filename = filename; }
    public String getFilename() { return this.filename; }
    /* Display This Object */
    @Override
    public String toString() {
        String string = "\nXY-Tree: ";
        for(Coordinate coordinate: dataset)
            string += coordinate;

        return string;
    }

    /* Print Formula for Reference */
    public String formula(int i) {
        if(i < 1 || i > 2)
            return "Sorry! The interpolation methodology for your desired input is not supported in this program.\n";

        return "\nFormula for " + (i == 1 ? "Linear" : "Quadratic") + " Interpolation: f" + i + "(x) = y0 + (y1 - y0)/(x1 - x0) * (x - x0)" + (i == 2 ? " + ((y2 - y1)/(x2 - x1) - (y1 - y0)/(x1 - x0)) / (x2 - x0) * (x - x0) * (x - x1)" : "");
    }

    /* Prompt User to Enter all Coordinates of the XY-Tree Dataset through iteration of the Coordinate Class Constructor Call */
    public void set(int coordinates) {
        this.dataset = new ArrayList<>(coordinates);
        for(int i = 0; i < coordinates; i++)
            this.dataset.add(new Coordinate());
    }

    public String computeY(int interpolation) {
        /* User Input: x for computing f(x) */
        double x = new Scanner(System.in).nextDouble();
        /* Determine whether the problem is an issue that can be solved by polynomial interpolation */
        boolean extrapolation = false;
        if(x < dataset.get(0).getX())
            extrapolation = true;

        /* Begin iteration to find upper and lower bound of the dataset points available around the x value */
        int i;
        for(i = 1; !extrapolation && i < dataset.size() && x > dataset.get(i).getX(); i++);

        if(i == dataset.size() || extrapolation )
            return "Sorry! The issue cannot be resolved using computational methods for interpolation because it is an extrapolation problem. ";

        /* Substituting the variables for the formula with input and corresponding values */
        if(interpolation < 1 || interpolation > 2)
            return "Sorry! The interpolation methodology for your desired input is not supported in this program.\n";

        return "\nUsing the Formula -\nf" + interpolation + "(" + x + ") = " + (interpolation == 1 ? printLinearSub (x,i) : printQuadraticSub(x,i)) + " = " + Double.toString(interpolation == 1 ? computeY(x, i) : computeY2(x, i));
    }

    /* Display equation of the problem after replacing/sustituting the variables of the formula for the values accoding to the dataset */
    public String printLinearSub(double x, int i) { return y(i-1) + " + (" + y(i) + " - " + y(i - 1) + ") / (" + x(i) + " - " + x(i-1) + ") * (" + x + " - " + x(i-1); }
    public String printQuadraticSub(double x, int i) { return y(i-2) + " + (" + y(i-1) + " - " + y(i-2) + ") / (" + x(i-1) + " - " + x(i-2) + ") * (" + x + " - " + x(i-1) + ") + ((" + y(i) + " - " + y(i-1) + ") / (" + x(i) + " - " + x(i-1) + ") - (" + y(i-1) + " - " + y(i-2) + ") / (" + x(i-1) + " - " + x(i-2) + ")) / (" + x(i) + " - " + x(i-2) + ") * (" + x + " - " + x(i-2) + ") * (" + x + " - " + x(i-1) + ")"; }

    /* Compute the approximate value of f(x) according to the given input as x into the formala for either of the following interpolation methods */
    public double computeY(double x, int i) { return y(i-1) + computeSlope(i, i-1) * (x - x(i-1)); }
    public double computeY2(double x, int i) { return y(i-2) + computeSlope(i-1, i-2) * (x - x(i-2)) + (computeSlope(i, i-1) - computeSlope(i-1, i-2)) / (x(i) - x(i-2)) * (x - x(i-2)) * (x - x(i-1)); }

    /* Get x, y, or m of a function -> ordinate, abscissa, or slope of the function at a given index in the dataset */
    public double y(int index) { return this.dataset.get(index).getY(); }
    public double x(int index) { return this.dataset.get(index).getX(); }
    public double computeSlope(int upper, int lower) { return (y(upper) - y(lower)) / (x(upper) - x(lower)); }
}
