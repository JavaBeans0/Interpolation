package numeric;

import java.util.Scanner;

public class Coordinate {
    private double x;
    private double y;

    public Coordinate() {
        /* Declare and Initialize Input Variable */
        Scanner input = new Scanner(System.in);
        System.out.print("Enter x: ");
        this.x = new Scanner(System.in).nextDouble();
        System.out.print("Enter y: ");
        this.y = new Scanner(System.in).nextDouble();
    }
    public Coordinate(double x, double y) { this.x = x; this.y = y; }
    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
    public double getX() { return this.x; }
    public double getY() { return this.y; }

    @Override
    public String toString() { return "\n(" + this.x + "," + this.y + ")"; }
}
