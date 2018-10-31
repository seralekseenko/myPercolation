import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    
    private final boolean[][] siteStatus;
    private final WeightedQuickUnionUF myUF;

    private  int openSites = 0;

    /**
     * Create square grid.
     * 
     * @param n - the dimension of the grid.
     */
    public Percolation(int n) { // O(n^2)
        if (n <= 0) throw new IllegalArgumentException("Invalid input argument. n: " + n);
        siteStatus = new boolean[n][n];
        myUF = new WeightedQuickUnionUF(n * n + 2);
    }

    /**
     * Opens a cell in the grid.
     * @param row - coordinate row <= 1 && row <= n
     * @param col - coordinate col <= 1 && col <= n
     */
    public void open(int row, int col) { //
        assertCoordinate(row, col); // O(1)
        if (isOpen(row, col)) return; // O(1)

        siteStatus[row - 1][col - 1] = true;
        maybeConnectWithVirtualSites(row, col);
        connectWithOpenSites(row, col);
        openSites++;
    }

    /**
     * This method indicates the status of the cell.
     *
     * @return true if the cell is open.
     */
    public boolean isOpen(int row, int col) { // O(1)
        assertCoordinate(row, col); // O(1)
        return siteStatus[row - 1][col - 1];
    }

    /**
     * Method return percolation status of the cell. If the cell is percolate it is called full. 
     * 
     * @param row - of the cell that you checking
     * @param col - of the cell that you checking
     * @return true - is the cell is full, false othervise
     */
    public boolean isFull(int row, int col) {
        assertCoordinate(row, col);
        // This method checks the connection to the top virtual cell communication with the upper virtual cell indicates
        // the fullness of the cell..
        // If cell is connected to cell with index 0 it is called full.

        return myUF.connected(0, getIndex(row, col));
    }

    /**
     * @return - how many open cells are in the grid.
     */
    public int numberOfOpenSites() { // O(1)
        return openSites;
    }

    /**
     * This method checks whether the system is leaking at this moment.
     */
    public boolean percolates() { // O (n)
        return myUF.connected(0, getSize() * getSize() + 1);
    }

    /**
     *This method checks the validity of coordinate values.
     * @param row - coordinate row <= 1 && row <= n
     * @param col - coordinate col <= 1 && col <= n
     */
    private void assertCoordinate(int row, int col) { // O(1)
        if (row < 1 || row > getSize()) throw new IllegalArgumentException("Invalid input argument. row: " + row);
        if (col < 1 || col > getSize()) throw new IllegalArgumentException("Invalid input argument. col: " + col);
    }

    private int getSize() { // O(1)
        return siteStatus.length;
    }

    /**
     * This method is necessary for working with myUF*.
     * @param row,col - coordinate an incoming cell
     * @return index of incoming cell
     */
    private int getIndex(int row, int col) {
        return (row - 1) * getSize() + col;
    }

    /**
     * This method combines an incoming cell with a virtual cell.
     * @param row - coordinate an incoming cell
     * @param col - coordinate an incoming cell
     */
    private void maybeConnectWithVirtualSites(int row, int col) { // O(n)
        if (row == 1) {
            myUF.union(0, getIndex(row, col)); // O(n)
        } else if (row == getSize()) {
            myUF.union(getSize() * getSize() + 1, getIndex(row, col)); // O(n)
        }
    }

    /**
     * This method combines an incoming cell with neighboring cells that are also open.
     * @param row - coordinate an incoming cell
     * @param col - coordinate an incoming cell
     */
    private void connectWithOpenSites(int row, int col) { // O(4 * (n + log n))

        if (row > 1 && isOpen(row - 1, col))// upper
            myUF.union(getIndex(row - 1, col), getIndex(row, col));

        if (col > 1 && isOpen(row, col - 1))// left
            myUF.union(getIndex(row, col - 1), getIndex(row, col));

        if (col < getSize() - 1 && isOpen(row, col + 1))// right
            myUF.union(getIndex(row, col + 1), getIndex(row, col));

        if (row < getSize() - 1 && isOpen(row + 1, col))// bottom
            myUF.union(getIndex(row + 1, col), getIndex(row, col));
    }
}
