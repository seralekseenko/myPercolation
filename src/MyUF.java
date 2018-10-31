class MyUF {

    private final int[] id;
    private final int[] treeSize;

    /**
     * @param n - Amount of elements.
     */
    MyUF(int n) { // O(n)
        this.id = new int[n];
        this.treeSize = new int[n];
        for (int i = 0; i < n; i++) {
            this.id[i] = i;
            this.treeSize[i] = 1;
        }
    }

    /**
     * Checks if the cells are connected or not.
     * @param p - first argument (p >= 0 && p < n)
     * @param q - second argument (q >= 0 && q < n)
     * @return - true if the cells are connected.
     * @throws IllegalArgumentException - if input arguments are not within boundaries.
     */
    boolean connected(int p, int q) { // O(n + log n)
        return root(p) == root(q);
    }

    /**
     * The method searches for the root for the input argument.
     * @param i - the argument must be within: i >= 0 && i < n
     * @return index of the root of the input element
     * @throws IllegalArgumentException - if input arguments are not within boundaries.
     */
    private int root(int i) { // O(n) O(lod n)
        validate(i); // O(1)
        while (i != id[i]) {
            id[i] = id[id[i]];
            i = id[i];
        }
        return i;
    }

    /**
     * Checks the validity of the argument.
     * @param i - the argument to check.
     */
    private void validate(int i) { // O(1)
        if (i < 0 || i >= id.length) {
            throw new IllegalArgumentException("Index "
                    + i
                    + " in unacceptable limits. Valid values are from 0 to "
                    + (id.length-1)
                    +".");
        }
    }

    /**
     * This method weightedly combines the specified arguments.
     * @param p - first argument p >= 0 && p < n
     * @param q - second argument q >= 0 && p < n
     */
    void union(int p, int q) { // O(n + log n)
        validate(p);
        validate(q);
        int i = root(p); // O(n)
        int j = root(q); // O(log n)

        if (i == j) return;

        if  (treeSize[i] < treeSize[j]) {
            id[i] = j;
            treeSize[j] += treeSize[i];
        }  else {
            id[j] = i;
            treeSize[i] += treeSize[j];
        }
    }
}
