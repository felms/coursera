import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        double i = 1.0;
        String champion = "";
        while (!StdIn.isEmpty()) {
            String input = StdIn.readString();
            champion = StdRandom.bernoulli(1.0 / i) ? input : champion;
            i++;
        }

        System.out.println(champion);
    }
}
