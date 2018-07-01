package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[][] world;
    private int dimension;
    private int openSites;
    private WeightedQuickUnionUF tracker; // include the top and bottom as two additional virtual nodes
    private WeightedQuickUnionUF mapKeeper; // does not include the bottom virtual node. to solve the backwash issue.

    /**
     * takes the position (row, col), returns the index of the system correlates to the position (row, col)
     * the positions are indexed as (0 ~ N^2-1)
     * there is a virtual node indexed as N^2 at the top
     * there is another virtual nodes indexed as (N^2+1) at the bottom. Connecting to any of them will make the system percolate.
     *
     * @param row
     * @param col
     * @return
     */
    private int Pos2Index (int row, int col) {
        return row * dimension + col;
    }


    /**
     * constructor
     *
     * @param n: the dimension of the world (N by N matrix)
     */
    public Percolation (int n) {
        if (n <= 0) throw new java.lang.IllegalArgumentException("World dimension should be greater than 0");
        this.dimension = n;
        this.world = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.world[i][j] = false;
            }
        }
        this.tracker = new WeightedQuickUnionUF(n * n + 2);
        this.mapKeeper = new WeightedQuickUnionUF(n * n + 1);
        this.openSites = 0;
    }

    /**
     * open the site (row, col) if it is not open
     *
     * @param row
     * @param col
     */
    public void open (int row, int col) {
        if (row < 0 || col < 0 || row >= dimension || col >= dimension) throw new java.lang.IndexOutOfBoundsException("Invalid index!");

        if (!world[row][col]) {
            // mark the corresponding coordinate to be true
            world[row][col] = true;
            openSites++;
            // connects the adjacent coordinates if they are open
            if (row - 1 >= 0 && isOpen(row - 1, col)) {
                tracker.union(Pos2Index(row, col), Pos2Index(row - 1, col));
                mapKeeper.union(Pos2Index(row, col), Pos2Index(row - 1, col));
            }
            if (row + 1 < dimension && isOpen(row + 1, col)) {
                tracker.union(Pos2Index(row, col), Pos2Index(row + 1, col));
                mapKeeper.union(Pos2Index(row, col), Pos2Index(row + 1, col));
            }
            if (col - 1 >= 0 && isOpen(row, col - 1)) {
                tracker.union(Pos2Index(row, col), Pos2Index(row, col - 1));
                mapKeeper.union(Pos2Index(row, col), Pos2Index(row, col - 1));
            }
            if (col + 1 < dimension && isOpen(row, col + 1)) {
                tracker.union(Pos2Index(row, col), Pos2Index(row, col + 1));
                mapKeeper.union(Pos2Index(row, col), Pos2Index(row, col + 1));
            }
            // connects to the virtual nodes if it's on the top or bottom row
            if (row == 0) {
                tracker.union(Pos2Index(row, col), dimension * dimension);
                mapKeeper.union(Pos2Index(row, col), dimension * dimension);
            }
            if (row == dimension - 1) {
                tracker.union(Pos2Index(row, col), dimension * dimension + 1);
            }
        }
    }

    /**
     * is the site (row, col) open?
     *
     * @param row
     * @param col
     * @return
     */
    public boolean isOpen (int row, int col) {
        if (row < 0 || col < 0 || row >= dimension || col >= dimension) throw new java.lang.IndexOutOfBoundsException("Invalid index!");
        return world[row][col];
    }

    /**
     * returns true if the position is connected to the upper virtual node
     * @param row
     * @param col
     * @return
     */
    public boolean isFull (int row, int col) {
        if (row < 0 || col < 0 || row >= dimension || col >= dimension) throw new java.lang.IndexOutOfBoundsException("Invalid index!");
        return mapKeeper.connected(Pos2Index(row, col), dimension * dimension);
    }

    /**
     * returns the number of open sites
     *
     * @return
     */
    public int numberOfOpenSites() {
        return openSites;
    }

    /**
     * returns if the system percolates or not
     *
     * @return
     */
    public boolean percolates() {
        return tracker.connected(dimension * dimension, dimension * dimension + 1);
    }

    /**
     * Used for testing
     * @param args
     */
    public static void main (String[] args) {

    }
}
