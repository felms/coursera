import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF quickFindUF;
    private final int firstCell;
    private final int lastCell;
    private int openSites;
    private final int n;
    private int[] cells;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n){

        if (n <= 0) {
            throw new IllegalArgumentException("n should be an Integer > 0");
        }

        this.n = n;
        int size = n * n;
        this.firstCell = 0;
        this.lastCell = size + 1;
        this.openSites = 0;
        this.cells = new int[size + 1];
        for (int i = 0; i < size + 1; i++) {
            cells[i] = 0;
        }

        this.quickFindUF = new WeightedQuickUnionUF(size + 2);
        for (int i = 1; i <= n; i++) {
            quickFindUF.union(this.firstCell, i);
        }

        for (int i = size; i > size - n; i--) {
            quickFindUF.union(this.lastCell, i);
        }

    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col){

        if (row <= 0 || col <= 0 || row > n || col > n) {
            throw new IllegalArgumentException("row and col should be ints int the interval: 1 <= x <= n");
        }

        // Abre a celula
        int pos = (row - 1) * n + col;
        if (!isOpen(row, col)) {
            this.cells[pos] = 1;
            this.openSites++;
        }

        // Conecta os vizinhos
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
    public boolean isOpen(int row, int col){
        if (row <= 0 || col <= 0 || row > n || col > n) {
            throw new IllegalArgumentException("row and col should be ints int the interval: 1 <= x <= n");
        }

        int pos = (row - 1) * n + col;
        return this.cells[pos] == 1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col){
        if (row <= 0 || col <= 0 || row > n || col > n) {
            throw new IllegalArgumentException("row and col should be ints int the interval: 1 <= x <= n");
        }
        int pos = (row - 1) * n + col;
        return quickFindUF.find(this.firstCell) == quickFindUF.find(pos);
    }

    // returns the number of open sites
    public int numberOfOpenSites(){
        return this.openSites;
    }

    // does the system percolate?
    public boolean percolates(){
        return quickFindUF.find(this.firstCell) == quickFindUF.find(this.lastCell);
    }

    private String printState() {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                sb.append(this.cells[(i - 1) * n + j]).append(" ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    // test client (optional)
    public static void main(String[] args){
        int n = 20;
        Percolation percolation = new Percolation(n);
        System.out.println(percolation.printState());

        while (!percolation.percolates()) {
            int row = StdRandom.uniform(1, n + 1);
            int column = StdRandom.uniform(1, n + 1);
            percolation.open(row, column);
        }
        System.out.println(percolation.printState());
        System.out.println("Percolation threshold: " + ((double)percolation.numberOfOpenSites() / ((double) n * n)));
    }
}



