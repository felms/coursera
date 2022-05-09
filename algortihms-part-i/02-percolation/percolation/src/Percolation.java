import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final WeightedQuickUnionUF quickFindUF;
    private final int firstCell;
    private final int lastCell;
    private int openSites;
    private final int n;
    private boolean[] cells;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {

        if (n <= 0) {
            throw new IllegalArgumentException("n should be an Integer > 0");
        }

        this.n = n;
        int size = n * n;
        this.firstCell = 0;
        this.lastCell = size + 1;
        this.openSites = 0;
        this.cells = new boolean[size + 1];
        for (int i = 0; i < size + 1; i++) {
            cells[i] = false;
        }

        this.quickFindUF = new WeightedQuickUnionUF(size + 2);

    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {

        if (row <= 0 || col <= 0 || row > n || col > n) {
            throw new IllegalArgumentException("row and col should be ints int the interval: 1 <= x <= n");
        }

        // Abre a celula
        int pos = (row - 1) * n + col;
        if (!isOpen(row, col)) {
            this.cells[pos] = true;
            this.openSites++;
        }

        // Conecta os vizinhos
        // Primeira coluna
        if (row == 1) {
            quickFindUF.union(this.firstCell, pos);
        }

        // Ultima coluna
        if (row == n) {
            quickFindUF.union(this.lastCell, pos);
        }

        // Esquerda
        if (col > 1 && isOpen(row, col - 1)) {
            int left = pos - 1;
            quickFindUF.union(left, pos);
        }
        // Direita
        if (col < n && isOpen(row, col + 1)) {
            int right = pos + 1;
            quickFindUF.union(right, pos);
        }

        // Em cima
        if (row > 1 && isOpen(row - 1, col)) {
            int up = pos - n;
            quickFindUF.union(up, pos);
        }

        // Em baixo
        if (row < n && isOpen(row + 1, col)) {
            int down = pos + n;
            quickFindUF.union(down, pos);
        }

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row <= 0 || col <= 0 || row > n || col > n) {
            throw new IllegalArgumentException("row and col should be ints int the interval: 1 <= x <= n");
        }

        int pos = (row - 1) * n + col;
        return this.cells[pos];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row <= 0 || col <= 0 || row > n || col > n) {
            throw new IllegalArgumentException("row and col should be ints int the interval: 1 <= x <= n");
        }
        int pos = (row - 1) * n + col;
        return quickFindUF.find(this.firstCell) == quickFindUF.find(pos);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return quickFindUF.find(this.firstCell) == quickFindUF.find(this.lastCell);
    }

    // test client (optional)
    public static void main(String[] args) {
        // Unnecessary after I implemented the PercolationStats class
    }
}



