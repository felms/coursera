public class HelloGoodbye {
    public static void main(String[] args) {
        if (args.length > 0) {
            String name0 = args[0];
            String name1 = args[1];
            System.out.printf("Hello %s and %s.\n", name0, name1);
            System.out.printf("Goodbye %s and %s.\n", name1, name0);
        }
    }
}
