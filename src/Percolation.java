public class Percolation {
    private boolean[][] siteStatus;
    private int openSites = 0;

    private MyUF UF;


    /**
     * Constructor.
     * @param n - the dimension of the grid.
     */
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("Invalid input argument.");

        siteStatus = new boolean[n][n];
        UF = new MyUF(n * n + 2);
    }

    /**
     * Opens a cell in the grid.
     * @param row - coordinate row <= 1 && row <= n
     * @param col - coordinate col <= 1 && col <= n
     */
    public void open (int row, int col) {
        if (!isLegalCoordinate(row, col)) throw new IllegalArgumentException("Invalid input argument.");
        if (isOpen(row, col)) return;

        siteStatus[row - 1][col - 1] = true;
        if (row == 1 || row == getSize()) connectWithVirtualSites(row, col);
        connectWithOpenSites(row, col);
        openSites++;
    }

    /**
     * This method indicates the status of the cell: closed or open.
     * @param row,col - coordinate an incoming cell
     * @return true or false
     */
    public boolean isOpen(int row, int col) {
        if (!isLegalCoordinate(row, col)) throw new IllegalArgumentException("Invalid input argument.");
        return siteStatus[row - 1][col - 1];
    }

    /**
     * This method checks the connection to the top virtual cell. The presence of
     * communication with the upper virtual cell indicates the fullness of the cell.
     * @param row,col - coordinate an incoming cell
     * @return ????
     */
    public boolean isFull(int row, int col) {
        if (!isLegalCoordinate(row, col)) throw new IllegalArgumentException("Invalid input argument.");
        return UF.isConnected(0, getIndex(row, col));
    }

    /**
     * This method says how many open cells are in the grid.
     * @return ??
     */
    public int numberOfOpenSites() {
        return openSites;
    }

    /**
     * This method checks whether the system is leaking at this moment.
     */
    public boolean percolates() {
        return UF.isConnected(0, getSize() * getSize() + 1);
    }

    /**
     *This method checks the validity of coordinate values.
     * @param row - coordinate row <= 1 && row <= n
     * @param col - coordinate col <= 1 && col <= n
     * @return true or false
     */
    private boolean isLegalCoordinate(int row, int col) {
        return row > 0 && row <= getSize() && col > 0 && col <= getSize();
    }

    private int getSize(){
        return siteStatus.length;
    }

    /**
     * This method is necessary for working with UF*.
     * @param row,col - coordinate an incoming cell
     * @return index of incoming cell
     */
    private int getIndex(int row, int col) {
        return (row - 1) * getSize() + col;
    }

    /**
     * This method combines an incoming cell with a virtual cell.
     * @param row,col - coordinate an incoming cell
     */
    private void connectWithVirtualSites(int row, int col) {
        if (row == 1) {
            UF.union(0, getIndex(row, col));
        } else {
            UF.union(getSize() * getSize() + 1, getIndex(row, col));
        }
    }

    /**
     * This method combines an incoming cell with neighboring cells that are also open.
     * @param row,col - coordinate an incoming cell
     */
    private void connectWithOpenSites(int row, int col) {
        if (isLegalCoordinate(row - 1, col))// upper
            UF.union(getIndex(row - 1, col), getIndex(row, col));
        if (isLegalCoordinate(row, col - 1))// left
            UF.union(getIndex(row, col - 1), getIndex(row, col));
        if (isLegalCoordinate(row, col + 1))// right
            UF.union(getIndex(row, col + 1), getIndex(row, col));
        if (isLegalCoordinate(row + 1, col))// bottom
            UF.union(getIndex(row + 1, col), getIndex(row, col));
    }
}
